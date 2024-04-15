package br.senac.ecommerce.tapeteyoga.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table
public class BillingAddress {
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

    @OneToOne
    @JoinColumn(name = "client_id", foreignKey = @ForeignKey(name = "fk_billing_address"))
    private Client client;

}

