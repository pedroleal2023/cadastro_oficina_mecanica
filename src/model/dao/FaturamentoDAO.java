package model.dao;

import model.entities.Faturamento;
import DataBaseConnection.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FaturamentoDAO {
    private Connection connection;

    public FaturamentoDAO() {
        connection = DataBaseConnection.getConnection();
    }

    public void adicionarFaturamento(Faturamento faturamento) {
        String sql = "INSERT INTO faturamento (ordem_servico_id, valor_servico, valor_pecas) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, faturamento.getOrdemServicoId());
            stmt.setDouble(2, faturamento.getValorServico());
            stmt.setDouble(3, faturamento.getValorPecas());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarFaturamento(Faturamento faturamento) {
        String sql = "UPDATE faturamento SET ordem_servico_id=?, valor_servico=?, valor_pecas=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, faturamento.getOrdemServicoId());
            stmt.setDouble(2, faturamento.getValorServico());
            stmt.setDouble(3, faturamento.getValorPecas());
            stmt.setInt(4, faturamento.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarFaturamento(int id) {
        String sql = "DELETE FROM faturamento WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Faturamento> listarFaturamentos() {
        List<Faturamento> faturamentos = new ArrayList<>();
        String sql = "SELECT * FROM faturamento";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Faturamento faturamento = new Faturamento(
                    rs.getInt("id"),
                    rs.getInt("ordem_servico_id"),
                    rs.getDouble("valor_servico"),
                    rs.getDouble("valor_pecas")
                );
                faturamentos.add(faturamento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return faturamentos;
    }

    public boolean existeFaturamento(int ordemServicoId) {
        String sql = "SELECT COUNT(*) FROM faturamento WHERE ordem_servico_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, ordemServicoId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
