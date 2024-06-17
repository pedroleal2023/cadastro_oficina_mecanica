package model.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

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
import javax.swing.border.EmptyBorder;

import model.dao.PecaDAO;
import model.entities.Pecas;

public class PecasGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTextField codigoField;
	private JTextField nomeField;
	private JTextField descricaoField;
	private JTextField quantidadeField;
	private JList<Pecas> pecaList;
	private DefaultListModel<Pecas> listModel;
	private PecaDAO pecaDAO;

	public PecasGUI() {
		pecaDAO = new PecaDAO();
		setTitle("Gerenciamento de Peças e Estoque");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel mainPanel = new JPanel(new BorderLayout());

		// Painel para o formulário de peças
		JPanel formPanel = new JPanel(new GridBagLayout());
		formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 5, 5);

		JLabel codigoLabel = new JLabel("Código:");
		codigoField = new JTextField(20);
		formPanel.add(codigoLabel, gbc);

		gbc.gridx++;
		formPanel.add(codigoField, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		JLabel nomeLabel = new JLabel("Nome:");
		formPanel.add(nomeLabel, gbc);

		gbc.gridx++;
		nomeField = new JTextField(20);
		formPanel.add(nomeField, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		JLabel descricaoLabel = new JLabel("Descrição:");
		formPanel.add(descricaoLabel, gbc);

		gbc.gridx++;
		descricaoField = new JTextField(20);
		formPanel.add(descricaoField, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		JLabel quantidadeLabel = new JLabel("Quantidade:");
		formPanel.add(quantidadeLabel, gbc);

		gbc.gridx++;
		quantidadeField = new JTextField(20);
		formPanel.add(quantidadeField, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JButton adicionarButton = new JButton("Adicionar");
		adicionarButton.addActionListener(e -> adicionarPeca());
		formPanel.add(adicionarButton, gbc);

		mainPanel.add(formPanel, BorderLayout.NORTH);

		// Painel para a lista de peças e estoque
		JPanel listaPanel = new JPanel(new BorderLayout());

		// Botão sem função para o título destacado
		JButton tituloButton = new JButton("Lista de Peças no Estoque:");
		tituloButton.setEnabled(false); // Desabilita o botão para não ser clicável
		tituloButton.setFont(new Font("Arial", Font.BOLD, 18)); // Define a fonte e tamanho do texto

		listaPanel.add(tituloButton, BorderLayout.NORTH);

		listModel = new DefaultListModel<>();
		pecaList = new JList<>(listModel);
		pecaList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		pecaList.addListSelectionListener(e -> selecionarPeca());

		listaPanel.add(new JScrollPane(pecaList), BorderLayout.CENTER);

		mainPanel.add(listaPanel, BorderLayout.CENTER);

		add(mainPanel);

		listarPecas();
	}

	private void adicionarPeca() {
		try {
			int quantidade = Integer.parseInt(quantidadeField.getText());
			Pecas peca = new Pecas(0, // Defina como 0 ou deixe para o DAO atribuir
					codigoField.getText(), nomeField.getText(), descricaoField.getText(), quantidade);
			pecaDAO.adicionarPeca(peca);
			listModel.addElement(peca);
			limparCampos();
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Por favor, insira uma quantidade válida.", "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	void listarPecas() {
		List<Pecas> pecas = pecaDAO.listarPecas();
		listModel.clear();
		for (Pecas peca : pecas) {
			listModel.addElement(peca);
		}
	}

	private void selecionarPeca() {
		Pecas peca = pecaList.getSelectedValue();
		if (peca != null) {
			codigoField.setText(peca.getCodigo());
			nomeField.setText(peca.getNome());
			descricaoField.setText(peca.getDescricao());
			quantidadeField.setText(String.valueOf(peca.getQuantidade()));
		}
	}

	private void limparCampos() {
		codigoField.setText("");
		nomeField.setText("");
		descricaoField.setText("");
		quantidadeField.setText("");
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new PecasGUI().setVisible(true);
		});
	}
}
