package model;

import java.math.BigDecimal;

public class ContaPoupanca extends Conta {
	private static final BigDecimal TAXA_RENDIMENTO = new BigDecimal("0.005"); // 0.5% de rendimento mensal

	public ContaPoupanca(Integer numero) {
		super(numero);
	}

	public void aplicarRendimento() {
		BigDecimal rendimento = this.getSaldo().multiply(TAXA_RENDIMENTO);
		try {
			this.depositar(rendimento);
			System.out.println("Rendimento aplicado: " + rendimento);
		} catch (Exception e) {
			System.err.println("Erro ao aplicar rendimento: " + e.getMessage());
		}
	}
}