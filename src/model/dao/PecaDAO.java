package model.dao;

import model.entities.Pecas;
import DataBaseConnection.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PecaDAO {
    private Connection connection;

    public PecaDAO() {
        connection = DataBaseConnection.getConnection();
    }

    public void adicionarPeca(Pecas peca) {
        try {
            String sql = "INSERT INTO pecas (codigo, nome, descricao, quantidade) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, peca.getCodigo());
            stmt.setString(2, peca.getNome());
            stmt.setString(3, peca.getDescricao());
            stmt.setInt(4, peca.getQuantidade());
            stmt.executeUpdate();

            // Obtém o ID gerado automaticamente
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                peca.setId(generatedKeys.getInt(1));
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarPeca(Pecas peca) {
        try {
            String sql = "UPDATE pecas SET codigo=?, nome=?, descricao=?, quantidade=? WHERE id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, peca.getCodigo());
            stmt.setString(2, peca.getNome());
            stmt.setString(3, peca.getDescricao());
            stmt.setInt(4, peca.getQuantidade());
            stmt.setInt(5, peca.getId());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarPeca(int id) {
        try {
            String sql = "DELETE FROM pecas WHERE id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Pecas> listarPecas() {
        List<Pecas> pecas = new ArrayList<>();
        try {
            String sql = "SELECT * FROM pecas";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Pecas peca = new Pecas(
                        rs.getInt("id"),
                        rs.getString("codigo"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getInt("quantidade")
                );
                pecas.add(peca);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pecas;
    }

    // Método para listar peças com informações de estoque (exemplo de JOIN)
    public List<Pecas> listarPecasComEstoque() {
        List<Pecas> pecas = new ArrayList<>();
        try {
            String sql = "SELECT p.*, e.nome AS nome_estoque FROM pecas p " +
                         "LEFT JOIN estoque e ON p.estoque_id = e.id";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Pecas peca = new Pecas(
                        rs.getInt("id"),
                        rs.getString("codigo"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getInt("quantidade")
                );
                peca.setNomeEstoque(rs.getString("nome_estoque"));
                pecas.add(peca);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pecas;
    }
}
