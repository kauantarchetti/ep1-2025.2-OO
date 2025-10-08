package DAOS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import entidades.Internacao;
import entidades.StatusInternacao;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class internacaoDAO {
    private static final String FILE_NAME = "internacoes.json";
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public static class InternacaoRegistro {
        public String id;
        public String cpfPaciente;
        public String nomePaciente;
        public int crmMedico;
        public String nomeMedico;
        public LocalDateTime dataEntrada;
        public LocalDateTime dataSaida;
        public String quarto;
        public double custo;
        public StatusInternacao status;
    }

    private List<InternacaoRegistro> carregarTodos() {
        try (Reader reader = new FileReader(FILE_NAME)) {
            List<InternacaoRegistro> lista = gson.fromJson(reader, new TypeToken<List<InternacaoRegistro>>(){}.getType());
            return (lista != null) ? lista : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void salvarTodos(List<InternacaoRegistro> lista) {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar internacoes: " + e.getMessage());
        }
    }

    public void salvar(Internacao internacao) {
        List<InternacaoRegistro> lista = carregarTodos();
        InternacaoRegistro r = new InternacaoRegistro();
        r.id = internacao.getId();
        r.cpfPaciente = internacao.getPaciente().getCpf();
        r.nomePaciente = internacao.getPaciente().getNome();
        r.crmMedico = internacao.getMedicoResponsavel().getCrm();
        r.nomeMedico = internacao.getMedicoResponsavel().getNome();
        r.dataEntrada = internacao.getDataEntrada();
        r.dataSaida = internacao.getDataSaida();
        r.quarto = internacao.getQuarto();
        r.custo = internacao.getCusto();
        r.status = internacao.getStatus();
        lista.add(r);
        salvarTodos(lista);
    }

    public List<InternacaoRegistro> listarTodos() { return carregarTodos(); }

    public List<InternacaoRegistro> listarAtivas() {
        List<InternacaoRegistro> out = new ArrayList<>();
        for (InternacaoRegistro r : carregarTodos()) {
            if (r.status == StatusInternacao.ATIVA) out.add(r);
        }
        return out;
    }

    public Optional<InternacaoRegistro> buscarPorId(String id) {
        for (InternacaoRegistro r : carregarTodos()) {
            if (r.id.equals(id)) return Optional.of(r);
        }
        return Optional.empty();
    }

    public boolean cancelar(String id) {
        List<InternacaoRegistro> lista = carregarTodos();
        boolean alterou = false;
        for (InternacaoRegistro r : lista) {
            if (r.id.equals(id) && r.status != StatusInternacao.ALTA) {
                r.status = StatusInternacao.CANCELADA;
                alterou = true;
                break;
            }
        }
        if (alterou) salvarTodos(lista);
        return alterou;
    }

    public boolean darAlta(String id, LocalDateTime dataSaida) {
        List<InternacaoRegistro> lista = carregarTodos();
        boolean alterou = false;
        for (InternacaoRegistro r : lista) {
            if (r.id.equals(id) && r.status == StatusInternacao.ATIVA) {
                r.dataSaida = dataSaida;
                r.status = StatusInternacao.ALTA;
                alterou = true;
                break;
            }
        }
        if (alterou) salvarTodos(lista);
        return alterou;
    }
}

