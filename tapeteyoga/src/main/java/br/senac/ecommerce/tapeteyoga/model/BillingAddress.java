package br.senac.ecommerce.tapeteyoga.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table
public class BillingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    

    private String zipCode;
    

    private String street;
    
 
    private String number;
    
    private String complement;
    

    private String neighborhood;
    

    private String city;
    

    private String state;

    @OneToOne
    @JoinColumn(name = "client_id", foreignKey = @ForeignKey(name = "fk_billing_address"))
    private Client client;

}

