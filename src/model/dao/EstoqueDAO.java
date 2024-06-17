package model.dao;

import model.entities.Estoque;
import model.entities.Pecas;
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
        String sql = "INSERT INTO estoque (nome, descricao) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, estoque.getNome());
            stmt.setString(2, estoque.getDescricao());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                estoque.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Falha ao obter o ID gerado para o estoque.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarEstoque(Estoque estoque) {
        String sql = "UPDATE estoque SET nome = ?, descricao = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, estoque.getNome());
            stmt.setString(2, estoque.getDescricao());
            stmt.setInt(3, estoque.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarEstoque(int id) {
        String sql = "DELETE FROM estoque WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Estoque buscarEstoquePorId(int id) {
        Estoque estoque = null;
        String sql = "SELECT * FROM estoque WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    estoque = new Estoque(rs.getInt("id"), rs.getString("nome"), rs.getString("descricao"));
                    carregarPecas(estoque);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return estoque;
    }

    public List<Estoque> listarEstoques() {
        List<Estoque> estoques = new ArrayList<>();
        String sql = "SELECT * FROM estoque";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Estoque estoque = new Estoque(rs.getInt("id"), rs.getString("nome"), rs.getString("descricao"));
                carregarPecas(estoque);
                estoques.add(estoque);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return estoques;
    }

    private void carregarPecas(Estoque estoque) {
        String sql = "SELECT * FROM pecas WHERE estoque_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, estoque.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Pecas peca = new Pecas(
                            rs.getInt("id"),
                            rs.getString("codigo"),
                            rs.getString("nome"),
                            rs.getString("descricao"),
                            rs.getInt("quantidade")                           
                    );
                    estoque.adicionarPeca(peca);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	public Pecas adicionarPeca(Pecas peca) {
		return peca;
		
	}
}
