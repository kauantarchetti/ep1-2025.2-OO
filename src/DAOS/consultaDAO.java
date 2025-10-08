package DAOS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import entidades.Consulta;
import entidades.StatusConsulta;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class consultaDAO {
    private static final String FILE_NAME = "consultas.json";
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public static class ConsultaRegistro {
        public String id;
        public String cpfPaciente;
        public String nomePaciente;
        public int crmMedico;
        public String nomeMedico;
        public LocalDateTime dataHora;
        public String local;
        public StatusConsulta status;
        public double valorBase;
        public double descontoPercentual;
        public double valorFinal;
        public String diagnostico;
        public String prescricao;
    }

    private List<ConsultaRegistro> carregarTodos() {
        try (Reader reader = new FileReader(FILE_NAME)) {
            List<ConsultaRegistro> lista = gson.fromJson(reader, new TypeToken<List<ConsultaRegistro>>(){}.getType());
            return (lista != null) ? lista : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void salvarTodos(List<ConsultaRegistro> lista) {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar consultas: " + e.getMessage());
        }
    }

    public void salvar(Consulta consulta) {
        List<ConsultaRegistro> lista = carregarTodos();
        ConsultaRegistro r = new ConsultaRegistro();
        r.id = consulta.getId();
        r.cpfPaciente = consulta.getPaciente().getCpf();
        r.nomePaciente = consulta.getPaciente().getNome();
        r.crmMedico = consulta.getMedico().getCrm();
        r.nomeMedico = consulta.getMedico().getNome();
        r.dataHora = consulta.getDataHora();
        r.local = consulta.getLocal();
        r.status = consulta.getStatus();
        r.valorBase = consulta.getValorBase();
        r.descontoPercentual = consulta.getDescontoPercentual();
        r.valorFinal = consulta.getValorFinal();
        r.diagnostico = consulta.getDiagnostico();
        r.prescricao = consulta.getPrescricao();
        lista.add(r);
        salvarTodos(lista);
    }

    public List<ConsultaRegistro> listarTodos() {
        return carregarTodos();
    }

    public List<ConsultaRegistro> listarPorPacienteCpf(String cpf) {
        List<ConsultaRegistro> out = new ArrayList<>();
        for (ConsultaRegistro r : carregarTodos()) {
            if (r.cpfPaciente != null && r.cpfPaciente.equals(cpf)) {
                out.add(r);
            }
        }
        return out;
    }

    public List<ConsultaRegistro> listarPorMedicoCrm(int crm) {
        List<ConsultaRegistro> out = new ArrayList<>();
        for (ConsultaRegistro r : carregarTodos()) {
            if (r.crmMedico == crm) {
                out.add(r);
            }
        }
        return out;
    }

    public List<ConsultaRegistro> listarAgendadas() {
        List<ConsultaRegistro> out = new ArrayList<>();
        for (ConsultaRegistro r : carregarTodos()) {
            if (r.status == StatusConsulta.AGENDADA) {
                out.add(r);
            }
        }
        return out;
    }

    public Optional<ConsultaRegistro> buscarPorId(String id) {
        for (ConsultaRegistro r : carregarTodos()) {
            if (r.id.equals(id)) return Optional.of(r);
        }
        return Optional.empty();
    }

    public boolean atualizarStatus(String id, StatusConsulta novoStatus) {
        List<ConsultaRegistro> lista = carregarTodos();
        boolean alterou = false;
        for (ConsultaRegistro r : lista) {
            if (r.id.equals(id)) {
                r.status = novoStatus;
                alterou = true;
                break;
            }
        }
        if (alterou) salvarTodos(lista);
        return alterou;
    }

    public boolean atualizarConclusao(String id, String diagnostico, String prescricao) {
        List<ConsultaRegistro> lista = carregarTodos();
        boolean alterou = false;
        for (ConsultaRegistro r : lista) {
            if (r.id.equals(id)) {
                r.diagnostico = (diagnostico == null || diagnostico.trim().isEmpty()) ? null : diagnostico;
                r.prescricao = (prescricao == null || prescricao.trim().isEmpty()) ? null : prescricao;
                r.status = StatusConsulta.CONCLUIDA;
                alterou = true;
                break;
            }
        }
        if (alterou) salvarTodos(lista);
        return alterou;
    }
}
