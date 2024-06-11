package model.entities;

public class Estoque {
    private int id;
    private int codigo;
    private String nome;
    private String descricao;
    private int quantidade;

    public Estoque(int id, int codigo, int quantidade, String nome, String descricao) {
        this.id = id;
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return  " Codigo: " + codigo + '\'' +
                ", Nome: " + nome + '\'' +
                ", Descricao: '" + descricao + '\'' +
                ", Quantidade: " + quantidade
                ;
    }
}
