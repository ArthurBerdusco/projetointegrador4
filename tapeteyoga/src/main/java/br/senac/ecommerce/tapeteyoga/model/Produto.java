package br.senac.ecommerce.tapeteyoga.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 200, message = "Nome do produto deve ter no máximo 200 caracteres")
    private String name;

    @Size(max = 2000, message = "A descrição deve ter no máximo 2000 caracteres")
    private String description;

    @Min(value = 1, message = "Nota deve estar entre 1 e 5")
    @Max(value = 5, message = "Nota deve estar entre 1 e 5")
    @NotNull(message = "O valor não pode ser nulo") 
    private BigDecimal rating;

    @NotNull(message = "O valor não pode ser nulo")
    @Min(value = 0, message = "não pode ser negativo")
    private BigDecimal price;

    @Min(value = 0, message = "Não pode ser negativo")
    @NotNull(message = "O valor não pode ser nulo")
    private Integer stockQuantity;

    private boolean isActive = true;

    private float avaliacao;

    @OneToMany(mappedBy = "produto", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<ImagemProduto> imagens;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getRating(){
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<ImagemProduto> getImagens() {
        return imagens;
    }

    public void setImagens(List<ImagemProduto> imagens) {
        this.imagens = imagens;
    }


    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                ", isActive=" + isActive +
                ", rating=" + rating +
                '}';
    }
}
