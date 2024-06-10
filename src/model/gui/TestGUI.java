package model.gui;

import javax.swing.SwingUtilities;

public class TestGUI {
	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            
	        	ClienteGUI clienteGUI = new ClienteGUI();
	            clienteGUI.setVisible(true);

	            FaturamentoGUI faturamentoGUI = new FaturamentoGUI();
	            faturamentoGUI.setVisible(true);

	            OrdemServicoGUI ordemDeServicoGUI = new OrdemServicoGUI();
	            ordemDeServicoGUI.setVisible(true);

	            PecasGUI pecaGUI = new PecasGUI();
	            pecaGUI.setVisible(true);

	            PedidoCompraGUI pedidoDeCompraGUI = new PedidoCompraGUI();
	            pedidoDeCompraGUI.setVisible(true);
	        });
	    }
	}


