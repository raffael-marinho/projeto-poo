package model;

import java.math.BigDecimal;

import exception.ContaException;

public interface IConta {

	// Constantes de taxas
	float TAXA_ADMINISTRATIVA = 0.02f;

	// Métodos essenciais
	boolean isStatus();

	BigDecimal getSaldo();

	void setSaldo(BigDecimal saldo);

	boolean realizarSaque(BigDecimal quantia) throws ContaException;

	boolean realizarDeposito(BigDecimal quantia) throws ContaException;

	boolean realizarTransferencia(IConta destino, BigDecimal quantia) throws ContaException;

	// Método para aplicar tarifa, será implementado de forma diferente em cada tipo
	// de conta
	BigDecimal aplicarTarifa(BigDecimal valor);

	void setStatus(boolean status);

	String getNumero();
}
