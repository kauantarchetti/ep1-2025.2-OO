package entidades;

import DAOS.consultaDAO;
import DAOS.internacaoDAO;
import DAOS.medicoDAO;
import DAOS.pacienteDAO;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class Service {

    private final inputScanner leitorEntrada = new inputScanner();
    private final pacienteDAO pacienteDaos = new pacienteDAO();
    private final medicoDAO medicoDaos = new medicoDAO();
    private final consultaDAO consultaDaos = new consultaDAO();
    private final internacaoDAO internacaoDaos = new internacaoDAO();
    private final AgendamentoConsultas agendamentoConsultas = new AgendamentoConsultas();
    private final DateTimeFormatter dataHoraFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private String local;
    private final List<String> especialidades = Arrays.asList(
        "Medico geral",
        "Ortopedista",
        "Cirurgiao",
        "Pediatra",
        "Oftalmo",
        "Psiquiatra",
        "Dermatologista",
        "Cardiologista",
        "Ginecologista",
        "Urologista"
    );
    private final List<String> planos = Arrays.asList(
        "Cassi",
        "Amil",
        "Porto Seguro",
        "Unimed",
        "Sulamerica",
        "Bradesco Saude",
        "Saude Caixa"
    );
    private int opcao;

    public void exibirMenu() {
        System.out.println("== SISTEMA DE GERENCIAMENTO HOSPITALAR ==");
        System.out.println("Opcoes:");
        System.out.println("0 -> Medico");
        System.out.println("1 -> Paciente");
        System.out.println("2 -> ADM");
    }

    public void lerEntrada() {
        this.opcao = leitorEntrada.nextNum();
        leitorEntrada.nextEspaco();
    }

    public void escolherMenu() {
        switch (opcao) {
            case 0 -> menuMedico();
            case 1 -> menuPaciente();
            case 2 -> menuAdm();
            default -> System.out.println("Opcao invalida");
        }
    }

    private void menuMedico() {
        Medico medico = new Medico();
        System.out.println("=== Cadastro de medico ===");

        System.out.println("Nome:");
        medico.setNome(leitorEntrada.nextText());

        System.out.println("CRM:");
        medico.setCrm(leitorEntrada.nextNum());
        leitorEntrada.nextEspaco();

        System.out.println("Valor da consulta:");
        medico.setCustoConsulta(leitorEntrada.nextNum());
        leitorEntrada.nextEspaco();

        System.out.println("Escolha a especialidade:");
        exibirOpcoes(especialidades);
        int especialidadeIndice = lerIndiceValido(especialidades.size());
        medico.setEspecialidade(especialidades.get(especialidadeIndice));

        List<String> planosAceitos = new ArrayList<>();
        System.out.println("Selecione os planos aceitos (digite 7 para encerrar):");
        exibirPlanos();
        while (true) {
            int codigoPlano = leitorEntrada.nextNum();
            leitorEntrada.nextEspaco();
            if (codigoPlano == 7) {
                break;
            }
            if (codigoPlano >= 0 && codigoPlano < planos.size()) {
                String plano = planos.get(codigoPlano);
                if (!planosAceitos.contains(plano)) {
                    planosAceitos.add(plano);
                    System.out.println("Plano adicionado: " + plano);
                } else {
                    System.out.println("Plano já adicionado.");
                }
            } else {
                System.out.println("Opcao invalida, tente novamente.");
            }
        }
        medico.setPlanosAceitos(planosAceitos);

        System.out.println("Quantos horarios deseja cadastrar?");
        int quantidade = leitorEntrada.nextNum();
        leitorEntrada.nextEspaco();
        for (int i = 1; i <= quantidade; i++) {
            LocalDateTime horario = lerHorarioDisponivel(i);
            if (!medico.getAgendaHorario().contains(horario)) {
                medico.adicionarHorarioDisponivel(horario);
            } else {
                System.out.println("Horario duplicado ignorado.");
            }
        }

        medicoDaos.salvarMedico(medico);
        System.out.println("Medico cadastrado com sucesso.");

        gerenciarConsultasMedico(medico.getCrm());
    }

    private void menuPaciente() {
        System.out.println("=== Cadastro de paciente ===");
        Paciente paciente;

        System.out.println("Possui plano de saude?");
        System.out.println("0 -> Sim");
        System.out.println("1 -> Nao");
        int temPlano;
        while (true) {
            temPlano = leitorEntrada.nextNum();
            leitorEntrada.nextEspaco();
            if (temPlano == 0 || temPlano == 1) {
                break;
            }
            System.out.println("Opcao invalida, tente novamente.");
        }

        if (temPlano == 0) {
            PacienteEspecial pacienteEspecial = new PacienteEspecial();
            pacienteEspecial.setTemRegistroPlano(true);
            System.out.println("Selecione o plano de saude:");
            exibirPlanos();
            int planoIndice = lerIndiceValido(planos.size());
            pacienteEspecial.setNomePlanoDeSaude(planos.get(planoIndice));
            System.out.println("Informe o numero do registro do plano:");
            pacienteEspecial.setNumeroRegistroPlano(leitorEntrada.nextNum());
            leitorEntrada.nextEspaco();
            paciente = pacienteEspecial;
        } else {
            PacienteComum pacienteComum = new PacienteComum();
            pacienteComum.setTemRegistroPlano(false);
            pacienteComum.setNomePlanoDeSaude("");
            paciente = pacienteComum;
        }

        System.out.println("Nome:");
        paciente.setNome(leitorEntrada.nextText());

        System.out.println("CPF:");
        paciente.setCpf(leitorEntrada.nextText());

        System.out.println("Idade:");
        paciente.setIdade(leitorEntrada.nextNum());
        leitorEntrada.nextEspaco();

        pacienteDaos.salvarPaciente(paciente);
        System.out.println("Paciente cadastrado com sucesso.");

        oferecerAgendamentoParaPaciente(paciente);
        gerenciarConsultasPaciente(paciente);
    }

    private void menuAdm() {
        System.out.println("== Menu ADM ==");
        System.out.println("Escolha a opcao desejada:");
        System.out.println("0 -> Configuracoes de pacientes");
        System.out.println("1 -> Configuracoes de medicos");
        System.out.println("2 -> Internacoes");
        int escolha = leitorEntrada.nextNum();
        leitorEntrada.nextEspaco();

        switch (escolha) {
            case 0 -> menuAdmPacientes();
            case 1 -> menuAdmMedicos();
            case 2 -> menuAdmInternacoes();
            default -> System.out.println("Opcao invalida");
        }
    }

    private void menuAdmPacientes() {
        System.out.println("Opcoes:");
        System.out.println("0 -> Listar pacientes");
        System.out.println("1 -> Buscar por nome");
        System.out.println("2 -> Buscar por CPF");
        System.out.println("3 -> Buscar por plano de saude");
        int escolha = leitorEntrada.nextNum();
        leitorEntrada.nextEspaco();
        switch (escolha) {
            case 0 -> System.out.println(pacienteDaos.listarPaciente());
            case 1 -> pacienteDaos.buscaPorNome();
            case 2 -> pacienteDaos.buscaPorCpf();
            case 3 -> pacienteDaos.buscaPorPlanoDeSaude();
            default -> System.out.println("Opcao invalida");
        }
    }

    private void menuAdmMedicos() {
        System.out.println("Opcoes:");
        System.out.println("0 -> Listar medicos");
        System.out.println("1 -> Buscar por nome");
        System.out.println("2 -> Buscar por CRM");
        System.out.println("3 -> Buscar por especialidade");
        System.out.println("4 -> Buscar por plano aceito");
        int escolha = leitorEntrada.nextNum();
        leitorEntrada.nextEspaco();
        switch (escolha) {
            case 0 -> listarMedicosCadastrados();
            case 1 -> medicoDaos.buscaPorNomeMedico();
            case 2 -> medicoDaos.buscaPorCrm();
            case 3 -> medicoDaos.buscaPorEspecialidade();
            case 4 -> medicoDaos.buscaPorPlanoAceito();
            default -> System.out.println("Opcao invalida");
        }
    }

    private void gerenciarConsultasPaciente(Paciente paciente) {
        while (true) {
            System.out.println("== Consultas do paciente ==");
            System.out.println("0 -> Listar minhas consultas");
            System.out.println("1 -> Cancelar uma consulta por ID");
            System.out.println("2 -> Voltar");
            int escolha = leitorEntrada.nextNum();
            leitorEntrada.nextEspaco();
            if (escolha == 2) return;
            switch (escolha) {
                case 0 -> listarConsultasPaciente(paciente.getCpf());
                case 1 -> {
                    listarConsultasPaciente(paciente.getCpf());
                    System.out.println("Informe o codigo (ID) da consulta a cancelar:");
                    String id = leitorEntrada.nextText();
                    try {
                        agendamentoConsultas.atualizarStatus(id, StatusConsulta.CANCELADA);
                        System.out.println("Consulta cancelada.");
                    } catch (RuntimeException e) {
                        System.out.println("Falha ao cancelar: " + e.getMessage());
                    }
                }
                default -> System.out.println("Opcao invalida");
            }
        }
    }

    private void gerenciarConsultasMedico(int crm) {
        while (true) {
            System.out.println("== Consultas do medico ==");
            System.out.println("0 -> Listar minhas consultas");
            System.out.println("1 -> Cancelar uma consulta por ID");
            System.out.println("2 -> Concluir uma consulta por ID");
            System.out.println("3 -> Voltar");
            int escolha = leitorEntrada.nextNum();
            leitorEntrada.nextEspaco();
            if (escolha == 3) return;
            switch (escolha) {
                case 0 -> listarConsultasMedico(crm);
                case 1 -> {
                    listarConsultasMedico(crm);
                    System.out.println("Informe o codigo (ID) da consulta a cancelar:");
                    String id = leitorEntrada.nextText();
                    try {
                        agendamentoConsultas.atualizarStatus(id, StatusConsulta.CANCELADA);
                        System.out.println("Consulta cancelada.");
                    } catch (RuntimeException e) {
                        System.out.println("Falha ao cancelar: " + e.getMessage());
                    }
                }
                case 2 -> {
                    listarConsultasMedico(crm);
                    System.out.println("Informe o codigo (ID) da consulta a concluir:");
                    String id = leitorEntrada.nextText();
                    System.out.println("Digite o diagnostico (ou deixe em branco):");
                    String diag = leitorEntrada.nextText();
                    System.out.println("Digite a prescricao (ou deixe em branco):");
                    String presc = leitorEntrada.nextText();
                    try {
                        agendamentoConsultas.concluirConsulta(id, diag, presc);
                        System.out.println("Consulta concluida com sucesso.");
                    } catch (RuntimeException e) {
                        System.out.println("Falha ao concluir: " + e.getMessage());
                    }
                }
                default -> System.out.println("Opcao invalida");
            }
        }
    }

    private void listarConsultasPaciente(String cpf) {
        List<consultaDAO.ConsultaRegistro> registros = consultaDaos.listarPorPacienteCpf(cpf);
        if (registros.isEmpty()) {
            System.out.println("Nenhuma consulta encontrada.");
            return;
        }
        for (consultaDAO.ConsultaRegistro r : registros) {
            String base = r.id + " | Medico: " + r.nomeMedico + " (CRM " + r.crmMedico + ") | "
                + r.dataHora.format(dataHoraFormatter) + " | Local: " + r.local + " | Status: " + r.status
                + " | Valor: R$ " + formatValor(r.valorFinal);
            if (r.status == StatusConsulta.CONCLUIDA) {
                if (r.diagnostico != null && !r.diagnostico.isBlank()) {
                    base += " | Diagnostico: " + r.diagnostico;
                }
                if (r.prescricao != null && !r.prescricao.isBlank()) {
                    base += " | Prescricao: " + r.prescricao;
                }
            }
            System.out.println(base);
        }
    }

    private void listarConsultasMedico(int crm) {
        List<consultaDAO.ConsultaRegistro> registros = consultaDaos.listarPorMedicoCrm(crm);
        if (registros.isEmpty()) {
            System.out.println("Nenhuma consulta encontrada.");
            return;
        }
        for (consultaDAO.ConsultaRegistro r : registros) {
            String base = r.id + " | Paciente: " + r.nomePaciente + " (CPF " + r.cpfPaciente + ") | "
                + r.dataHora.format(dataHoraFormatter) + " | Local: " + r.local + " | Status: " + r.status
                + " | Valor: R$ " + formatValor(r.valorFinal);
            if (r.status == StatusConsulta.CONCLUIDA) {
                if (r.diagnostico != null && !r.diagnostico.isBlank()) {
                    base += " | Diagnostico: " + r.diagnostico;
                }
                if (r.prescricao != null && !r.prescricao.isBlank()) {
                    base += " | Prescricao: " + r.prescricao;
                }
            }
            System.out.println(base);
        }
    }

    private void oferecerAgendamentoParaPaciente(Paciente paciente) {
        System.out.println("Deseja agendar uma consulta agora?");
        System.out.println("0 -> Sim");
        System.out.println("1 -> Nao");
        int resposta = leitorEntrada.nextNum();
        leitorEntrada.nextEspaco();
        if (resposta != 0) {
            return;
        }

        List<Medico> medicos = medicoDaos.listarMedicos();
        if (medicos.isEmpty()) {
            System.out.println("Nao ha medicos cadastrados no momento.");
            return;
        }

        System.out.println("Selecione o medico desejado:");
        for (int i = 0; i < medicos.size(); i++) {
            Medico m = medicos.get(i);
            System.out.println(i + " -> " + m.getNome() + " | CRM " + m.getCrm() + " | " + m.getEspecialidade());
        }
        int indiceMedico = lerIndiceValido(medicos.size());
        Medico medicoSelecionado = medicos.get(indiceMedico);

        List<LocalDateTime> horariosDisponiveis = new ArrayList<>(medicoSelecionado.getAgendaHorario());
        horariosDisponiveis.sort(Comparator.naturalOrder());
        if (horariosDisponiveis.isEmpty()) {
            System.out.println("O medico escolhido nao possui horarios cadastrados.");
            return;
        }

        System.out.println("Selecione o horario:");
        for (int i = 0; i < horariosDisponiveis.size(); i++) {
            System.out.println(i + " -> " + horariosDisponiveis.get(i).format(dataHoraFormatter));
        }
        int indiceHorario = lerIndiceValido(horariosDisponiveis.size());
        LocalDateTime dataHora = horariosDisponiveis.get(indiceHorario);

        System.out.println("Informe o local da consulta:");
        System.out.println("Locais Disponíveis: ");
        System.out.println("0 -> Hospital Brasília");
        System.out.println("1 -> Hospital DF Star");
        System.out.println("2 -> Hospital Santa Luzia");
        System.out.println("3 -> Hospital Santa Marta");
        System.out.println("4 -> Hospital HOME");
        Integer localInteger = leitorEntrada.nextNum();
        leitorEntrada.nextEspaco();
        switch (localInteger) {
            case 0 -> setLocal("Hospital Brasília");
            case 1 -> setLocal("Hospital DF Star");
            case 2 -> setLocal("Hospital Santa Luzia");
            case 3 -> setLocal("Hospital Santa Marta");
            case 4 -> setLocal("Hospital HOME");
            default -> System.out.println("Digite uma opção válida");
        }

        try {
            Consulta consulta = agendamentoConsultas.agendarConsulta(paciente, medicoSelecionado, dataHora, local);
            System.out.println("Consulta agendada com sucesso!");
            System.out.println("Codigo: " + consulta.getId());
            System.out.println("Medico: " + medicoSelecionado.getNome() + " (CRM " + medicoSelecionado.getCrm() + ")");
            System.out.println("Data e hora: " + dataHora.format(dataHoraFormatter));
            System.out.println("Local: " + consulta.getLocal());
            System.out.println("Status: " + consulta.getStatus());
            System.out.println("Valor final: R$ " + formatValor(consulta.getValorFinal())
                + " (desconto " + Math.round(consulta.getDescontoPercentual() * 100) + "%)");
        } catch (RuntimeException e) {
            System.out.println("Nao foi possivel agendar a consulta: " + e.getMessage());
        }
    }

    

    private void listarMedicosCadastrados() {
        List<Medico> medicos = medicoDaos.listarMedicos();
        if (medicos.isEmpty()) {
            System.out.println("Nenhum medico cadastrado.");
            return;
        }
        for (Medico medico : medicos) {
            System.out.println(medico);
        }
    }

    private void exibirOpcoes(List<String> opcoes) {
        for (int i = 0; i < opcoes.size(); i++) {
            System.out.println(i + " -> " + opcoes.get(i));
        }
    }

    private void exibirPlanos() {
        exibirOpcoes(planos);
        System.out.println("7 -> Finalizar selecao");
    }

    private int lerIndiceValido(int limite) {
        while (true) {
            int valor = leitorEntrada.nextNum();
            leitorEntrada.nextEspaco();
            if (valor >= 0 && valor < limite) {
                return valor;
            }
            System.out.println("Opcao invalida, tente novamente.");
        }
    }

    private LocalDateTime lerHorarioDisponivel(int ordem) {
        while (true) {
            System.out.println("Digite a data do horario " + ordem + " (formato dd/MM/yyyy):");
            String data = leitorEntrada.nextText();
            System.out.println("Digite a hora do horario " + ordem + " (formato HH:mm):");
            String hora = leitorEntrada.nextText();
            try {
                return LocalDateTime.parse(data + " " + hora, dataHoraFormatter);
            } catch (RuntimeException e) {
                System.out.println("Horario invalido, tente novamente.");
            }
        }
    }

    private String formatValor(double valor) {
        return String.format(Locale.US, "%.2f", valor);
    }

    private void setLocal(String local) {
        this.local = local;
    }

    private void menuAdmInternacoes() {
        while (true) {
            System.out.println("== Internacoes ==");
            System.out.println("0 -> Cadastrar internação");
            System.out.println("1 -> Listar internações");
            System.out.println("2 -> Dar alta por ID");
            System.out.println("3 -> Cancelar por ID");
            System.out.println("4 -> Voltar");
            int escolha = leitorEntrada.nextNum();
            leitorEntrada.nextEspaco();
            if (escolha == 4) return;
            switch (escolha) {
                case 0 -> cadastrarInternacao();
                case 1 -> listarInternacoes();
                case 2 -> darAltaInternacao();
                case 3 -> cancelarInternacao();
                default -> System.out.println("Opcao invalida");
            }
        }
    }

    private void cadastrarInternacao() {
        List<Paciente> pacientes = pacienteDaos.listarPaciente();
        if (pacientes.isEmpty()) {
            System.out.println("Nao ha pacientes cadastrados.");
            return;
        }
        System.out.println("Selecione o paciente:");
        for (int i = 0; i < pacientes.size(); i++) {
            System.out.println(i + " -> " + pacientes.get(i).getNome() + " | CPF: " + pacientes.get(i).getCpf());
        }
        int idxPaciente = lerIndiceValido(pacientes.size());
        Paciente paciente = pacientes.get(idxPaciente);

        List<Medico> medicos = medicoDaos.listarMedicos();
        if (medicos.isEmpty()) {
            System.out.println("Nao ha medicos cadastrados.");
            return;
        }
        System.out.println("Selecione o medico responsavel:");
        for (int i = 0; i < medicos.size(); i++) {
            Medico m = medicos.get(i);
            System.out.println(i + " -> " + m.getNome() + " | CRM: " + m.getCrm() + " | " + m.getEspecialidade());
        }
        int idxMedico = lerIndiceValido(medicos.size());
        Medico medico = medicos.get(idxMedico);

        System.out.println("Digite a data de entrada (dd/MM/yyyy):");
        String data = leitorEntrada.nextText();
        System.out.println("Digite a hora de entrada (HH:mm):");
        String hora = leitorEntrada.nextText();
        LocalDateTime entrada;
        try {
            entrada = LocalDateTime.parse(data + " " + hora, dataHoraFormatter);
        } catch (RuntimeException e) {
            System.out.println("Data/hora invalida.");
            return;
        }

        System.out.println("Informe o quarto (texto ou numero):");
        String quarto = leitorEntrada.nextText();

        for (internacaoDAO.InternacaoRegistro r : internacaoDaos.listarAtivas()) {
            if (r.quarto != null && r.quarto.equalsIgnoreCase(quarto)) {
                System.out.println("Quarto ja ocupado por outra internacao ativa (ID: " + r.id + ")");
                return;
            }
        }

        System.out.println("Informe o custo da internacao (ex: 1500.00):");
        double custo = leitorEntrada.nextNum();
        leitorEntrada.nextEspaco();

        try {
            Internacao internacao = new Internacao(paciente, medico, entrada, quarto, custo);
            internacaoDaos.salvar(internacao);
            String resumo = "Internacao " + internacao.getId() + " | Quarto: " + quarto + " | Entrada: " + entrada;
            pacienteDaos.atualizarHistoricoConsulta(paciente.getCpf(), resumo);
            System.out.println("Internacao registrada com sucesso. ID: " + internacao.getId());
        } catch (RuntimeException e) {
            System.out.println("Falha ao registrar internacao: " + e.getMessage());
        }
    }

    private void listarInternacoes() {
        List<internacaoDAO.InternacaoRegistro> regs = internacaoDaos.listarTodos();
        if (regs.isEmpty()) {
            System.out.println("Nenhuma internacao encontrada.");
            return;
        }
        for (internacaoDAO.InternacaoRegistro r : regs) {
            String base = r.id + " | Paciente: " + r.nomePaciente + " | Medico: " + r.nomeMedico
                    + " | Quarto: " + r.quarto + " | Entrada: " + (r.dataEntrada != null ? r.dataEntrada.format(dataHoraFormatter) : "-")
                    + " | Saida: " + (r.dataSaida != null ? r.dataSaida.format(dataHoraFormatter) : "-")
                    + " | Status: " + r.status + " | Custo: R$ " + formatValor(r.custo);
            System.out.println(base);
        }
    }

    private void darAltaInternacao() {
        listarInternacoes();
        System.out.println("Digite o ID da internacao para dar alta:");
        String id = leitorEntrada.nextText();
        System.out.println("Digite a data de saida (dd/MM/yyyy):");
        String data = leitorEntrada.nextText();
        System.out.println("Digite a hora de saida (HH:mm):");
        String hora = leitorEntrada.nextText();
        LocalDateTime saida;
        try {
            saida = LocalDateTime.parse(data + " " + hora, dataHoraFormatter);
        } catch (RuntimeException e) {
            System.out.println("Data/hora invalida.");
            return;
        }
        boolean ok = internacaoDaos.darAlta(id, saida);
        if (ok) {
            internacaoDaos.buscarPorId(id).ifPresent(r -> {
                String resumo = "Alta de internacao " + r.id + " | Quarto: " + r.quarto + " | Saida: " + saida;
                pacienteDaos.atualizarHistoricoConsulta(r.cpfPaciente, resumo);
            });
            System.out.println("Alta registrada.");
        } else {
            System.out.println("Nao foi possivel dar alta (ID invalido ou ja encerrada).");
        }
    }

    private void cancelarInternacao() {
        listarInternacoes();
        System.out.println("Digite o ID da internacao para cancelar:");
        String id = leitorEntrada.nextText();
        boolean ok = internacaoDaos.cancelar(id);
        if (ok) {
            internacaoDaos.buscarPorId(id).ifPresent(r -> {
                String resumo = "Cancelamento de internacao " + r.id + " | Quarto: " + r.quarto;
                pacienteDaos.atualizarHistoricoConsulta(r.cpfPaciente, resumo);
            });
            System.out.println("Internacao cancelada.");
        } else {
            System.out.println("Nao foi possivel cancelar (ID invalido ou ja encerrada).");
        }
    }
}
