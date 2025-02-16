package model;

import java.math.BigDecimal;

public class ContaCorrente extends Conta {

    public ContaCorrente(String numero) {
        super(numero);
    }

    // Sobrescreve o método para aplicar a tarifa específica para ContaCorrente
    @Override
    public BigDecimal aplicarTarifa(BigDecimal valor) {
        // Para ContaCorrente, aplica a tarifa de 2% (0.02) apenas nas transferências
        return valor.add(valor.multiply(new BigDecimal(IConta.TAXA_ADMINISTRATIVA)));
    }

    @Override
    public String toString() {
        return "ContaCorrente [numero=" + numero + ", saldo=" + saldo + ", status=" + status + "]";
    }
}
