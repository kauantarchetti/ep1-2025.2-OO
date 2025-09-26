package entidades;

public class PlanoDeSaude {
    private String nome;
    private String categoria;
    private double valor;
    private boolean validade;

    public PlanoDeSaude(){
        this.nome = "";
        this.categoria = "";
        this.valor = 0.0;
        this.validade = false;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getNome(){
        return nome;
    }

    public void setCategoria(String categoria){
        this.categoria = categoria;
    }

    public String getCategoria(){
        return categoria;
    }

    public void setValor(double valor){
        this.valor = valor;
    }

    public double getValor(){
        return valor;
    }

    public void setValidade(boolean validade){
        this.validade = validade;
    }

    public boolean getValidade(){
        return validade;
    }
}
