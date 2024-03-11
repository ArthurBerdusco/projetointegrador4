package br.senac.ecommerce.tapeteyoga.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 200, message = "Nome do produto deve ter no máximo 200 caracteres")
    private String name;

    @Size(max = 2000, message = "A descrição deve ter no máximo 2000 caracteres")
    private String description;

    @NotNull(message = "O valor não pode ser nulo")
    @Min(value = 0, message = "não pode ser negativo")
    private BigDecimal price;

    @Min(value = 0, message = "Não pode ser negativo")
    @NotNull(message = "O valor não pode ser nulo")
    private Integer stockQuantity;

}
