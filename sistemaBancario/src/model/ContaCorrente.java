package model;

import java.math.BigDecimal;

public class ContaCorrente extends Conta {
	public ContaCorrente(String numero) {
		super(numero);
	}

	@Override
	public void realizarTransferencia(IConta destino, BigDecimal quantia) {
		super.realizarTransferencia(destino, quantia);
	}
}
