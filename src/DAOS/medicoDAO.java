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
        }
    }

    public List<Medico> listarMedicos(){
        try (Reader reader = new FileReader(FILE_NAME)){
            List<Medico> medicos = gson.fromJson(reader, new TypeToken<List<Medico>>(){}.getType());
            return medicos != null ? medicos : new ArrayList<>();
        } catch(IOException e){
            return new ArrayList<>();
        }
    }
    
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
        this.entrada = new inputScanner();
        String especialidadeText = null;
        System.out.println("Digite o número da especialidade que você deseja consultar: ");
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
        Integer especialidadeNum = entrada.nextNum();
        switch(especialidadeNum){
            case 0 -> especialidadeText = "Médigo geral";
            case 1 -> especialidadeText = "Ortopedista";
            case 2 -> especialidadeText = "Cirurgião";
            case 3 -> especialidadeText = "Pediatra";
            case 4 -> especialidadeText = "Oftalmo";
            case 5 -> especialidadeText = "Psiquiatra";
            case 6 -> especialidadeText = "Dermatologista";
            case 7 -> especialidadeText = "Cardiologista";
            case 8 -> especialidadeText = "Ginecologista";
            case 9 -> especialidadeText = "Urologista";
            default -> System.out.println("Digite uma opção válida");
        }
        if(especialidadeText == null){
            return;
        }
        boolean encontrou = false;

        for(Medico medico: listarMedicos()){
            if(medico.getEspecialidade().equals(especialidadeText)){
                System.out.println("Médicos encontrados: "+ medico.getNome());
            }
            else{
                encontrou = true;
            }
        }
        if(!encontrou){
            System.out.println("Médico não encontrado");
        }
    }
    
    public void buscaPorNomeMedico(){
        this.entrada = new inputScanner();
        System.out.println("Digite o nome do médico que você deseja consultar: ");
        String nomeMedicoProcura = entrada.nextText();

        for(Medico medico: listarMedicos()){
            if(medico.getNome().equals(nomeMedicoProcura)){
                System.out.println("Médico encontrado: "+ medico.getNome() + " Crm " + ":" + medico.getCrm());
            }else{
                return;
            }
        }
        System.out.println("Médio não encontrado");
    
    
        this.entrada = new inputScanner();
        System.out.println("Digite o número do plano de saúde que deseja consultar: ");
        System.out.println("Opções: ");
        System.out.println("0 -> Cassi");
        System.out.println("1 -> Amil");
        System.out.println("2 -> Porto Seguro");
        System.out.println("3 -> Unimed");
        System.out.println("4 -> Sulamerica");
        System.out.println("5 -> Bradesco Saúde");
        System.out.println("6 -> Saúde Caixa");
        Integer planoEscolhido = entrada.nextNum();
        List<String> planos = Arrays.asList(
            "Cassi", "Amil", "Porto Seguro", "Unimed", "Sulamerica", "Bradesco Saúde", "Saúde Caixa"
        );
        if(planoEscolhido < 0 || planoEscolhido >= planos.size()){
            System.out.println("Digite um número válido");
            return;
        }
        String planoTexto = planos.get(planoEscolhido);
        boolean encontrou = false;
        for(Medico medico: listarMedicos()){
            if(medico.getPlanosAceitos().contains(planoTexto)){
                System.out.println("Médico encontrado: "+ medico.getNome());
                encontrou = true;
            }
        }
        if(!encontrou){
            System.out.println("Nenhum médico encontrado para o plano informado");
        }
    }}


