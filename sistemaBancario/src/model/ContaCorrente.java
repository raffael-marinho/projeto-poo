package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ContaCorrente extends Conta {
	public ContaCorrente(String numero) {
		super(numero);
	}

	@Override
	public boolean realizarSaque(BigDecimal quantia) {
		if (quantia.compareTo(BigDecimal.ZERO) <= 0 || quantia.compareTo(getSaldo()) > 0) {
			throw new IllegalArgumentException("Quantia inválida ou saldo insuficiente.");
		}
		setSaldo(getSaldo().subtract(quantia));
		getTransacoes().add(new Transacao(quantia, LocalDateTime.now(), "DEBITO", null));
		return true;
	}

	@Override
	public boolean realizarTransferencia(Conta destino, BigDecimal quantia) {
		if (destino == null || quantia.compareTo(BigDecimal.ZERO) <= 0 || quantia.compareTo(getSaldo()) > 0) {
			throw new IllegalArgumentException("Dados inválidos para transferência.");
		}
		setSaldo(getSaldo().subtract(quantia));
		destino.setSaldo(destino.getSaldo().add(quantia));
		getTransacoes().add(new Transacao(quantia, LocalDateTime.now(), "TRANSFERÊNCIA", destino));
		return true;
	}

	@Override
	public String toString() {
		return "ContaCorrente [numero=" + getNumero() + ", saldo=" + getSaldo() + ", dataAbertura=" + getDataAbertura()
				+ ", status=" + isStatus() + "]";
	}
}
