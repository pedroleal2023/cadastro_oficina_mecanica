
package model.dao;

import model.entities.PedidoCompra;
import DataBaseConnection.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoCompraDAO {
    private Connection conexao;

    public PedidoCompraDAO() {
        conexao = DataBaseConnection.getConnection();
    }

    public void adicionarPedidoDeCompra(PedidoCompra pedido) {
        try {
            String sql = "INSERT INTO pedidos_de_compra (data_pedido, status) VALUES (?, ?)";
            PreparedStatement pstmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setDate(1, new java.sql.Date(pedido.getDataPedido().getTime()));
            pstmt.setString(2, pedido.getStatus());
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                pedido.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PedidoCompra> listarPedidosDeCompra() {
        List<PedidoCompra> pedidosDeCompra = new ArrayList<>();
        try {
            String sql = "SELECT * FROM pedidos_de_compra";
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                PedidoCompra pedido = new PedidoCompra(
                        rs.getInt("id"),
                        rs.getDate("data_pedido"),
                        rs.getString("status")
                );
                pedidosDeCompra.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidosDeCompra;
    }

    public void atualizarPedidoDeCompra(PedidoCompra pedido) {
        try {
            String sql = "UPDATE pedidos_de_compra SET data_pedido = ?, status = ? WHERE id = ?";
            PreparedStatement pstmt = conexao.prepareStatement(sql);
            pstmt.setDate(1, new java.sql.Date(pedido.getDataPedido().getTime()));
            pstmt.setString(2, pedido.getStatus());
            pstmt.setInt(3, pedido.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarPedidoDeCompra(int id) {
        try {
            String sql = "DELETE FROM pedidos_de_compra WHERE id = ?";
            PreparedStatement pstmt = conexao.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

