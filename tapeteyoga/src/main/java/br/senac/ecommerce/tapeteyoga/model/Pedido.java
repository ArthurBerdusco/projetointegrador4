package br.senac.ecommerce.tapeteyoga.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Client cliente;

    private String dataPedido;

    @OneToMany(mappedBy = "pedido", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ItemPedido> itens;

    private BigDecimal total;

    @Override
    public String toString() {
        return "Pedido{"
                + "id=" + id
                + ", cliente=" + cliente
                + ", total=" + total
                + '}';
    }

    //private String enderecoEntrega;
    //private String metodoPagamento;
    //private String status;
}
