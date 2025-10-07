package DAOS;
import entidades.Paciente;
import java.io.*;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import entidades.PlanoDeSaude;
import entidades.inputScanner;

public class pacienteDAO {  
    private static final String FILE_NAME = "pacientes.json";
    private final Gson gson = new Gson();
    private inputScanner entrada;

    public void salvarPaciente(Paciente paciente){
        List<Paciente> pacientes = listarPaciente();
        pacientes.add(paciente);

        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            gson.toJson(pacientes, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar paciente" + e.getMessage());
        }
    }

    public List<Paciente> listarPaciente(){
        try (Reader reader = new FileReader(FILE_NAME)){
            Type pacienteListType = new TypeToken<List<Paciente>>() {}.getType();
            List<Paciente> pacientes = gson.fromJson(reader, pacienteListType);
            return pacientes != null ? pacientes : new ArrayList<>();
        }

        catch(IOException e){
            return new ArrayList<>();
        }
    }

    public void buscaPorCpf(){
        this.entrada = new inputScanner();
        System.out.println("Digite o cpf do paciente que você deseja consultar: ");
        String cpfDigitado = entrada.nextText();

        for (Paciente paciente : listarPaciente()){
            if(paciente.getCpf().equals(cpfDigitado)){
                System.out.println("Paciente encontrado: "+ paciente.getNome());
                return;
            }
        }
        System.out.println("Paciente não encontrado");
    }

    public void buscaPorNome(){
        this.entrada = new inputScanner();
        System.out.println("Digite o nome do paciente que você deseja consultar: ");
        String nomePacienteDigitado = entrada.nextText();

        for (Paciente paciente: listarPaciente()){
            if(paciente.getNome().equals(nomePacienteDigitado)){
                System.out.println("Paciente encontrado: "+ paciente.getNome() + ";" + paciente.getCpf());
                return;
            }
        }
        System.out.println("Paciente não encontrado");
    }

    public void buscaPorPlanoDeSaude(){
        this.entrada = new inputScanner();
        String planoDeSaudePacienteText = null;
        System.out.println("Digite o número correspondente ao Plano de saúde do paciente que você deseja consultar: ");
        System.out.println("Opções: ");
        System.out.println("0 -> Cassi");
        System.out.println("1 -> Amil");
        System.out.println("2 -> Porto Seguro");
        System.out.println("3 -> Unimed");
        System.out.println("4 -> Sulamerica");
        System.out.println("5 -> Bradesco Saúde");
        System.out.println("6 -> Saúde Caixa");
        Integer planoDeSaudePacienteNum = entrada.nextNum();
        switch(planoDeSaudePacienteNum){
            case 0 -> planoDeSaudePacienteText = "Cassi";
            case 1 -> planoDeSaudePacienteText = "Amil";
            case 2 -> planoDeSaudePacienteText = "Porto Seguro";
            case 3 -> planoDeSaudePacienteText = "Unimed";
            case 4 -> planoDeSaudePacienteText = "Sulamerica";
            case 5 -> planoDeSaudePacienteText = "Bradesco Saúde";
            case 6 -> planoDeSaudePacienteText = "Saúde Caixa";
            default -> {
                System.out.println("Digite um número válido");
                return;
            }
        }
        boolean encontrou = false;
        for (Paciente paciente: listarPaciente()){
            if(planoDeSaudePacienteText.equals(paciente.getNomePlanoDeSaude())){
                System.out.println("Usuários por plano de saúde encontrados: "+ paciente.getNome());
                encontrou = true;
            }
        }
        if(!encontrou){
            System.out.println("Nenhum paciente encontrado para o plano informado");
        }
        }
    }



