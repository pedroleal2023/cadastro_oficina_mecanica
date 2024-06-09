package model.entities;


public class OrdemServico {
    private int id;
    private int clienteId;
    private String descricao;
    private String status;
    private double valorServico;
    private double valorPecas;

    public OrdemServico(int clienteId, String descricao, String status) {
        this.clienteId = clienteId;
        this.descricao = descricao;
        this.status = status;
    }

    public OrdemServico(int id, int clienteId, String descricao, String status, double valorServico, double valorPecas) {
        this.id = id;
        this.clienteId = clienteId;
        this.descricao = descricao;
        this.status = status;
        this.valorServico = valorServico;
        this.valorPecas = valorPecas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getValorServico() {
        return valorServico;
    }

    public void setValorServico(double valorServico) {
        this.valorServico = valorServico;
    }

    public double getValorPecas() {
        return valorPecas;
    }

    public void setValorPecas(double valorPecas) {
        this.valorPecas = valorPecas;
    }
    
    

    @Override
    public String toString() {
        return "OS #" + id + " - " + descricao;
    }
}


