package entidades;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Internacao {
    private final String id;
    private final Paciente paciente;
    private final Medico medicoResponsavel;
    private final LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;
    private final String quarto;
    private final double custo;
    private StatusInternacao status;

    public Internacao(Paciente paciente, Medico medicoResponsavel, LocalDateTime dataEntrada, String quarto, double custo) {
        this.id = UUID.randomUUID().toString();
        this.paciente = Objects.requireNonNull(paciente, "Paciente nao pode ser nulo");
        this.medicoResponsavel = Objects.requireNonNull(medicoResponsavel, "Medico nao pode ser nulo");
        this.dataEntrada = Objects.requireNonNull(dataEntrada, "Data de entrada nao pode ser nula");
        this.quarto = Objects.requireNonNull(quarto, "Quarto nao pode ser nulo").trim();
        if (this.quarto.isEmpty()) {
            throw new IllegalArgumentException("Quarto nao pode ser vazio");
        }
        if (custo < 0) {
            throw new IllegalArgumentException("Custo nao pode ser negativo");
        }
        this.custo = custo;
        this.status = StatusInternacao.ATIVA;
    }

    public String getId() { return id; }
    public Paciente getPaciente() { return paciente; }
    public Medico getMedicoResponsavel() { return medicoResponsavel; }
    public LocalDateTime getDataEntrada() { return dataEntrada; }
    public LocalDateTime getDataSaida() { return dataSaida; }
    public String getQuarto() { return quarto; }
    public double getCusto() { return custo; }
    public StatusInternacao getStatus() { return status; }

    public boolean isAtiva() { return status == StatusInternacao.ATIVA; }

    public void darAlta(LocalDateTime dataSaida) {
        if (!isAtiva()) return;
        this.dataSaida = Objects.requireNonNull(dataSaida, "Data de saida nao pode ser nula");
        this.status = StatusInternacao.ALTA;
    }

    public void cancelar() {
        if (status == StatusInternacao.ALTA) {
            throw new IllegalStateException("Nao e possivel cancelar uma internacao ja encerrada");
        }
        this.status = StatusInternacao.CANCELADA;
    }
}

