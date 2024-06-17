package model.entities;

public class OrdemServico {
    private int id;
    private int clienteId;
    private String descricao;
    private String status;
    private Double valorServico; // Alteração: mudança de double para Double
    private Double valorPecas;   // Alteração: mudança de double para Double

    public OrdemServico() {
    }

    public OrdemServico(int id, int clienteId, String descricao, String status, Double valorServico, Double valorPecas) {
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

    public Double getValorServico() {
        return valorServico;
    }

    public void setValorServico(Double valorServico) {
        this.valorServico = valorServico;
    }

    public Double getValorPecas() {
        return valorPecas;
    }

    public void setValorPecas(Double valorPecas) {
        this.valorPecas = valorPecas;
    }

    @Override
    public String toString() {
        return "OS #" + id + " - " + descricao;
    }
}
