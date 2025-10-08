package entidades;

import DAOS.consultaDAO;
import DAOS.consultaDAO.ConsultaRegistro;
import DAOS.pacienteDAO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

public class AgendamentoConsultas {
    private final List<Consulta> consultas;
    private final consultaDAO consultaDao;

    public AgendamentoConsultas() {
        this.consultas = new ArrayList<>();
        this.consultaDao = new consultaDAO();
    }

    public Consulta agendarConsulta(Paciente paciente, Medico medico, LocalDateTime dataHora, String local) {
        Objects.requireNonNull(paciente, "Paciente nao pode ser nulo");
        Objects.requireNonNull(medico, "Medico nao pode ser nulo");
        Objects.requireNonNull(dataHora, "Data e hora nao podem ser nulas");
        String localNormalizado = normalizarLocal(local);

        validarDisponibilidade(medico, dataHora);
        validarConflitos(medico, dataHora, localNormalizado);
        validarConflitosPersistidos(medico, dataHora, localNormalizado);

        double descontoPercentual = calcularDesconto(paciente, medico);
        Consulta novaConsulta = new Consulta(paciente, medico, dataHora, localNormalizado, descontoPercentual);
        consultas.add(novaConsulta);
        liberarOuReservarHorario(medico, dataHora, false);
        registrarConsultaNoHistorico(novaConsulta);
        consultaDao.salvar(novaConsulta);
        return novaConsulta;
    }

    public void concluirConsulta(String consultaId, String diagnostico, String prescricao) {
        Optional<Consulta> opt = buscarConsultaPorId(consultaId);
        if (opt.isPresent()) {
            Consulta consulta = opt.get();
            consulta.concluir(diagnostico, prescricao);
            consultaDao.atualizarConclusao(consultaId, diagnostico, prescricao);
            registrarConclusaoNoHistorico(consulta);
            return;
        }
        Optional<ConsultaRegistro> regOpt = consultaDao.buscarPorId(consultaId);
        if (regOpt.isPresent()) {
            ConsultaRegistro r = regOpt.get();
            consultaDao.atualizarConclusao(consultaId, diagnostico, prescricao);
            String resumo = montarResumoConclusao(r.nomeMedico, r.crmMedico, r.dataHora, r.local, diagnostico, prescricao);
            new pacienteDAO().atualizarHistoricoConsulta(r.cpfPaciente, resumo);
            return;
        }
        throw new java.util.NoSuchElementException("Consulta nao encontrada");
    }

    public void atualizarStatus(String consultaId, StatusConsulta novoStatus) {
        Optional<Consulta> opt = buscarConsultaPorId(consultaId);
        if (opt.isPresent()) {
            Consulta consulta = opt.get();
            StatusConsulta statusAtual = consulta.getStatus();
            if (statusAtual == novoStatus) {
                return;
            }
            if (statusAtual == StatusConsulta.CANCELADA && novoStatus == StatusConsulta.AGENDADA) {
                throw new IllegalStateException("Consulta cancelada nao pode retornar para agendada");
            }

            consulta.atualizarStatus(novoStatus);
            if (novoStatus == StatusConsulta.CANCELADA) {
                liberarOuReservarHorario(consulta.getMedico(), consulta.getDataHora(), true);
            }
        }
        consultaDao.atualizarStatus(consultaId, novoStatus);
    }

    public Optional<Consulta> buscarConsultaPorId(String consultaId) {
        return consultas.stream()
            .filter(consulta -> consulta.getId().equals(consultaId))
            .findFirst();
    }

    public List<Consulta> listarConsultas() {
        return Collections.unmodifiableList(new ArrayList<>(consultas));
    }

    public List<Consulta> listarConsultasPorMedico(int crm) {
        List<Consulta> resultado = new ArrayList<>();
        for (Consulta consulta : consultas) {
            if (consulta.getMedico().getCrm() == crm) {
                resultado.add(consulta);
            }
        }
        return Collections.unmodifiableList(resultado);
    }

    public List<Consulta> listarConsultasPorPaciente(String cpf) {
        List<Consulta> resultado = new ArrayList<>();
        for (Consulta consulta : consultas) {
            if (consulta.getPaciente().getCpf().equals(cpf)) {
                resultado.add(consulta);
            }
        }
        return Collections.unmodifiableList(resultado);
    }

