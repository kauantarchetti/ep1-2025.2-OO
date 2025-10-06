package entidades;

import DAOS.medicoDAO;
import DAOS.pacienteDAO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;



public class Service {
    
    private inputScanner leitorEntrada;
    private int opcao;
    private int especialidadeEscolhida;
    private int temPlanoDeSaudePaciente;
    private int planoDeSaudePaciente;
    private int escolhaMenu;
    private int escolhaPaciente;
    private int escolhaMedico;
    private int entradaRegistroPlano;
    private int querPlanoDeSaude;
    private final Medico medico = new Medico();
    private final Paciente paciente = new Paciente();
    private final PlanoDeSaude planodesaude = new PlanoDeSaude();
    pacienteDAO pacientedaos = new pacienteDAO();
    medicoDAO medicodaos = new medicoDAO();

    public void exibirMenu(){
        System.out.println("== SISTEMA DE GERENCIAMENTO DE HOSPITALAR ==");
        System.out.println("Opções:");
        System.out.println("Aperte 0 se for um médico");
        System.out.println("Aperte 1 se for um paciente");
        System.out.println("Aperte 2 se for um ADM");
    }
    
    public void lerEntrada(){
        this.leitorEntrada = new inputScanner();
        this.opcao = leitorEntrada.nextNum();
        System.out.println("Você escolheu a opção "+ opcao);
        }

    public void menuMedico(){
        this.leitorEntrada = new inputScanner();
        

        System.out.println("=== Cadastro de médico ===");

        System.out.println("Qual o seu nome?");
        medico.setNome(leitorEntrada.nextText());

        System.out.println("Qual o seu CRM?");
        medico.setCrm(leitorEntrada.nextNum());
        leitorEntrada.nextEspaco();

        System.out.println("Quanto custa sua consulta?");
        medico.setCustoConsulta(leitorEntrada.nextNum());
        leitorEntrada.nextEspaco();
      
        System.out.println("Qual a sua especialidade?");
        System.out.println("Opções: ");
        System.out.println("0 -> Médico geral");
        System.out.println("1 -> Ortopedista");
        System.out.println("2 -> Cirurgião");
        System.out.println("3 -> Pediatra");
        System.out.println("4 -> Oftalmo");
        System.out.println("5 -> Psiquiatra");
        System.out.println("6 -> Dermatologista");
        System.out.println("7 -> Cardiologista");
        System.out.println("8 -> Ginecologista");
        System.out.println("9 -> Urologista");
        while (true){
            this.especialidadeEscolhida = leitorEntrada.nextNum();
            if (especialidadeEscolhida >= 0 && especialidadeEscolhida <= 9){
                break;
            }
            System.out.println("Digite uma opção válida");
        }
        System.out.println("Você escolheu a especialidade "+ especialidadeEscolhida);

        switch(especialidadeEscolhida){
            case 0 -> medico.setEspecialidade("Médico geral");
            case 1 -> medico.setEspecialidade("Ortopedista");
            case 2 -> medico.setEspecialidade("Cirurgião");
            case 3 -> medico.setEspecialidade("Pediatra");
            case 4 -> medico.setEspecialidade("Oftalmo");
            case 5 -> medico.setEspecialidade("Psiquiatra");
            case 6 -> medico.setEspecialidade("Dermatologista");
            case 7 -> medico.setEspecialidade("Cardiologista");
            case 8 -> medico.setEspecialidade("Ginecologista");
            case 9 -> medico.setEspecialidade("Urologista");
            default -> {System.out.println("Digite uma opção válida"); this.especialidadeEscolhida = leitorEntrada.nextNum(); System.out.println("Você escolheu a especialidade "+ especialidadeEscolhida);}
        }

        this.leitorEntrada = new inputScanner();
        List<String> nomesPlanos = Array.asList(
            "Cassi",
            "Amil",
            "Porto Seguro",
            "Unimed",
            "Sulamerica",
            "Bradesco Saúde",
            "Saúde Caixa"
        );
        List<String> planosAceitos = new ArrayList<>();
        System.out.println("Quais planos de saúde você atende?");
        System.out.println("Opções: ");
        System.out.println("0 -> Cassi");
        System.out.println("1 -> Amil");
        System.out.println("2 -> Porto Seguro");
        System.out.println("3 -> Unimed");
        System.out.println("4 -> Sulamerica");
        System.out.println("5 -> Bradesco Saúde");
        System.out.println("6 -> Saúde Caixa");
        System.out.println("Digite o 7 para parar");

        while(true){
            int valor = leitorEntrada.nextNum();
            if (valor == 7) break;
            if (valor >= 0  && valor < nomesPlanos.size()) {
                planosAceitos.add(nomesPlanos.get(valor));
            } else {
                System.out.println("Opção Inválida, Tente Novamente");
            }
         }
         
        medico.setPlanosAceitos(planosAceitos);

        this.leitorEntrada = new inputScanner();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.println("Quantos horários deseja cadastrar como médico?");
        Integer quantidade = leitorEntrada.nextNum();

        for (int i =0; i < quantidade; i++){
            System.out.println("Digite o dia disponível (Formato dd)");
            String horarioDisponivel = leitorEntrada.nextText();
        leitorEntrada.nextEspaco();
        
        for (int z =0; z < quantidade; z++){
            System.out.println("Digite a data disponível (Formato dd/MM/yyyy)");
            String data = leitorEntrada.nextText();
            System.out.println("Digite o horário disponível (Formato HH:mm)");
            String hora = leitorEntrada.nextText();
            try {
                LocalDateTime horario = LocalDateTime.parse(data + " " + hora, formatter);
                medico.adicionarHorarioDisponivel(horario);
            }catch (Exception e) {
                System.out.println("Horário inválido, tente novamente");
                i--;}


            
        }
        
        System.out.println("Cadastro concluido com sucesso");
        medico.exibirDadosMedico();
        medicodaos.salvarMedico(medico);
    }}
         
