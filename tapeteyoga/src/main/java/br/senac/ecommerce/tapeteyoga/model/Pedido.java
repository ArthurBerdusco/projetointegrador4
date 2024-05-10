package br.senac.ecommerce.tapeteyoga.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

    private Long serial;

    private String formaPagamento; // IMPLEMENTAR

    private BigDecimal valorFrete; // IMPLEMENTAR

    @ManyToOne
    @JoinColumn(name = "endereco_entrega_id") // Nome da coluna na tabela Pedido que referencia o DeliveryAddress
    private DeliveryAddress enderecoEntrega;

    @ManyToOne
    private Client cliente;

    private String dataPedido;

    @OneToMany(mappedBy = "pedido", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ItemPedido> itens;

    private BigDecimal total;

    private String status;

    @Override
    public String toString() {
        return "Pedido{"
                + "id=" + id
                + ", cliente=" + cliente
                + ", total=" + total
                + '}';
    }

}
