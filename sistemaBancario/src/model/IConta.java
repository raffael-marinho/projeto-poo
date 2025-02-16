package model;

import java.math.BigDecimal;

public interface IConta {

    public static final float TAXA_ADMINISTRATIVA = 0.02f;

    public boolean isStatus();
    public BigDecimal getSaldo();
    public void setSaldo(BigDecimal novoSaldo);
    public void realizarSaque(BigDecimal quantia);
    public void realizarDeposito(BigDecimal quantia);
    public void realizarTransferencia(IConta destino, BigDecimal quantia);
}	