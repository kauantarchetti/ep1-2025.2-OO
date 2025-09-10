public class Medico {
    private String nome;
    private int crm;
    private String especialidade;
    private int custoConsulta;
    private int custoInternacao;
    private String agendaHorario;

    public Medico(){
        this.nome = "";
        this.crm = 0;
        this.especialidade = "";
        this.custoConsulta = 0;
        this.custoInternacao = 0;
        this.agendaHorario = "";
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

    public void setAgendaHorario(String agendaHorario){
        this.agendaHorario = agendaHorario;
    }

    public String getAgendaHorario(){
        return agendaHorario;
    }
}


