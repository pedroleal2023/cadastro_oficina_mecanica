
package model.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		setTitle("Gerenciamento de Peças");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel mainPanel = new JPanel(new BorderLayout());

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
		adicionarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				adicionarPeca();
			}
		});
		formPanel.add(adicionarButton, gbc);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton atualizarButton = new JButton("Atualizar");
		JButton deletarButton = new JButton("Deletar");

		atualizarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				atualizarPeca();
			}
		});

		deletarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deletarPeca();
			}
		});

		buttonPanel.add(atualizarButton);
		buttonPanel.add(deletarButton);

		mainPanel.add(formPanel, BorderLayout.NORTH);
		mainPanel.add(buttonPanel, BorderLayout.CENTER);

		listModel = new DefaultListModel<>();
		pecaList = new JList<>(listModel);
		pecaList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		pecaList.addListSelectionListener(e -> selecionarPeca());

		mainPanel.add(new JScrollPane(pecaList), BorderLayout.SOUTH);

		add(mainPanel);

		listarPecas();
	}

	private void adicionarPeca() {
		try {
			int quantidade = Integer.parseInt(quantidadeField.getText());
			Pecas peca = new Pecas(0, // Seu construtor espera um ID, defina como 0 ou deixe para o DAO atribuir
					codigoField.getText(), nomeField.getText(), descricaoField.getText(), quantidade);
			pecaDAO.adicionarPeca(peca);
			listModel.addElement(peca); // Adiciona a peça à lista na GUI
			limparCampos();
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Por favor, insira uma quantidade válida.", "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void atualizarPeca() {
		Pecas peca = pecaList.getSelectedValue();
		if (peca != null) {
			peca.setCodigo(codigoField.getText());
			peca.setNome(nomeField.getText());
			peca.setDescricao(descricaoField.getText());
			try {
				int quantidade = Integer.parseInt(quantidadeField.getText());
				peca.setQuantidade(quantidade);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Por favor, insira uma quantidade válida.", "Erro",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			pecaDAO.atualizarPeca(peca);
			pecaList.repaint();
			limparCampos();
		}
	}

	private void deletarPeca() {
		Pecas peca = pecaList.getSelectedValue();
		if (peca != null) {
			pecaDAO.deletarPeca(peca.getId());
			listModel.removeElement(peca);
			limparCampos();
		}
	}

	private void listarPecas() {
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
			PecasGUI pecaGUI = new PecasGUI();
			pecaGUI.setVisible(true);
		});
	}
}
