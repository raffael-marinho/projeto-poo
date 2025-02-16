package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Conta implements IConta {

	private String numero;
	private BigDecimal saldo;
	private LocalDateTime dataAbertura;
	private boolean status;
	private List<Transacao> transacoes;

	public Conta(String numero) {
		this.numero = numero;
		this.saldo = BigDecimal.ZERO.setScale(2);
		this.dataAbertura = LocalDateTime.now();
		this.status = true;
		this.transacoes = new ArrayList<>();
	}

	public String getNumero() {
		return numero;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	protected void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public LocalDateTime getDataAbertura() {
		return dataAbertura;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<Transacao> getTransacoes() {
		return transacoes;
	}

	@Override
	public void realizarDeposito(BigDecimal quantia) {
		if (quantia.compareTo(BigDecimal.ZERO) > 0) {
			saldo = saldo.add(quantia);
			transacoes.add(new Transacao(quantia, LocalDateTime.now(), "CREDITO", null));
			System.out.println("Depósito realizado com sucesso.");
		} else {
			System.out.println("Valor inválido para depósito.");
		}
	}

	@Override
	public void realizarSaque(BigDecimal quantia) {
		if (quantia.compareTo(BigDecimal.ZERO) > 0 && saldo.compareTo(quantia) >= 0) {
			saldo = saldo.subtract(quantia);
			transacoes.add(new Transacao(quantia, LocalDateTime.now(), "DEBITO", null));
			System.out.println("Saque realizado com sucesso.");
		} else {
			System.out.println("Saldo insuficiente ou valor inválido para saque.");
		}
	}

	@Override
	public void realizarTransferencia(IConta destino, BigDecimal quantia) {
		if (quantia.compareTo(BigDecimal.ZERO) > 0 && saldo.compareTo(quantia) >= 0) {
			saldo = saldo.subtract(quantia);
			destino.setSaldo(destino.getSaldo().add(quantia));
			transacoes.add(new Transacao(quantia, LocalDateTime.now(), "TRANSFERÊNCIA_DEBITO", destino));
			destino.getTransacoes().add(new Transacao(quantia, LocalDateTime.now(), "TRANSFERÊNCIA_CREDITO", this));
			System.out.println("Transferência realizada com sucesso.");
		} else {
			System.out.println("Saldo insuficiente ou valor inválido para transferência.");
		}
	}

	@Override
	public String toString() {
		return "Conta [numero=" + numero + ", saldo=" + saldo + ", dataAbertura=" + dataAbertura + ", status=" + status
				+ "]";
	}
}
