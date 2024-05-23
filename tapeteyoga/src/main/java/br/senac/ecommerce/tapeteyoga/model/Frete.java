package br.senac.ecommerce.tapeteyoga.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Frete {

    private Long id;
    private String tipoEntrega;
    private BigDecimal valor;
    private Integer dias;

    public Frete(Long id, String tipoEntrega, BigDecimal valor, Integer dias) {
        this.id = id;
        this.tipoEntrega = tipoEntrega;
        this.valor = valor;
        this.dias = dias;
    }

    public BigDecimal calcularFrete(BigDecimal distanciaEmKm) {
        return distanciaEmKm.multiply(valor);
    }

}

