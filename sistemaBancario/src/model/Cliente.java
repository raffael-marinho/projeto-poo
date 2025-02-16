package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import exception.ClienteException;

public class Cliente implements Serializable {

	private String nome;
	private String cpf;
	private List<Conta> contas;

	public Cliente(String nome, String cpf) throws ClienteException {
		if (!validarCpf(cpf)) {
			throw new ClienteException("CPF inválido!");
		}
		this.nome = nome;
		this.cpf = cpf;
		this.contas = new ArrayList<>();
	}

	public void adicionarConta(Conta conta) throws ClienteException {
		if (conta == null) {
			throw new ClienteException("Conta não pode ser nula!");
		}
		if (contas.contains(conta)) {
			throw new ClienteException("Conta já associada ao cliente!");
		}
		contas.add(conta);
	}

	public boolean removerConta(Conta conta) {
		return contas.remove(conta);
	}

	public List<Conta> getContas() {
		return new ArrayList<>(contas); // Retorna uma cópia da lista
	}

	public String getCpf() {
		return cpf;
	}

	public String getNome() {
		return nome;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(cpf, other.cpf);
	}

	public boolean validarCpf(String cpf) {
		// Implementação simples de verificação de CPF
		return cpf != null && cpf.matches("\\d{11}");
	}

	@Override
	public String toString() {
		return "Cliente [nome=" + nome + ", cpf=" + cpf + ", contas=" + contas + "]";
	}
}
