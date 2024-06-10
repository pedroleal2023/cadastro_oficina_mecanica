package model.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import model.dao.OrdemServicoDAO;
import model.entities.OrdemServico;

public class FaturamentoGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JList<OrdemServico> osList;
	private DefaultListModel<OrdemServico> listModel;
	private JTextField valorServicoField;
	private JTextField valorPecasField;
	private JButton registrarButton;
	private OrdemServicoDAO osDAO;

	public FaturamentoGUI() {
		setTitle("Controle de Faturamento");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// Panel de entrada
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
		inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel titleLabel = new JLabel("Faturamento de Ordem de Serviço");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		inputPanel.add(titleLabel);

		inputPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		listModel = new DefaultListModel<>();
		osList = new JList<>(listModel);
		JScrollPane scrollPane = new JScrollPane(osList);
		scrollPane.setPreferredSize(new Dimension(400, 300));
		inputPanel.add(scrollPane);

		inputPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		valorServicoField = new JTextField(20);
		inputPanel.add(createLabeledPanel("Valor do Serviço:", valorServicoField));

		valorPecasField = new JTextField(20);
		inputPanel.add(createLabeledPanel("Valor das Peças:", valorPecasField));

		registrarButton = new JButton("Registrar Faturamento");
		registrarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				registrarFaturamento();
			}
		});
		inputPanel.add(registrarButton);

		// Adiciona inputPanel ao JFrame
		getContentPane().add(inputPanel, BorderLayout.CENTER);

		osDAO = new OrdemServicoDAO();
		listarOrdensDeServicoConcluidas();
	}

	private JPanel createLabeledPanel(String labelText, JComponent component) {
		JPanel panel = new JPanel();
		JLabel label = new JLabel(labelText);
		panel.add(label);
		panel.add(component);
		return panel;
	}

	private void listarOrdensDeServicoConcluidas() {
		List<OrdemServico> ordensConcluidas = osDAO.listarOrdemServicoConcluidas();
		listModel.clear();
		for (OrdemServico os : ordensConcluidas) {
			listModel.addElement(os);
		}
	}

	private void registrarFaturamento() {
		OrdemServico os = osList.getSelectedValue();
		if (os != null) {
			try {
				double valorServico = Double.parseDouble(valorServicoField.getText());
				double valorPecas = Double.parseDouble(valorPecasField.getText());

				os.setValorServico(valorServico);
				os.setValorPecas(valorPecas);

				// Aqui você precisará de uma lógica para registrar o faturamento da ordem de
				// serviço
				// registrarFaturamento(os);

				JOptionPane.showMessageDialog(this, "Faturamento registrado com sucesso!");
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Valores inválidos. Por favor, insira valores numéricos.");
			}
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			FaturamentoGUI faturamentoGUI = new FaturamentoGUI();
			faturamentoGUI.setVisible(true);
		});
	}
}
