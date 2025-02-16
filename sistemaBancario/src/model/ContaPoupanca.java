package model;

import java.math.BigDecimal;

public class ContaPoupanca extends Conta {

	// Taxa de rendimento de 0.5% por exemplo
	private static final BigDecimal TAXA_RENDIMENTO = new BigDecimal("0.005");

	public ContaPoupanca(String numero) {
		super(numero);
	}

	// Sobrescreve o método para não aplicar nenhuma tarifa
	@Override
	public BigDecimal aplicarTarifa(BigDecimal valor) {
		return valor; // Não há tarifa na ContaPoupanca
	}

	// Método para calcular o rendimento da poupança (exemplo de rendimento mensal)
	public void aplicarRendimento() {
		if (saldo.compareTo(BigDecimal.ZERO) > 0) {
			BigDecimal rendimento = saldo.multiply(TAXA_RENDIMENTO);
			saldo = saldo.add(rendimento);
		}
	}

	@Override
	public String toString() {
		return "ContaPoupanca [numero=" + numero + ", saldo=" + saldo + ", status=" + status + "]";
	}
}
