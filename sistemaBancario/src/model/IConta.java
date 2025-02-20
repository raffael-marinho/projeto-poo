package model;

import java.math.BigDecimal;

import exception.ContaInativaException;
import exception.SaldoInsuficienteException;
import exception.ValorInvalidoException;

public interface IConta {
	void depositar(BigDecimal quantia) throws ValorInvalidoException, ContaInativaException;

	void sacar(BigDecimal quantia) throws SaldoInsuficienteException, ValorInvalidoException, ContaInativaException;

	void transferir(IConta outraConta, BigDecimal quantia)
			throws SaldoInsuficienteException, ValorInvalidoException, ContaInativaException;

	BigDecimal getSaldo();

	int getNumeroDaConta();

	boolean isStatus();
}