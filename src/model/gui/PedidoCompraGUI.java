
package model.gui;

import model.dao.*;
import model.entities.PedidoCompra;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;	
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PedidoCompraGUI extends JFrame {
    private static final long serialVersionUID = 1L; // Adicionado serialVersionUID

    private PedidoCompraDAO pedidoDeCompraDAO;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField dataPedidoField;
    private JTextField statusField;

    public PedidoCompraGUI() {
        pedidoDeCompraDAO = new PedidoCompraDAO();

        setTitle("Gerenciamento de Pedidos de Compra");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Data do Pedido", "Status"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(3, 2));
        panel.add(formPanel, BorderLayout.NORTH);

        formPanel.add(new JLabel("Data do Pedido:"));
        dataPedidoField = new JTextField();
        formPanel.add(dataPedidoField);

        formPanel.add(new JLabel("Status:"));
        statusField = new JTextField();
        formPanel.add(statusField);

        JButton addButton = new JButton("Adicionar Pedido de Compra");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarPedidoDeCompra();
            }
        });
        formPanel.add(addButton);

        JButton refreshButton = new JButton("Atualizar Lista");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarPedidosDeCompra();
            }
        });
        formPanel.add(refreshButton);

        listarPedidosDeCompra();
    }

    private void adicionarPedidoDeCompra() {
        try {
            String dataStr = dataPedidoField.getText();
            Date dataPedido = new SimpleDateFormat("dd/MM/yyyy").parse(dataStr);
            String status = statusField.getText();

            PedidoCompra pedido = new PedidoCompra(dataPedido, status);
            pedidoDeCompraDAO.adicionarPedidoCompra(pedido);
            listarPedidosDeCompra();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar pedido de compra: " + e.getMessage());
        }
    }

    private void listarPedidosDeCompra() {
        List<PedidoCompra> pedidosDeCompra = pedidoDeCompraDAO.listarPedidoCompra();
        tableModel.setRowCount(0);

        for (PedidoCompra pedido : pedidosDeCompra) {
            tableModel.addRow(new Object[]{
                    pedido.getId(),
                    new SimpleDateFormat("yyyy-MM-dd").format(pedido.getDataPedido()),
                    pedido.getStatus()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PedidoCompraGUI().setVisible(true);
            }
        });
    }
}
