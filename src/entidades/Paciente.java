package entidades;

import java.util.ArrayList;

public class Paciente {
    private String nome;
    private String cpf;
    private int idade;
    private ArrayList<String> historicoConsultas;
    private ArrayList<String> historicoInternacoes;
    public boolean temRegistroPlano;
    

    public Paciente(){
        this.nome = "";
        this.cpf = "";
        this.idade = 0;
        this.historicoConsultas = null;
        this.historicoInternacoes = null;
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

    @Override
    public String toString() {
        return "Paciente{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", idade=" + idade +
                ", temRegistroPlano=" + temRegistroPlano +
                ", historicoConsultas=" + historicoConsultas +
                ", historicoInternacoes=" + historicoInternacoes +
                '}';
}}
