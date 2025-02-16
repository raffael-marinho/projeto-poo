package model;

import java.math.BigDecimal;

public class ContaPoupanca extends Conta {
	public ContaPoupanca(String numero) {
		super(numero);
	}

	@Override
	public void realizarTransferencia(IConta destino, BigDecimal quantia) {
		super.realizarTransferencia(destino, quantia);
	}
}
