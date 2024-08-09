package model.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class MenuPrincipal extends JFrame {
	private static final long serialVersionUID = 1L;

	private ClienteGUI clienteGUI;
	private OrdemServicoGUI osGUI;
	private PecasGUI pecasGUI;
	private PedidoCompraGUI pedidoCompraGUI;

	public MenuPrincipal() {
		setTitle("Oficina Mecânica - Menu Principal");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.BLACK); // Define a cor de fundo como preto
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		panel.setLayout(new GridLayout(5, 1, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// Título da Oficina Mecânica
		JLabel titleLabel = new JLabel("Oficina Mecânica - Menu Principal", SwingConstants.CENTER);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
		panel.add(titleLabel);

		// Botões do menu
		JButton clienteButton = createMenuButton("Cadastrar Cliente", e -> showClienteGUI());
		JButton ordemServicoButton = createMenuButton("Ordem de Serviço", e -> showOrdemServicoGUI());
		JButton pecasButton = createMenuButton("Cadastrar Peça no Estoque", e -> showPecasGUI());
		JButton pedidoCompraButton = createMenuButton("Pedido de Compra", e -> showPedidoCompraGUI());

		// Adiciona os botões ao painel
		panel.add(clienteButton);
		panel.add(ordemServicoButton);
		panel.add(pecasButton);
		panel.add(pedidoCompraButton);

		// Define o painel como conteúdo principal da janela
		setContentPane(panel);
	}

	// Método auxiliar para criar botões do menu
	private JButton createMenuButton(String text, ActionListener listener) {
		JButton button = new JButton(text);
		button.setForeground(Color.BLACK); // Define a cor do texto como preto
		button.setBackground(new Color(30, 144, 255)); // Define a cor de fundo como azul metálico (RGB 30, 144, 255)
		button.setFocusPainted(false); // Remove o foco pintado
		button.addActionListener(listener);
		button.setFont(button.getFont().deriveFont(20.0f)); // Aumenta o tamanho da fonte
		return button;
	}

	// Ações dos botões do menu
	private void showClienteGUI() {
		if (clienteGUI == null) {
			clienteGUI = new ClienteGUI();
		}
		clienteGUI.setVisible(true);
	}

	private void showOrdemServicoGUI() {
		if (osGUI == null) {
			osGUI = new OrdemServicoGUI();
		}
		osGUI.setVisible(true);
	}

	private void showPecasGUI() {
		if (pecasGUI == null) {
			pecasGUI = new PecasGUI();
		}
		pecasGUI.listarPecas(); // Atualiza a lista de peças ao abrir a GUI de Peças
		pecasGUI.setVisible(true);
	}

	private void showPedidoCompraGUI() {
		if (pedidoCompraGUI == null) {
			pedidoCompraGUI = new PedidoCompraGUI();
		}
		pedidoCompraGUI.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			new MenuPrincipal().setVisible(true);
		});
	}
}