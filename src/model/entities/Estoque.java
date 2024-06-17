package model.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Estoque {
	private int id;
	private String nome;
	private String descricao;
	private List<Pecas> pecas;

	public Estoque(int id, String nome, String descricao) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.pecas = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Pecas> getPecas() {
		return pecas;
	}

	public void adicionarPeca(Pecas peca) {
		pecas.add(peca);
	}

	public void removerPeca(Pecas peca) {
		pecas.remove(peca);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nome, descricao, pecas);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Estoque estoque = (Estoque) obj;
		return id == estoque.id && Objects.equals(nome, estoque.nome) && Objects.equals(descricao, estoque.descricao)
				&& Objects.equals(pecas, estoque.pecas);
	}
}