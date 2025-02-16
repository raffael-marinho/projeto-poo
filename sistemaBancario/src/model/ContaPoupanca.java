package model;

import java.math.BigDecimal;

public class ContaPoupanca extends Conta {
	public ContaPoupanca(String numero) {
		super(numero);
	}

	@Override
	public void realizarTransferencia(IConta destino, BigDecimal quantia) {
		if (destino instanceof ContaPoupanca) {
			BigDecimal taxa = quantia.multiply(BigDecimal.valueOf(IConta.TAXA_ADMINISTRATIVA));
			quantia = quantia.add(taxa);
		}
		super.realizarTransferencia(destino, quantia);
	}
}
