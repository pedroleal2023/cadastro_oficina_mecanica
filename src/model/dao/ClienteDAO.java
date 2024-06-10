package model.dao;

import model.entities.Cliente;
import DataBaseConnection.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private Connection connection;

    public ClienteDAO() {
        connection = DataBaseConnection.getConnection();
    }

    public void adicionarCliente(Cliente cliente) {
        try {
            if (!existeCliente(cliente.getNome(), cliente.getTelefone(), cliente.getEmail())) {
                String sql = "INSERT INTO cliente (nome, endereco, telefone, email) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, cliente.getNome());
                stmt.setString(2, cliente.getEndereco());
                stmt.setString(3, cliente.getTelefone());
                stmt.setString(4, cliente.getEmail());
                stmt.executeUpdate();
                stmt.close();
            } else {
                System.out.println("Cliente já existe!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarCliente(Cliente cliente) {
        try {
            String sql = "UPDATE cliente SET nome=?, endereco=?, telefone=?, email=? WHERE id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEndereco());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEmail());
            stmt.setInt(5, cliente.getId());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarCliente(int id) {
        try {
            String sql = "DELETE FROM cliente WHERE id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Cliente> listarCliente() {
        List<Cliente> clientes = new ArrayList<>();
        try {
            String sql = "SELECT * FROM cliente";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEmail(rs.getString("email"));
                clientes.add(cliente);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }
    
    public boolean existeCliente(String nome, String telefone, String email) {
        String sql = "SELECT * FROM cliente WHERE nome = ? OR telefone = ? OR email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, telefone);
            stmt.setString(3, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Se houver algum resultado, significa que o cliente já existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}


































