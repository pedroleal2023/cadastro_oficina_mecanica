package model.entities;


import java.util.Objects;

public class Faturamento {
    private int id;
    private int ordemServicoId;
    private double valorServico;
    private double valorPecas;

    public Faturamento(int id, int ordemServicoId, double valorServico, double valorPecas) {
        this.id = id;
        this.ordemServicoId = ordemServicoId;
        this.valorServico = valorServico;
        this.valorPecas = valorPecas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrdemServicoId() {
        return ordemServicoId;
    }

    public void setOrdemServicoId(int ordemServicoId) {
        this.ordemServicoId = ordemServicoId;
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
    public int hashCode() {
        return Objects.hash(id, ordemServicoId, valorServico, valorPecas);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Faturamento other = (Faturamento) obj;
        return id == other.id && ordemServicoId == other.ordemServicoId &&
               Double.doubleToLongBits(valorServico) == Double.doubleToLongBits(other.valorServico) &&
               Double.doubleToLongBits(valorPecas) == Double.doubleToLongBits(other.valorPecas);
    }
}
