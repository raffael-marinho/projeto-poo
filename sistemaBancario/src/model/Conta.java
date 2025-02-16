package model;

import java.math.BigDecimal;
import java.util.Objects;

import exception.ContaException;

public abstract class Conta implements IConta {

	protected String numero;
	protected BigDecimal saldo;
	protected boolean status;

	public Conta(String numero) {
		this.numero = numero;
		this.saldo = BigDecimal.ZERO.setScale(2); // Inicializa o saldo com 2 casas decimais
		this.status = true;
	}

	@Override
	public String getNumero() {
		return numero;
	}

	@Override
	public BigDecimal getSaldo() {
		return saldo;
	}

	@Override
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo.setScale(2); // Garante que o saldo tenha 2 casas decimais
	}

	@Override
	public boolean isStatus() {
		return status;
	}

	@Override
	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public boolean realizarSaque(BigDecimal quantia) throws ContaException { // Lançando a exceção aqui
		if (isStatus() && quantia.compareTo(BigDecimal.ZERO) > 0 && quantia.compareTo(saldo) <= 0) {
			saldo = saldo.subtract(quantia);
			return true;
		}
		throw new ContaException("Saque não realizado. Verifique o saldo ou o status da conta.");
	}

	@Override
	public boolean realizarDeposito(BigDecimal quantia) throws ContaException {
		if (isStatus() && quantia.compareTo(BigDecimal.ZERO) > 0) {
			saldo = saldo.add(quantia);
			return true;
		}
		throw new ContaException("Depósito não realizado. Verifique o status da conta.");
	}

	@Override
	public boolean realizarTransferencia(IConta destino, BigDecimal quantia) throws ContaException {
		if (isStatus() && destino.isStatus() && quantia.compareTo(BigDecimal.ZERO) > 0) {
			BigDecimal valorComTarifa = aplicarTarifa(quantia);

			if (quantia.compareTo(saldo) <= 0) {
				saldo = saldo.subtract(valorComTarifa);
				destino.setSaldo(destino.getSaldo().add(quantia));
				return true;
			}
		}
		throw new ContaException("Transferência não realizada. Verifique o saldo ou o status das contas.");
	}

	@Override
	public abstract BigDecimal aplicarTarifa(BigDecimal valor);

	@Override
	public int hashCode() {
		return Objects.hash(numero);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Conta other = (Conta) obj;
		return Objects.equals(numero, other.numero);
	}
}
