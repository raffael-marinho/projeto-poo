package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Conta {

	private String numero;
	private BigDecimal saldo;
	private LocalDateTime dataAbertura;
	private boolean status;
	private List<Transacao> transacoes;

	public Conta(String numero, BigDecimal saldo, LocalDateTime dataAbertura, boolean status) {
		this.numero = numero;
		this.saldo = BigDecimal.ZERO.setScale(2);
		this.dataAbertura = LocalDateTime.now();
		this.status = true;
		this.transacoes = new ArrayList<>();
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public LocalDateTime getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(LocalDateTime dataAbertura) {
		this.dataAbertura = dataAbertura;
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

	public void setTransacoes(List<Transacao> transacoes) {
		this.transacoes = transacoes;
	}

	public void realizarDeposito(BigDecimal quantia) {
		if (quantia.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Quantia inválida para depósito.");
		}
		this.saldo = this.saldo.add(quantia);
		transacoes.add(new Transacao(quantia, LocalDateTime.now(), "CREDITO", null));
	}

	public abstract boolean realizarSaque(BigDecimal quantia);

	public abstract boolean realizarTransferencia(Conta destino, BigDecimal quantia);

	@Override
	public int hashCode() {
		return Objects.hash(numero);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Conta other = (Conta) obj;
		return Objects.equals(numero, other.numero);
	}

	@Override
	public String toString() {
		return "Conta [numero=" + numero + ", saldo=" + saldo + ", dataAbertura=" + dataAbertura + ", status=" + status
				+ "]";
	}

}
