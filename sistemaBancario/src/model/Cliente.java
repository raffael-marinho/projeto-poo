package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;
	private String cpf;
	private String nome;
	private List<IConta> contas;

	public Cliente(String cpf, String nome) {
		this.cpf = cpf;
		this.nome = nome;
		this.contas = new ArrayList<>();
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<IConta> getContas() {
		return contas;
	}

	public void setContas(List<IConta> contas) {
		this.contas = contas;
	}

	public void adicionarConta(IConta conta) {
		if (contas.contains(conta)) {
			System.out.println("A conta já está associada a este cliente.");
		} else {
			contas.add(conta);
			System.out.println("Conta adicionada com sucesso!");
		}
	}

	public void removerConta(IConta conta) {
		if (contas.contains(conta)) {
			contas.remove(conta);
			System.out.println("Conta removida com sucesso!");
		} else {
			System.out.println("A conta não está associada a este cliente.");
		}
	}

	public IConta localizarContaNumero(String numero) throws ContaNaoEncontradaException {
		for (IConta conta : contas) {
			if (conta.getNumero().equals(numero)) {
				return conta;
			}
		}
		throw new ContaNaoEncontradaException("Conta não encontrada.");
	}

	public double balancoEntreContas() {
		double total = 0.0;
		for (IConta conta : contas) {
			total += conta.getSaldo().doubleValue();
		}
		System.out.println("Balanço entre contas: R$" + total);
		return total;
	}

	@Override
	public String toString() {
		return "Cliente [cpf=" + cpf + ", nome=" + nome + ", contas=" + contas + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(cpf, other.cpf);
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}
}
