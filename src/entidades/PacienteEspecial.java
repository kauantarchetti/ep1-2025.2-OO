package entidades;

public class PacienteEspecial extends Paciente {
    private int numeroRegistroPlano;

    public PacienteEspecial(){
        super();
        this.numeroRegistroPlano = 000000;
    }

    public void setNumeroRegistroPlano(int numeroRegistroPlano){
        this.numeroRegistroPlano = numeroRegistroPlano;
    }

    public int getNumeroRegistroPlano(){
        return numeroRegistroPlano;
    }

    
}



