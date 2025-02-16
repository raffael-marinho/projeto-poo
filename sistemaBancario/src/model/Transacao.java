package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transacao {

	private long id;
	private BigDecimal quantia;
	private LocalDateTime dataTransacao;
	private TipoTransacao tipoTransacao;
	private IConta destino;

	public Transacao(BigDecimal quantia, LocalDateTime dataTransacao, TipoTransacao tipoTransacao, IConta destino) {
		this.id = System.currentTimeMillis();
		this.quantia = quantia;
		this.dataTransacao = dataTransacao;
		this.tipoTransacao = tipoTransacao;
		this.destino = destino;
	}

	public long getId() {
		return id;
	}

	public BigDecimal getQuantia() {
		return quantia;
	}

	public LocalDateTime getDataTransacao() {
		return dataTransacao;
	}

	public TipoTransacao getTipoTransacao() {
		return tipoTransacao;
	}

	public IConta getDestino() {
		return destino;
	}

	@Override
	public String toString() {
		return "Transacao [id=" + id + ", quantia=" + quantia + ", dataTransacao=" + dataTransacao + ", tipoTransacao="
				+ tipoTransacao + ", destino=" + destino + "]";
	}
}