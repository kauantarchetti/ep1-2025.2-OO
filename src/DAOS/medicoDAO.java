package DAOS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import entidades.Medico;
import entidades.inputScanner;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class medicoDAO {
    private static final String FILE_NAME = "medicos.json";
    private final Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
        .create();
    private inputScanner entrada;

    public void salvarMedico(Medico medico) {
        List<Medico> medicos = listarMedicos();
        medicos.add(medico);
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            gson.toJson(medicos, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar medico: " + e.getMessage());
        }
    }

    public List<Medico> listarMedicos() {
        try (Reader reader = new FileReader(FILE_NAME)) {
            List<Medico> medicos = gson.fromJson(reader, new TypeToken<List<Medico>>() {}.getType());
            return medicos != null ? medicos : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public void buscaPorCrm() {
        this.entrada = new inputScanner();
        System.out.println("Digite o CRM do medico que deseja consultar:");
        int crmDigitado = entrada.nextNum();
        entrada.nextEspaco();
        for (Medico medico : listarMedicos()) {
            if (medico.getCrm() == crmDigitado) {
                System.out.println("Medico encontrado: " + medico.getNome());
                return;
            }
        }
        System.out.println("Medico nao encontrado");
    }

    public void buscaPorEspecialidade() {
        this.entrada = new inputScanner();
        System.out.println("Digite o numero da especialidade que deseja consultar:");
        System.out.println("Opcoes:");
        System.out.println("0 -> Medico geral");
        System.out.println("1 -> Ortopedista");
        System.out.println("2 -> Cirurgiao");
        System.out.println("3 -> Pediatra");
        System.out.println("4 -> Oftalmo");
        System.out.println("5 -> Psiquiatra");
        System.out.println("6 -> Dermatologista");
        System.out.println("7 -> Cardiologista");
        System.out.println("8 -> Ginecologista");
        System.out.println("9 -> Urologista");
        int especialidadeNum = entrada.nextNum();
        entrada.nextEspaco();
        List<String> especialidades = Arrays.asList(
            "Medico geral", "Ortopedista", "Cirurgiao", "Pediatra", "Oftalmo",
            "Psiquiatra", "Dermatologista", "Cardiologista", "Ginecologista", "Urologista"
        );
        if (especialidadeNum < 0 || especialidadeNum >= especialidades.size()) {
            System.out.println("Opcao invalida");
            return;
        }
        String especialidadeText = especialidades.get(especialidadeNum);
        boolean encontrou = false;
        for (Medico medico : listarMedicos()) {
            if (especialidadeText.equals(medico.getEspecialidade())) {
                System.out.println("Medico encontrado: " + medico.getNome());
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum medico encontrado para a especialidade informada");
        }
    }

    public void buscaPorNomeMedico() {
        this.entrada = new inputScanner();
        System.out.println("Digite o nome do medico que deseja consultar:");
        String nomeMedicoProcura = entrada.nextText();
        boolean encontrou = false;
        for (Medico medico : listarMedicos()) {
            if (medico.getNome().equalsIgnoreCase(nomeMedicoProcura)) {
                System.out.println("Medico encontrado: " + medico.getNome() + " | CRM: " + medico.getCrm());
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Medico nao encontrado");
        }
    }

    public void buscaPorPlanoAceito() {
        this.entrada = new inputScanner();
        System.out.println("Digite o numero do plano de saude que deseja consultar:");
        System.out.println("Opcoes:");
        System.out.println("0 -> Cassi");
        System.out.println("1 -> Amil");
        System.out.println("2 -> Porto Seguro");
        System.out.println("3 -> Unimed");
        System.out.println("4 -> Sulamerica");
        System.out.println("5 -> Bradesco Saude");
        System.out.println("6 -> Saude Caixa");
        int planoEscolhido = entrada.nextNum();
        entrada.nextEspaco();
        List<String> planos = Arrays.asList(
            "Cassi", "Amil", "Porto Seguro", "Unimed", "Sulamerica", "Bradesco Saude", "Saude Caixa"
        );
        if (planoEscolhido < 0 || planoEscolhido >= planos.size()) {
            System.out.println("Opcao invalida");
            return;
        }
        String planoTexto = planos.get(planoEscolhido);
        boolean encontrou = false;
        for (Medico medico : listarMedicos()) {
            if (medico.getPlanosAceitos().contains(planoTexto)) {
                System.out.println("Medico encontrado: " + medico.getNome());
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum medico encontrado para o plano informado");
        }
    }
}
