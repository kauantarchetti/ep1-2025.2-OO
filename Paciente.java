public class Paciente {
    private String nome;
    private String cpf;
    private int idade;
    private String historicoConsultas;
    private String historicoInternacoes;
    public boolean temRegistroPlano;

    public Paciente(){
        this.nome = "";
        this.cpf = "";
        this.idade = 0;
        this.historicoConsultas = "";
        this.historicoInternacoes = "";
        this.temRegistroPlano = false;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getNome(){
        return nome;
    }
    
    public void setCpf(String cpf){
        this.cpf = cpf;
    }

    public String getCpf(){
        return cpf;
    }

    public void setIdade(int idade){
        this.idade = idade;
    }

    public int getIdade(){
        return idade;
    }

    public void setHistoricoConsultas(String historicoConsultas){
        this.historicoConsultas = historicoConsultas;
    }

    public String getHistoricoConsultas(){
        return historicoConsultas;
    }

    public void setHistoricoInternacoes(String historicoInternacoes){
        this.historicoInternacoes = historicoInternacoes;
    }

    public String getHistoricoInternacoes(){
        return historicoInternacoes;
    }

    public void setTemRegistroPlano(boolean temRegistroPlano){
        this.temRegistroPlano = temRegistroPlano;
    }

    public boolean getTemRegistroPlano(){
        return temRegistroPlano;
    }
}
