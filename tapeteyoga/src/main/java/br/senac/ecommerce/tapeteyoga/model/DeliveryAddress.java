package br.senac.ecommerce.tapeteyoga.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table
public class DeliveryAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isDefault;
    
    private String zipCode;
    
    private String street;

    private String number;
    
    private String complement;
    
    private String neighborhood;
    
    private String city;
    
    private String state;

    @ManyToOne
    @JoinColumn(name = "client_id" , foreignKey = @ForeignKey(name = "fk_delivery_address"))
    private Client client;

    
    public String toString() {
        return "DeliveryAddress{" +
                "zipCode='" + zipCode + '\'' +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", complement='" + complement + '\'' +
                ", neighborhood='" + neighborhood + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
    
}
