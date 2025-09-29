package DAOS;
import entidades.Paciente;
import java.io.*;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class pacienteDAO {  
    private static final String FILE_NAME = "pacientes.json";
    private Gson gson = new Gson();

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
    }}