package entidades;

import java.util.ArrayList;

public class Paciente {
    private String nome;
    private String cpf;
    private int idade;
    private ArrayList<String> historicoConsultas;
    private ArrayList<String> historicoInternacoes;
    public boolean temRegistroPlano;
    private String nomePlanoDeSaude;
    
    

    public Paciente(){
        this.nome = "";
        this.cpf = "";
        this.idade = 0;
        this.historicoConsultas = null;
        this.historicoInternacoes = null;
        this.nomePlanoDeSaude = null;    
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

    public void setHistoricoConsultas(ArrayList<String> historicoConsultas){
        this.historicoConsultas = historicoConsultas;
    }

    public ArrayList<String> getHistoricoConsultas(){
        return historicoConsultas;
    }

    public void setHistoricoInternacoes(ArrayList<String> historicoInternacoes){
        this.historicoInternacoes = historicoInternacoes;
    }

    public ArrayList<String> getHistoricoInternacoes(){
        return historicoInternacoes;
    }

    public void setTemRegistroPlano(boolean temRegistroPlano){
        this.temRegistroPlano = temRegistroPlano;
    }

    public boolean getTemRegistroPlano(){
        return temRegistroPlano;
    }

    public void setNomePlanoDeSaude(String nomePlanoDeSaude){
        this.nomePlanoDeSaude = nomePlanoDeSaude;
    }

    public String getNomePlanoDeSaude(){
        return nomePlanoDeSaude;
    }

    @Override
    public String toString() {
        return "{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", idade=" + idade +
                ", Nome Plano de Saude" + nomePlanoDeSaude +
                ", historicoConsultas=" + historicoConsultas +
                ", historicoInternacoes=" + historicoInternacoes +
                ", tem plano de saude=" + temRegistroPlano +
                '}';
}}
