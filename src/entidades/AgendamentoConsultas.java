package entidades;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

public class AgendamentoConsultas {
    private final List<Consulta> consultas;

    public AgendamentoConsultas() {
        this.consultas = new ArrayList<>();
    }

    public Consulta agendarConsulta(Paciente paciente, Medico medico, LocalDateTime dataHora, String local) {
        Objects.requireNonNull(paciente, "Paciente nao pode ser nulo");
        Objects.requireNonNull(medico, "Medico nao pode ser nulo");
        Objects.requireNonNull(dataHora, "Data e hora nao podem ser nulas");
        String localNormalizado = normalizarLocal(local);

        validarDisponibilidade(medico, dataHora);
        validarConflitos(medico, dataHora, localNormalizado);

        double descontoPercentual = calcularDesconto(paciente, medico);
        Consulta novaConsulta = new Consulta(paciente, medico, dataHora, localNormalizado, descontoPercentual);
        consultas.add(novaConsulta);
        liberarOuReservarHorario(medico, dataHora, false);
        registrarConsultaNoHistorico(novaConsulta);
        return novaConsulta;
    }

    public void atualizarStatus(String consultaId, StatusConsulta novoStatus) {
        Consulta consulta = localizarConsultaPorId(consultaId);
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
        List<String> historico = consulta.getPaciente().getHistoricoConsultas();
        if (historico != null) {
            String resumo = "Consulta " + consulta.getId() + " com medico " + consulta.getMedico().getNome()
                + " em " + consulta.getDataHora() + " no local " + consulta.getLocal();
            historico.add(resumo);
        }
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
