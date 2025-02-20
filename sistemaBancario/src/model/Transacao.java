package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transacao implements Serializable {
	private static final long serialVersionUID = 1L;

	private TipoTransacao tipo;
	private BigDecimal valor;
	private LocalDateTime data;

	public Transacao(TipoTransacao tipo, BigDecimal valor) {
		this.tipo = tipo;
		this.valor = valor;
		this.data = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "Transacao [tipo=" + tipo + ", valor=" + valor + ", data=" + data + "]";
	}
}