package model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.entities.OrdemServico;
import DataBaseConnection.DataBaseConnection;

public class OrdemServicoDAO {
	private Connection conexao;

	public OrdemServicoDAO() {
		conexao = DataBaseConnection.getConnection();
	}

	public void adicionarOrdemServico(OrdemServico os) {
		try {
			String sql = "INSERT INTO ordem_servico (cliente_id, descricao, status, valor_servico, valor_pecas) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement pstmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, os.getClienteId());
			pstmt.setString(2, os.getDescricao());
			pstmt.setString(3, os.getStatus());
			pstmt.setDouble(4, os.getValorServico());
			pstmt.setDouble(5, os.getValorPecas());
			pstmt.executeUpdate();

			// Obter o ID gerado e definir no objeto os
			ResultSet generatedKeys = pstmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				os.setId(generatedKeys.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void atualizarOrdemServico(OrdemServico os) {
		try {
			String sql = "UPDATE ordem_servico SET cliente_id = ?, descricao = ?, status = ?, valor_servico = ?, valor_pecas = ? WHERE id = ?";
			PreparedStatement pstmt = conexao.prepareStatement(sql);
			pstmt.setInt(1, os.getClienteId());
			pstmt.setString(2, os.getDescricao());
			pstmt.setString(3, os.getStatus());
			pstmt.setDouble(4, os.getValorServico());
			pstmt.setDouble(5, os.getValorPecas());
			pstmt.setInt(6, os.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deletarOrdemServico(int id) {
		try {
			String sql = "DELETE FROM ordem_servico WHERE id = ?";
			PreparedStatement pstmt = conexao.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<OrdemServico> listarOrdemServico() {
		List<OrdemServico> ordemServico = new ArrayList<>();
		try {
			String sql = "SELECT * FROM ordem_servico";
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				OrdemServico os = new OrdemServico(rs.getInt("id"), rs.getInt("cliente_id"), rs.getString("descricao"),
						rs.getString("status"), rs.getDouble("valor_servico"), rs.getDouble("valor_pecas"));
				ordemServico.add(os);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ordemServico;
	}

	public List<OrdemServico> listarOrdemServicoConcluidas() {
		List<OrdemServico> ordemServico = new ArrayList<>();
		try {
			String sql = "SELECT * FROM ordem_servico WHERE status = 'CONCLUIDA'";
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				OrdemServico os = new OrdemServico(rs.getInt("id"), rs.getInt("cliente_id"), rs.getString("descricao"),
						rs.getString("status"), rs.getDouble("valor_servico"), rs.getDouble("valor_pecas"));
				ordemServico.add(os);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ordemServico;
	}

}
