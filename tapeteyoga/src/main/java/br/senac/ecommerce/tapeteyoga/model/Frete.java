package br.senac.ecommerce.tapeteyoga.model;

import java.math.BigDecimal;

import lombok.Data;

@Data

public class Frete {

    private String tipoEntrega;
    private BigDecimal precoPorKm;

    public Frete(String tipoEntrega, BigDecimal precoPorKm) {
        this.tipoEntrega = tipoEntrega;
        this.precoPorKm = precoPorKm;
    }

    public BigDecimal calcularFrete(BigDecimal distanciaEmKm) {
        return distanciaEmKm.multiply(precoPorKm);
    }

}
