package model;

import java.math.BigDecimal;

public class IConta {
	float TAXA_ADMINISTRATIVA = 0.02f;

	boolean isStatus();

	BigDecimal getSaldo();

	void setSaldo(BigDecimal novoSaldo);

	void realizarSaque(BigDecimal quantia);

	void realizarDeposito(BigDecimal quantia);

	void realizarTransferencia(IConta destino, BigDecimal quantia);
}
