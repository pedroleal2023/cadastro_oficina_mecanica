package model.gui;

import model.dao.ClienteDAO;
import model.dao.OrdemServicoDAO;
import model.entities.Cliente;
import model.entities.OrdemServico;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class OrdemServicoGUI extends JFrame {
    private static final long serialVersionUID = 1L;

    private JComboBox<Cliente> clienteComboBox;
    private JTextArea descricaoArea;
    private JTextField statusField;
    private JTextField valorServicoField; // Novo campo para valor de serviço
    private JTextField valorPecasField; // Novo campo para valor de peças
    private JList<OrdemServico> osList;
    private DefaultListModel<OrdemServico> listModel;
    private OrdemServicoDAO osDAO;
    private ClienteDAO clienteDAO;

    public OrdemServicoGUI() {
        osDAO = new OrdemServicoDAO();
        clienteDAO = new ClienteDAO();
        setTitle("Gerenciamento de Ordens de Serviço");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Criando painel principal com abas
        JTabbedPane tabbedPane = new JTabbedPane();

        // Painel de formulário
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Cliente:"), gbc);

        gbc.gridx = 1;
        clienteComboBox = new JComboBox<>();
        clienteComboBox.setPreferredSize(new Dimension(200, 25));
        formPanel.add(clienteComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Descrição:"), gbc);

        gbc.gridx = 1;
        descricaoArea = new JTextArea(5, 30);
        JScrollPane descricaoScrollPane = new JScrollPane(descricaoArea);
        formPanel.add(descricaoScrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Status:"), gbc);

        gbc.gridx = 1;
        statusField = new JTextField(20);
        formPanel.add(statusField, gbc);

        // Novos campos para valor de serviço e peças
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Valor Serviço:"), gbc);

        gbc.gridx = 1;
        valorServicoField = new JTextField(20);
        formPanel.add(valorServicoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Valor Peças:"), gbc);

        gbc.gridx = 1;
        valorPecasField = new JTextField(20);
        formPanel.add(valorPecasField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel();
        JButton adicionarButton = new JButton("Adicionar");
        adicionarButton.addActionListener(e -> adicionarOrdemDeServico());
        buttonPanel.add(adicionarButton);

        JButton atualizarButton = new JButton("Atualizar");
        atualizarButton.addActionListener(e -> atualizarOrdemDeServico());
        buttonPanel.add(atualizarButton);

        JButton deletarButton = new JButton("Deletar");
        deletarButton.addActionListener(e -> deletarOrdemDeServico());
        buttonPanel.add(deletarButton);

        formPanel.add(buttonPanel, gbc);

        // Painel de listagem
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel osLabel = new JLabel("Ordens de Serviço:");
        listPanel.add(osLabel, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        osList = new JList<>(listModel);
        osList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(osList);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        // Adicionando painéis ao tabbedPane
        tabbedPane.addTab("Formulário", formPanel);
        tabbedPane.addTab("Lista de Ordens de Serviço", listPanel);

        // Adicionando tabbedPane ao frame
        add(tabbedPane);

        carregarClientes();
        listarOrdensDeServico();
    }

    private void adicionarOrdemDeServico() {
        Cliente cliente = (Cliente) clienteComboBox.getSelectedItem();
        if (cliente != null) {
            String valorServicoText = valorServicoField.getText().trim();
            String valorPecasText = valorPecasField.getText().trim();

            if (valorServicoText.isEmpty() || valorPecasText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.");
                return;
            }

            try {
                Double valorServico = Double.parseDouble(valorServicoText);
                Double valorPecas = Double.parseDouble(valorPecasText);

                OrdemServico os = new OrdemServico(0, // ID será gerado automaticamente pelo banco de dados
                        cliente.getId(), descricaoArea.getText(), statusField.getText(), valorServico, valorPecas);
                osDAO.adicionarOrdemServico(os);
                listModel.addElement(os);
                limparCampos();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Valores de serviço e peças inválidos. Insira valores numéricos válidos.");
            }
        }
    }

    private void atualizarOrdemDeServico() {
        OrdemServico os = osList.getSelectedValue();
        if (os != null) {
            Cliente cliente = (Cliente) clienteComboBox.getSelectedItem();
            if (cliente != null) {
                String valorServicoText = valorServicoField.getText().trim();
                String valorPecasText = valorPecasField.getText().trim();

                if (valorServicoText.isEmpty() || valorPecasText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.");
                    return;
                }

                try {
                    os.setClienteId(cliente.getId());
                    os.setDescricao(descricaoArea.getText());
                    os.setStatus(statusField.getText());

                    Double valorServico = Double.parseDouble(valorServicoText);
                    Double valorPecas = Double.parseDouble(valorPecasText);

                    os.setValorServico(valorServico);
                    os.setValorPecas(valorPecas);

                    osDAO.atualizarOrdemServico(os);
                    osList.repaint();
                    limparCampos();
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Valores de serviço e peças inválidos. Insira valores numéricos válidos.");
                }
            }
        }
    }

    private void deletarOrdemDeServico() {
        OrdemServico os = osList.getSelectedValue();
        if (os != null) {
            osDAO.deletarOrdemServico(os.getId());
            listModel.removeElement(os);
            limparCampos();
        }
    }

    private void carregarClientes() {
        List<Cliente> clientes = clienteDAO.listarCliente();
        for (Cliente cliente : clientes) {
            clienteComboBox.addItem(cliente);
        }
    }

    private void listarOrdensDeServico() {
        List<OrdemServico> ordensDeServico = osDAO.listarOrdemServico();
        for (OrdemServico os : ordensDeServico) {
            listModel.addElement(os);
        }
    }

    private void limparCampos() {
        clienteComboBox.setSelectedIndex(-1);
        descricaoArea.setText("");
        statusField.setText("");
        valorServicoField.setText("");
        valorPecasField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OrdemServicoGUI osGUI = new OrdemServicoGUI();
            osGUI.setVisible(true);
        });
    }
}
