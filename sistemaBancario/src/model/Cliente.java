package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	private String cpf;
	private String nome;
	private List<Conta> contas;

	public Cliente(String cpf, String nome) {
		this.cpf = cpf;
		this.nome = nome;
		this.contas = new ArrayList<>();
	}

	public void adicionarConta(Conta conta) {
		if (!contas.contains(conta)) {
			contas.add(conta);
			System.out.println("Conta adicionada com sucesso.");
		} else {
			System.out.println("Conta já existe para este cliente.");
		}
	}

	public void removerConta(Conta conta) {
		if (contas.contains(conta)) {
			contas.remove(conta);
			System.out.println("Conta removida com sucesso.");
		} else {
			System.out.println("Conta não encontrada para este cliente.");
		}
	}

	public Conta localizarContaPorNumero(int numero) {
		for (Conta conta : contas) {
			if (conta.getNumeroDaConta() == numero) {
				return conta;
			}
		}
		return null;
	}

	public String getCpf() {
		return cpf;
	}

	public String getNome() {
		return nome;
	}

	// Método getContas() adicionado
	public List<Conta> getContas() {
		return contas;
	}

	@Override
	public String toString() {
		return "Cliente [cpf=" + cpf + ", nome=" + nome + ", contas=" + contas + "]";
	}
}