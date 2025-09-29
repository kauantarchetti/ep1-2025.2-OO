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
    private Gson gson = new Gson();
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
        List<Paciente> pacientes = new ArrayList<>();
        try (Reader reader = new FileReader(FILE_NAME)){
            Type pacienteListType = new TypeToken<List<Paciente>>() {}.getType();
            return gson.fromJson(reader, pacienteListType);
        }

        catch(IOException e){
            return new ArrayList<>();
        }
    }

    public void buscaPorCpf(){
        this.entrada = new inputScanner();
        System.out.println("Digite o cpf do paciente que você deseja consulta: ");
        String cpfDigitado = entrada.nextText();

        for (Paciente paciente : listarPaciente()){
            if(paciente.getCpf().equals(cpfDigitado.intern())){
                System.out.println("Paciente encontrado: "+ paciente.getNome());
                return;
            }
        }
        System.out.println("Paciente não encontrado");
    }

    public void buscaPorNome(){
        
    }








}