package entidades;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Consulta {
    private final String id;
    private final Paciente paciente;
    private final Medico medico;
    private final LocalDateTime dataHora;
    private String local;
    private StatusConsulta status;
    private final double valorBase;
    private final double descontoPercentual;
    private final double valorFinal;
    private String diagnostico;
    private String prescricao;

    Consulta(Paciente paciente, Medico medico, LocalDateTime dataHora, String local, double descontoPercentual) {
        this.id = UUID.randomUUID().toString();
        this.paciente = Objects.requireNonNull(paciente, "Paciente nao pode ser nulo");
        this.medico = Objects.requireNonNull(medico, "Medico nao pode ser nulo");
        this.dataHora = Objects.requireNonNull(dataHora, "Data e hora nao podem ser nulas");
        this.local = Objects.requireNonNull(local, "Local nao pode ser nulo").trim();
        if (this.local.isEmpty()) {
            throw new IllegalArgumentException("Local nao pode ser vazio");
        }
        this.status = StatusConsulta.AGENDADA;
        this.valorBase = medico.getCustoConsulta();
        double percentualNormalizado = Math.min(Math.max(descontoPercentual, 0.0), 1.0);
        this.descontoPercentual = percentualNormalizado;
        this.valorFinal = Math.max(0.0, valorBase * (1.0 - percentualNormalizado));
    }

    public String getId() {
        return id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local){
        this.local = local;
    }

    public StatusConsulta getStatus() {
        return status;
    }

    public double getValorBase() {
        return valorBase;
    }

    public double getDescontoPercentual() {
        return descontoPercentual;
    }

    public double getValorFinal() {
        return valorFinal;
    }

    void atualizarStatus(StatusConsulta novoStatus) {
        status = Objects.requireNonNull(novoStatus, "Status nao pode ser nulo");
    }

    boolean isAtiva() {
        return status == StatusConsulta.AGENDADA;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public String getPrescricao() {
        return prescricao;
    }

    public void concluir(String diagnostico, String prescricao) {
        this.diagnostico = (diagnostico == null ? null : diagnostico.trim());
        this.prescricao = (prescricao == null ? null : prescricao.trim());
        atualizarStatus(StatusConsulta.CONCLUIDA);
    }
}
