package entidades;

import DAOS.medicoDAO;
import DAOS.pacienteDAO;

public class Service {
    
    private inputScanner leitorEntrada;
    private int opcao;
    private int especialidadeEscolhida;
    private int temPlanoDeSaudePaciente;
    private int planoDeSaudePaciente;
    private int escolhaMenu;
    private int escolhaPaciente;
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
        this.especialidadeEscolhida = leitorEntrada.nextNum();
        System.out.println("Você escolheu a especialidade "+ especialidadeEscolhida);

        switch(especialidadeEscolhida){
            case 0 -> medico.setEspecialidade("Médico geral");
            case 1 -> medico.setEspecialidade("Ortopedista");
            case 2 -> medico.setEspecialidade("Cirurgião");
            case 3 -> medico.setEspecialidade("Pediatria");
            case 4 -> medico.setEspecialidade("Oftalmo");
            case 5 -> medico.setEspecialidade("Psiquiatra");
            case 6 -> medico.setEspecialidade("Dermatologista");
            case 7 -> medico.setEspecialidade("Cardiologista");
            case 8 -> medico.setEspecialidade("Ginecologista");
            case 9 -> medico.setEspecialidade("Urologista");
            default -> System.out.println("Opção inválida");
        }

        System.out.println("Quais planos de saúde você atende?");

        

        System.out.println("Cadastro concluido com sucesso");
        medico.exibirDadosMedico();
        medicodaos.salvarMedico(medico);

        medicodaos.salvarMedico(medico);
    }
         
    public void menuPaciente(){
        this.leitorEntrada = new inputScanner();
        this.temPlanoDeSaudePaciente = leitorEntrada.nextNum();
        System.out.println("=== Cadastro de Paciente ===");

        System.out.println("Tem plano de saúde?");
        System.out.println("0 -> Sim");
        System.out.println("1 -> Não");
        switch(temPlanoDeSaudePaciente){
            case 0:
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
                
            case 1:
            System.out.println("Deseja fazer um plano de saúde?");
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
        this.leitorEntrada = new inputScanner();
        this.escolhaMenu = leitorEntrada.nextNum();
        System.out.println("== Menu ADM ==");
        System.out.println("Escolha as configurações que deseja acessar: ");
        System.out.println("Opções: ");
        System.out.println("0 -> Configurações dos Pacientes");
        System.out.println("1 -> Configurações dos Médicos");
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
                case 1 -> pacientedaos.
                case 2 -> 
                case 3 -> 
            }
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