    public void menuPaciente(){
        this.leitorEntrada = new inputScanner();
        System.out.println("=== Cadastro de Paciente ===");

        System.out.println("Tem plano de saúde?");
        System.out.println("0 -> Sim");
        System.out.println("1 -> Não");
        this.temPlanoDeSaudePaciente = leitorEntrada.nextNum();
        switch(temPlanoDeSaudePaciente){
            case 0:
                paciente.setTemRegistroPlano(true);
                System.out.println("Qual seu plano de saúde?");
                System.out.println("Opções: ");
                System.out.println("0 -> Cassi");
                System.out.println("1 -> Amil");
                System.out.println("2 -> Porto Seguro");
                System.out.println("3 -> Unimed");
                System.out.println("4 -> Sulamerica");
                System.out.println("5 -> Bradesco Saúde");
                System.out.println("6 -> Saúde Caixa");
                this.planoDeSaudePaciente = leitorEntrada.nextNum();
                System.out.println("Você escolheu a opção: "+ planoDeSaudePaciente);
                switch(planoDeSaudePaciente){
                    case 0 -> planodesaude.setNome("Cassi");
                    case 1 -> planodesaude.setNome("Amil");
                    case 2 -> planodesaude.setNome("Porto Seguro");
                    case 3 -> planodesaude.setNome("Unimed");
                    case 4 -> planodesaude.setNome("Sulamerica");
                    case 5 -> planodesaude.setNome("Bradesco Saúde");
                    case 6 -> planodesaude.setNome("Saúde Caixa");
                }
                System.out.println("Qual o seu número de registro do plano?");
                this.leitorEntrada = new inputScanner();
                this.entradaRegistroPlano = leitorEntrada.nextNum();
                leitorEntrada.nextEspaco();
                break;
                
            case 1:
            paciente.setTemRegistroPlano(false);
            System.out.println("Deseja fazer um plano de saúde?");
            System.out.println("0 -> Sim");
            System.out.println("1 -> Não");
            this.querPlanoDeSaude = leitorEntrada.nextNum();
            leitorEntrada.nextEspaco();



            default:
            System.out.println("Digite uma opção válida");
        }


        
        System.out.println("Qual o seu nome?");
        paciente.setNome(leitorEntrada.nextText());

        System.out.println("Qual o seu CPF?");
        paciente.setCpf(leitorEntrada.nextText());

        System.out.println("Qual é a sua idade?");
        paciente.setIdade(leitorEntrada.nextNum());
        leitorEntrada.nextEspaco();

        pacientedaos.salvarPaciente(paciente);
        
    }

    public void menuAdm(){
        System.out.println("== Menu ADM ==");
        System.out.println("Escolha as configurações que deseja acessar: ");
        System.out.println("Opções: ");
        System.out.println("0 -> Configurações dos Pacientes");
        System.out.println("1 -> Configurações dos Médicos");
        this.leitorEntrada = new inputScanner();
        this.escolhaMenu = leitorEntrada.nextNum();
        switch(escolhaMenu){
            case 0:
            System.out.println("Entrou nas configurações de paciente");
            System.out.println("Qual configuração deseja acessar: ");
            System.out.println("Opções: ");
            System.out.println("0 -> Listagem de Pacientes");
            System.out.println("1 -> Consulta por Nome");
            System.out.println("2 -> Consulta por Cpf");
            System.out.println("3 -> Consulta por Plano de saúde");
            this.escolhaPaciente = leitorEntrada.nextNum();
            switch(escolhaPaciente){
                case 0 -> System.out.println(pacientedaos.listarPaciente());
                case 1 -> pacientedaos.buscaPorNome();
                case 2 -> pacientedaos.buscaPorCpf();
                case 3 -> pacientedaos.buscaPorPlanoDeSaude();
                default -> System.out.println("Digite uma opção válida");
            }
            case 1:
            System.out.println("Entrou nas configurações de médico");
            System.out.println("Qual configuração deseja acessar: ");
            System.out.println("Opções: ");
            System.out.println("0 -> Listagem de Médicos");
            System.out.println("1 -> Consulta por Nome");
            System.out.println("2 -> Consultar por Crm");
            System.out.println("3 -> Consultar por Especialidade");
            this.escolhaMedico = leitorEntrada.nextNum();
            switch(escolhaMedico){
                case 0 -> medicodaos.listarMedicos();
                case 1 -> medicodaos.buscaPorNomeMedico();
                case 2 -> medicodaos.buscaPorCrm();
                case 3 -> medicodaos.buscaPorEspecialidade();
                default -> menuAdm();
            }


            default:
            System.out.println("Digite uma opção válida");
        }
    }

    public void escolherMenu(){
        switch (opcao) {
            case 0 -> menuMedico();
            case 1 -> menuPaciente();
            case 2 -> menuAdm();
            default -> System.out.println("Opção inválida");
        }
    }   
    }


