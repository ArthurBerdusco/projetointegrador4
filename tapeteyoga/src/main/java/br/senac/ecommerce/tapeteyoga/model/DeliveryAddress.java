package br.senac.ecommerce.tapeteyoga.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table
public class DeliveryAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    private String zipCode;
    
    @NotBlank
    private String street;
    
    @NotBlank
    private String number;
    
    private String complement;
    
    @NotBlank
    private String neighborhood;
    
    @NotBlank
    private String city;
    
    @NotBlank
    private String state;

    @ManyToOne
    @JoinColumn(name = "client_id" , foreignKey = @ForeignKey(name = "fk_delivery_address"))
    private Client client;
    
}
