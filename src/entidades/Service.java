package entidades;

import DAOS.medicoDAO;
import DAOS.pacienteDAO;

public class Service {
    
    private inputScanner leitorEntrada;
    private int opcao;
    private final Medico medico = new Medico();
    private final Paciente paciente = new Paciente();
    pacienteDAO pacientedaos = new pacienteDAO();
    medicoDAO medicodaos = new medicoDAO();

    public void exibirMenu(){
        while(true) {System.out.println("== SISTEMA DE GERENCIAMENTO DE HOSPITALAR ==");
        System.out.println("Opções:");
        System.out.println("Aperte 0 se for um médico");
        System.out.println("Aperte 1 se for um paciente");
        System.out.println("Aperte 2 se for um ADM");
    }}
    
    public void lerEntrada(){
        this.leitorEntrada = new inputScanner();
        this.opcao = leitorEntrada.nextNum();
        System.out.println("Você escolheu a opção "+ opcao);
        }

    public void menuMedico(){
        medicodaos.criarArquivoMedico();
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
        medico.setEspecialidade(leitorEntrada.nextNum());

        System.out.println("Cadastro concluido com sucesso");
        medico.exibirDadosMedico();
        medicodaos.salvarMedico(medico);
    }
         
    public void menuPaciente(){
        pacientedaos.criarArquivoPaciente();
        this.leitorEntrada = new inputScanner();
        System.out.println("=== Cadastro de Paciente ===");

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

        System.out.println("Listagem de Pacientes");
        System.out.println(pacientedaos.listarPaciente());

        System.out.println("Listagem de Medicos");
        System.out.println(medicodaos.listarMedicos());
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


