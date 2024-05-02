package br.senac.ecommerce.tapeteyoga.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class ItemCarrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Carrinho carrinho;

    @ManyToOne
    private Produto produto;

    private String imagem;

    private int quantidade;

    private BigDecimal total;

    public BigDecimal getTotal() {

        if (this.total == null) {
            this.total = BigDecimal.ZERO;
        }
        this.total = produto.getPrice().multiply(new BigDecimal(quantidade));
        return this.total;
    }

    public void addTotal(BigDecimal valor){

        if (this.total == null) {
            this.total = BigDecimal.ZERO;
        }

        this.total = this.total.add(valor);
    } 

    @Override
    public String toString() {
        return "ItemCarrinho{" +
                "id=" + id +
                ", carrinho=" + carrinho +
                ", imagem='" + imagem + '\'' +
                ", quantidade=" + quantidade +
                ", total=" + total +
                '}';
    }


}
