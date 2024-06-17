package model.entities;

import java.util.Date;

public class PedidoCompra {
    private int id;
    private Date dataPedido;
    private String status;

    
    public PedidoCompra(Date dataPedido, String status) {
        this.dataPedido = dataPedido;
        this.status = status;
    }

    public PedidoCompra(int id, Date dataPedido, String status) {
        this.id = id;
        this.dataPedido = dataPedido;
        this.status = status;
    }

   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}