package model.gui;

import model.dao.*;
import model.entities.Cliente;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ClienteGUI extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextField nomeField;
    private JTextField enderecoField;
    private JTextField telefoneField;
    private JTextField emailField;
    private JList<Cliente> clienteList;
    private DefaultListModel<Cliente> listModel;
    private ClienteDAO clienteDAO;

    public ClienteGUI() {
        clienteDAO = new ClienteDAO();
        setTitle("Cadastro de Cliente");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        nomeField = new JTextField(20);
        enderecoField = new JTextField(20);
        telefoneField = new JTextField(20);
        emailField = new JTextField(20);

        JButton adicionarButton = new JButton("Adicionar");
        adicionarButton.addActionListener(e -> adicionarCliente());

        JButton atualizarButton = new JButton("Atualizar");
        atualizarButton.addActionListener(e -> atualizarCliente());

        JButton deletarButton = new JButton("Deletar");
        deletarButton.addActionListener(e -> deletarCliente());

        JButton novoCadastroButton = new JButton("Novo Cadastro");
        novoCadastroButton.addActionListener(e -> limparCampos());

        listModel = new DefaultListModel<>();
        clienteList = new JList<>(listModel);
        clienteList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        clienteList.addListSelectionListener(e -> selecionarCliente());

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.add(new JLabel("Nome:"));
        formPanel.add(nomeField);
        formPanel.add(new JLabel("Endereço:"));
        formPanel.add(enderecoField);
        formPanel.add(new JLabel("Telefone:"));
        formPanel.add(telefoneField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(adicionarButton);
        buttonPanel.add(atualizarButton);
        buttonPanel.add(deletarButton);
        buttonPanel.add(novoCadastroButton);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(formPanel, BorderLayout.CENTER);
        inputPanel.add(buttonPanel, BorderLayout.SOUTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(clienteList), BorderLayout.CENTER);

        getContentPane().add(mainPanel);

        listarClientes();
    }

    private void adicionarCliente() {
        if (validarCampos()) {
            Cliente cliente = new Cliente();
            cliente.setNome(nomeField.getText());
            cliente.setEndereco(enderecoField.getText());
            cliente.setTelefone(telefoneField.getText());
            cliente.setEmail(emailField.getText());

            if (!clienteDAO.existeCliente(cliente.getNome(), cliente.getTelefone(), cliente.getEmail())) {
                clienteDAO.adicionarCliente(cliente);
                listModel.addElement(cliente);
                limparCampos();
                JOptionPane.showMessageDialog(this, "Cliente adicionado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Cliente já cadastrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void atualizarCliente() {
        if (validarCampos()) {
            Cliente cliente = clienteList.getSelectedValue();
            if (cliente != null) {
                cliente.setNome(nomeField.getText());
                cliente.setEndereco(enderecoField.getText());
                cliente.setTelefone(telefoneField.getText());
                cliente.setEmail(emailField.getText());
                clienteDAO.atualizarCliente(cliente);
                clienteList.repaint();
                limparCampos();
                JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void deletarCliente() {
        Cliente cliente = clienteList.getSelectedValue();
        if (cliente != null) {
            clienteDAO.deletarCliente(cliente.getId());
            listModel.removeElement(cliente);
            limparCampos();
            JOptionPane.showMessageDialog(this, "Cliente deletado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void listarClientes() {
        List<Cliente> clientes = clienteDAO.listarCliente();
        listModel.clear();
        for (Cliente cliente : clientes) {
            listModel.addElement(cliente);
        }
    }

    private void selecionarCliente() {
        Cliente cliente = clienteList.getSelectedValue();
        if (cliente != null) {
            nomeField.setText(cliente.getNome());
            enderecoField.setText(cliente.getEndereco());
            telefoneField.setText(cliente.getTelefone());
            emailField.setText(cliente.getEmail());
        }
    }

    private void limparCampos() {
        nomeField.setText("");
        enderecoField.setText("");
        telefoneField.setText("");
        emailField.setText("");
    }

    private boolean validarCampos() {
        if (nomeField.getText().isEmpty() || enderecoField.getText().isEmpty() || telefoneField.getText().isEmpty() || emailField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            ClienteGUI clienteGUI = new ClienteGUI();
            clienteGUI.setVisible(true);
        });
    }
}









