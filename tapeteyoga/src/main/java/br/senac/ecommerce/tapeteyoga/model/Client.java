package br.senac.ecommerce.tapeteyoga.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String fullName;
    
    private String email;
    
    private String password;
    
    private String cpf;
    
    private LocalDate birthDate;
    
    private String gender;
    
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private BillingAddress billingAddress;
    
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<DeliveryAddress> deliveryAddresses;

}