    public boolean podeAgendar(Medico medico, LocalDateTime dataHora, String local) {
        try {
            validarDisponibilidade(medico, dataHora);
            validarConflitos(medico, dataHora, normalizarLocal(local));
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    private void validarDisponibilidade(Medico medico, LocalDateTime dataHora) {
        List<LocalDateTime> agenda = medico.getAgendaHorario();
        if (!agenda.contains(dataHora)) {
            throw new IllegalArgumentException("Horario nao disponivel na agenda do medico");
        }
    }

    private void validarConflitos(Medico medico, LocalDateTime dataHora, String local) {
        for (Consulta consulta : consultas) {
            if (!consulta.isAtiva()) {
                continue;
            }
            boolean mesmoHorario = consulta.getDataHora().equals(dataHora);
            if (!mesmoHorario) {
                continue;
            }
            if (consulta.getMedico().getCrm() == medico.getCrm()) {
                throw new IllegalStateException("Medico ja possui consulta neste horario");
            }
            if (consulta.getLocal().equalsIgnoreCase(local)) {
                throw new IllegalStateException("Ja existe consulta neste local e horario");
            }
        }
    }

    private void validarConflitosPersistidos(Medico medico, LocalDateTime dataHora, String local) {
        for (ConsultaRegistro r : consultaDao.listarAgendadas()) {
            if (r.dataHora.equals(dataHora)) {
                if (r.crmMedico == medico.getCrm()) {
                    throw new IllegalStateException("Medico ja possui consulta neste horario (persistido)");
                }
                if (r.local.equalsIgnoreCase(local)) {
                    throw new IllegalStateException("Ja existe consulta neste local e horario (persistido)");
                }
            }
        }
    }

    private double calcularDesconto(Paciente paciente, Medico medico) {
        if (paciente instanceof PacienteEspecial || paciente.getTemRegistroPlano()) {
            return 0.20;
        }
        String planoPaciente = paciente.getNomePlanoDeSaude();
        if (planoPaciente != null && !planoPaciente.isEmpty() && medico.getPlanosAceitos().contains(planoPaciente)) {
            return 0.10;
        }
        return 0.0;
    }

    private void registrarConsultaNoHistorico(Consulta consulta) {
        String resumo = "Consulta " + consulta.getId() + " com medico " + consulta.getMedico().getNome()
            + " em " + consulta.getDataHora() + " no local " + consulta.getLocal();
        List<String> historico = consulta.getPaciente().getHistoricoConsultas();
        if (historico != null) {
            historico.add(resumo);
        }
        new pacienteDAO().atualizarHistoricoConsulta(consulta.getPaciente().getCpf(), resumo);
    }

    private void registrarConclusaoNoHistorico(Consulta consulta) {
        String resumo = montarResumoConclusao(
            consulta.getMedico().getNome(),
            consulta.getMedico().getCrm(),
            consulta.getDataHora(),
            consulta.getLocal(),
            consulta.getDiagnostico(),
            consulta.getPrescricao()
        );
        List<String> historico = consulta.getPaciente().getHistoricoConsultas();
        if (historico != null) {
            historico.add(resumo);
        }
        new pacienteDAO().atualizarHistoricoConsulta(consulta.getPaciente().getCpf(), resumo);
    }

    private String montarResumoConclusao(String nomeMedico, int crm, LocalDateTime dataHora, String local, String diagnostico, String prescricao) {
        StringBuilder sb = new StringBuilder();
        sb.append("Conclusao da consulta em ").append(dataHora)
          .append(" | Medico: ").append(nomeMedico).append(" (CRM ").append(crm).append(")")
          .append(" | Local: ").append(local);
        if (diagnostico != null && !diagnostico.trim().isEmpty()) {
            sb.append(" | Diagnostico: ").append(diagnostico);
        }
        if (prescricao != null && !prescricao.trim().isEmpty()) {
            sb.append(" | Prescricao: ").append(prescricao);
        }
        return sb.toString();
    }

    private void liberarOuReservarHorario(Medico medico, LocalDateTime dataHora, boolean liberar) {
        if (liberar) {
            medico.adicionarHorarioDisponivel(dataHora);
        } else {
            medico.removerHorarioDisponivel(dataHora);
        }
    }

    private Consulta localizarConsultaPorId(String consultaId) {
        return buscarConsultaPorId(consultaId)
            .orElseThrow(() -> new NoSuchElementException("Consulta nao encontrada"));
    }

    private String normalizarLocal(String local) {
        String localNormalizado = Objects.requireNonNull(local, "Local nao pode ser nulo").trim();
        if (localNormalizado.isEmpty()) {
            throw new IllegalArgumentException("Local nao pode ser vazio");
        }
        return localNormalizado;
    }
}
