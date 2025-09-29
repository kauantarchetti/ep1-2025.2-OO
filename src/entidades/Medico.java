package entidades;
import java.util.ArrayList;

public class Medico {
    private String nome;
    private int crm;
    private String especialidade;
    private int custoConsulta;
    private int custoInternacao;
    private ArrayList<String> agendaHorario;
    private ArrayList<Integer> planosAceitos;

    public Medico(){
        this.nome = "";
        this.crm = 0;
        this.especialidade = "";
        this.custoConsulta = 0;
        this.custoInternacao = 0;
        this.agendaHorario = null;
        this.planosAceitos = null;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getNome(){
        return nome;
    }

    public void setCrm(int crm){
        this.crm = crm;
    }

    public int getCrm(){
        return crm;
    }

    public void setPlanosAceitos(ArrayList<Integer> planosAceitos){
        this.planosAceitos = planosAceitos;
    }

    public ArrayList<Integer> getPlanosAceitos(){
        return planosAceitos;
    }

    public void setEspecialidade(String especialidade){
        this.especialidade = especialidade;
    }

    public String getEspecialidade(){
        return especialidade;
    }

    public void setCustoConsulta(int custoConsulta){
        this.custoConsulta = custoConsulta;
    }

    public int getCustoConsulta(){
        return custoConsulta;
    }

    public void setCustoInternacao(int custoInternacao){
        this.custoInternacao = custoInternacao;
    }

    public int getCustoInternacao(){
        return custoInternacao;
    }

    public void setAgendaHorario(ArrayList<String> agendaHorario){
        this.agendaHorario = agendaHorario;
    }

    public ArrayList<String> getAgendaHorario(){
        return agendaHorario;
    }


    public void exibirDadosMedico(){
        System.out.println("Nome "+ nome);
        System.out.println("Crm "+ crm);
        System.out.println("Especialidade "+ especialidade);
        System.out.println("Custo da consulta "+ custoConsulta);
        System.out.println("Custo da internação "+ custoInternacao);
        System.out.println("Agenda horário "+ agendaHorario);
    }
}


