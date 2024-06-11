package model.dao;

import model.entities.Estoque;
import DataBaseConnection.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstoqueDAO {
    private Connection connection;

    public EstoqueDAO() {
        connection = DataBaseConnection.getConnection();
    }

    public void adicionarEstoque(Estoque estoque) {
        try {
            String sql = "INSERT INTO estoque (codigo, nome, descricao, quantidade) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, estoque.getCodigo());
            stmt.setString(2, estoque.getNome());
            stmt.setString(3, estoque.getDescricao());
            stmt.setInt(4, estoque.getQuantidade());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarEstoque(Estoque estoque) {
        try {
            String sql = "UPDATE estoque SET codigo=?, nome=?, descricao=?, quantidade=? WHERE id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, estoque.getCodigo());
            stmt.setString(2, estoque.getNome());
            stmt.setString(3, estoque.getDescricao());
            stmt.setInt(4, estoque.getQuantidade());
            stmt.setInt(5, estoque.getId());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarEstoque(int id) {
        try {
            String sql = "DELETE FROM estoque WHERE id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Estoque> listarEstoque() {
        List<Estoque> estoques = new ArrayList<>();
        try {
            String sql = "SELECT * FROM estoque";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Estoque estoque = new Estoque(
                    rs.getInt("id"),
                    rs.getInt("codigo"),
                    rs.getInt("quantidade"),
                    rs.getString("nome"),
                    rs.getString("descricao")
                );
                estoques.add(estoque); // Adicionando o objeto Estoque à lista
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return estoques;
    }

    public boolean existeEstoque(String codigo) {
        String sql = "SELECT * FROM estoque WHERE codigo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Se houver algum resultado, significa que o estoque já existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
