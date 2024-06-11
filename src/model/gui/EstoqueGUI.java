package model.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import model.dao.EstoqueDAO;
import model.entities.Estoque;

public class EstoqueGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private JTextField codigoField;
    private JTextField nomeField;
    private JTextField descricaoField;
    private JTextField quantidadeField;
    private JList<Estoque> estoqueList;
    private DefaultListModel<Estoque> listModel;
    private EstoqueDAO estoqueDao;
 
    public EstoqueGUI() {
        estoqueDao = new EstoqueDAO();
        setTitle("Controle de Estoque");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        codigoField = new JTextField(20);
        nomeField = new JTextField(20);
        descricaoField = new JTextField(20);
        quantidadeField = new JTextField(20);

        JButton adicionarButton = new JButton("Adicionar");
        adicionarButton.addActionListener(e -> adicionarEstoque());

        JButton atualizarButton = new JButton("Atualizar");
        atualizarButton.addActionListener(e -> atualizarEstoque());

        JButton deletarButton = new JButton("Deletar");
        deletarButton.addActionListener(e -> deletarEstoque());

        JButton novoCadastroButton = new JButton("Novo Cadastro");
        novoCadastroButton.addActionListener(e -> limparCampos());

        listModel = new DefaultListModel<>();
        estoqueList = new JList<>(listModel);
        estoqueList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        estoqueList.addListSelectionListener(e -> selecionarEstoque());

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.add(new JLabel("Código:"));
        formPanel.add(codigoField);
        formPanel.add(new JLabel("Nome:"));
        formPanel.add(nomeField);
        formPanel.add(new JLabel("Descrição:"));
        formPanel.add(descricaoField);
        formPanel.add(new JLabel("Quantidade:"));
        formPanel.add(quantidadeField);

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
        mainPanel.add(new JScrollPane(estoqueList), BorderLayout.CENTER);

        getContentPane().add(mainPanel);

        loadData();
    }
    private void adicionarEstoque() {
        if (validarCampos()) {
        	Estoque estoque = new Estoque(0, Integer.parseInt(codigoField.getText()), Integer.parseInt(quantidadeField.getText()), descricaoField.getText(), nomeField.getText());


            try {
                estoqueDao.adicionarEstoque(estoque);
                listModel.addElement(estoque);
                limparCampos();
                JOptionPane.showMessageDialog(this, "Estoque adicionado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar estoque: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void atualizarEstoque() {
        if (validarCampos()) {
            Estoque estoque = estoqueList.getSelectedValue();
            if (estoque != null) {
                estoque.setCodigo(Integer.parseInt(codigoField.getText()));
                estoque.setNome(nomeField.getText());
                estoque.setDescricao(descricaoField.getText());
                estoque.setQuantidade(Integer.parseInt(quantidadeField.getText()));
                try {
                    estoqueDao.atualizarEstoque(estoque);
                    estoqueList.repaint();
                    limparCampos();
                    JOptionPane.showMessageDialog(this, "Estoque atualizado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Erro ao atualizar estoque: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void deletarEstoque() {
        Estoque estoque = estoqueList.getSelectedValue();
        if (estoque != null) {
            try {
                estoqueDao.deletarEstoque(estoque.getId());
                listModel.removeElement(estoque);
                limparCampos();
                JOptionPane.showMessageDialog(this, "Estoque deletado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao deletar estoque: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadData() {
        try {
            listModel.clear();
            List<Estoque> estoqueList = estoqueDao.listarEstoque();
            for (Estoque estoque : estoqueList) {
                listModel.addElement(estoque);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados do estoque: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void selecionarEstoque() {
        Estoque estoque = estoqueList.getSelectedValue();
        if (estoque != null) {
            codigoField.setText(String.valueOf(estoque.getCodigo()));
            nomeField.setText(estoque.getNome());
            descricaoField.setText(estoque.getDescricao());
            quantidadeField.setText(String.valueOf(estoque.getQuantidade()));
        }
    }

    private void limparCampos() {
        codigoField.setText("");
        nomeField.setText("");
        descricaoField.setText("");
        quantidadeField.setText("");
    }

    private boolean validarCampos() {
        if (codigoField.getText().isEmpty() || nomeField.getText().isEmpty() || descricaoField.getText().isEmpty() || quantidadeField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Integer.parseInt(quantidadeField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "A quantidade deve ser um valor numérico inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
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

            EstoqueGUI estoqueGUI = new EstoqueGUI();
            estoqueGUI.setVisible(true);
        });
    }
}