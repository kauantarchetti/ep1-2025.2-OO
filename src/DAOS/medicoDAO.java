package DAOS;
import entidades.Medico;
import java.io.*;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entidades.inputScanner;

public class medicoDAO {
    private static final String FILE_NAME = "medicos.json";
    private Gson gson = new Gson();
    private inputScanner entrada;


    public void salvarMedico(Medico medico){
        List<Medico> medicos = listarMedicos();
        medicos.add(medico);

        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            gson.toJson(medicos, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar médico" + e.getMessage());
    }}

    public List<Medico> listarMedicos(){
        List<Medico> medicos = new ArrayList<>();
        try (Reader reader = new FileReader(FILE_NAME)){
            return gson.fromJson(reader, new TypeToken<List<Medico>>(){}.getType());
        
        } catch(IOException e){
            return new ArrayList<>();
        }}
    
    public void  buscaPorCrm(){
        this.entrada = new inputScanner();
        System.out.println("Digite o CRM Do médico que você deseja consultar: ");
        int crmDigitado = entrada.nextNum();

        for (Medico medico : listarMedicos()){
            if(medico.getCrm() == crmDigitado) {
                System.out.println("Médico encontrado: "+ medico.getNome());
                return;
            }
        }
        System.out.println("Médico não encontrado");
    }

    public void buscaPorEspecialidade(){

    }
    
    
    
    
    
    }

