package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import exception.ContaInativaException;
import exception.SaldoInsuficienteException;
import exception.ValorInvalidoException;

public abstract class Conta implements IConta, Serializable {
	private static final long serialVersionUID = 1L;

	private Integer numeroDaConta;
	private BigDecimal saldo;
	private LocalDateTime dataAbertura;
	private boolean status;
	private List<Transacao> transacoes;

	public Conta(Integer numero) {
		this.numeroDaConta = numero;
		this.saldo = BigDecimal.ZERO;
		this.dataAbertura = LocalDateTime.now();
		this.status = true;
		this.transacoes = new ArrayList<>();
	}

	@Override
	public void depositar(BigDecimal quantia) throws ValorInvalidoException, ContaInativaException {
		if (!status) {
			throw new ContaInativaException("Conta inativa. Operação não permitida.");
		}
		if (quantia.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ValorInvalidoException("Valor inválido para depósito.");
		}
		this.saldo = this.saldo.add(quantia);
		transacoes.add(new Transacao(TipoTransacao.CREDITO, quantia));
		System.out.println("Depósito realizado com sucesso.");
	}

	@Override
	public void sacar(BigDecimal quantia)
			throws SaldoInsuficienteException, ValorInvalidoException, ContaInativaException {
		if (!status) {
			throw new ContaInativaException("Conta inativa. Operação não permitida.");
		}
		if (quantia.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ValorInvalidoException("Valor inválido para saque.");
		}
		if (this.saldo.compareTo(quantia) < 0) {
			throw new SaldoInsuficienteException("Saldo insuficiente para realizar o saque.");
		}
		this.saldo = this.saldo.subtract(quantia);
		transacoes.add(new Transacao(TipoTransacao.DEBITO, quantia));
		System.out.println("Saque realizado com sucesso.");
	}

	@Override
	public void transferir(IConta outraConta, BigDecimal quantia)
			throws SaldoInsuficienteException, ValorInvalidoException, ContaInativaException {
		if (!status || !outraConta.isStatus()) {
			throw new ContaInativaException("Uma das contas está inativa. Operação não permitida.");
		}
		if (quantia.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ValorInvalidoException("Valor inválido para transferência.");
		}
		if (this.saldo.compareTo(quantia) < 0) {
			throw new SaldoInsuficienteException("Saldo insuficiente para realizar a transferência.");
		}

		// Cobrança de tarifa para transferências entre contas diferentes
		BigDecimal taxa = BigDecimal.ZERO;
		if (!this.getClass().equals(outraConta.getClass())) {
			taxa = quantia.multiply(new BigDecimal("0.01")); // 1% de taxa
			this.saldo = this.saldo.subtract(quantia.add(taxa));
			transacoes.add(new Transacao(TipoTransacao.DEBITO, quantia.add(taxa)));
			System.out.println("Taxa de transferência: " + taxa);
		} else {
			this.saldo = this.saldo.subtract(quantia);
			transacoes.add(new Transacao(TipoTransacao.DEBITO, quantia));
		}

		outraConta.depositar(quantia);
		System.out.println("Transferência realizada com sucesso.");
	}

	@Override
	public BigDecimal getSaldo() {
		return saldo;
	}

	@Override
	public int getNumeroDaConta() {
		return numeroDaConta;
	}

	@Override
	public boolean isStatus() {
		return status;
	}

	public List<Transacao> getTransacoes() {
		return transacoes;
	}

	@Override
	public String toString() {
		return "Conta [numeroDaConta=" + numeroDaConta + ", saldo=" + saldo + ", dataAbertura=" + dataAbertura
				+ ", status=" + status + "]";
	}
}