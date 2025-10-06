package entidades;
import java.time.*;
import java.util.*;

public class Medico {
    private String nome;
    private int crm;
    private String especialidade;
    private int custoConsulta;
    private int custoInternacao;
    private final List<LocalDateTime> agendaHorario;
    private final List<String> planosAceitos;

    public Medico(){
        this.nome = "";
        this.crm = 0;
        this.especialidade = "";
        this.custoConsulta = 0;
        this.custoInternacao = 0;
        this.agendaHorario = new ArrayList<>();
        this.planosAceitos = new ArrayList<>();
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

    public void setPlanosAceitos(List<String> planosAceitos){
        this.planosAceitos.clear();
        if (planosAceitos != null){
            this.planosAceitos.addAll(planosAceitos);
        }
    }

    public List<String> getPlanosAceitos(){
        return new ArrayList<>(planosAceitos);
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

    public void setAgendaHorario(List<LocalDateTime> agendaHorario){
        this.agendaHorario.clear();
        if(agendaHorario != null){
            this.agendaHorario.addAll(agendaHorario);
        }
    }

    public void adicionarHorarioDisponivel(LocalDateTime horario){
        agendaHorario.add(horario);
    }

    public void removerHorarioDisponivel(LocalDateTime horario){
        agendaHorario.remove(horario);
    }

    public List<LocalDateTime> getAgendaHorario(){
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

    @Override
    public String toString() {
        return "Medico{" +
        "nome='" + nome + '\'' +
        ", crm=" + crm + ", especialidade='" + especialidade + '\'' +
        ", custoInternacao=" + custoInternacao +
        ", agendaHorario=" + agendaHorario +
        ", planosAceitos=" + planosAceitos + '}';
    }
}


