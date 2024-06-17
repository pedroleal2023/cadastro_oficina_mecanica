package model.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import model.dao.EstoqueDAO;
import model.entities.Estoque;
import model.entities.Pecas;

import java.awt.BorderLayout;
import java.util.List;

public class EstoqueGUI extends JFrame {
    private static final long serialVersionUID = 1L;

    private DefaultTableModel tableModel;
    private JTable table;
    private EstoqueDAO estoqueDAO;

    public EstoqueGUI() {
        setTitle("Estoque");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Cria o modelo da tabela com as colunas desejadas
        String[] columnNames = {"ID Estoque", "Nome Estoque", "Descrição Estoque", "ID Peça", "Código Peça", "Nome Peça", "Descrição Peça", "Quantidade Peça"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        add(mainPanel);

        estoqueDAO = new EstoqueDAO();
        carregarDadosEstoque();

        setVisible(true);
    }

    private void carregarDadosEstoque() {
        // Limpa a tabela antes de carregar os novos dados
        tableModel.setRowCount(0);

        // Obtém a lista de estoques do banco de dados
        List<Estoque> estoques = estoqueDAO.listarEstoques();

        // Preenche a tabela com os dados dos estoques e suas peças associadas
        for (Estoque estoque : estoques) {
            for (Pecas peca : estoque.getPecas()) {
                Object[] rowData = {
                    estoque.getId(),
                    estoque.getNome(),
                    estoque.getDescricao(),
                    peca.getId(),
                    peca.getCodigo(),
                    peca.getNome(),
                    peca.getDescricao(),
                    peca.getQuantidade()
                };
                tableModel.addRow(rowData);
            }
        }
    }

    // Método para adicionar uma nova peça à tabela
    public void adicionarPeca(Pecas peca) {
        Object[] rowData = {
            peca.getEstoqueId(),
            "", // Nome e descrição do estoque são opcionais para a tabela de peças
            "",
            peca.getId(),
            peca.getCodigo(),
            peca.getNome(),
            peca.getDescricao(),
            peca.getQuantidade()
        };
        tableModel.addRow(rowData);
    }

    // Método para atualizar uma peça na tabela
    public void atualizarPeca(Pecas peca) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if ((int) tableModel.getValueAt(i, 3) == peca.getId()) {
                tableModel.setValueAt(peca.getCodigo(), i, 4);
                tableModel.setValueAt(peca.getNome(), i, 5);
                tableModel.setValueAt(peca.getDescricao(), i, 6);
                tableModel.setValueAt(peca.getQuantidade(), i, 7);
                break;
            }
        }
    }

    // Método para remover uma peça da tabela
    public void removerPeca(Pecas peca) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if ((int) tableModel.getValueAt(i, 3) == peca.getId()) {
                tableModel.removeRow(i);
                break;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EstoqueGUI());
    }
}
