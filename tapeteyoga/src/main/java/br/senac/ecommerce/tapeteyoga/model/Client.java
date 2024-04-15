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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(min = 3, max = 255)
    private String fullName;
    
    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    @Size(min = 6, max = 255)
    private String password;
    
    @NotBlank
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")
    private String cpf;
    
    @NotNull
    private LocalDate birthDate;
    
    @NotBlank
    private String gender;
    
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private BillingAddress billingAddress;
    
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<DeliveryAddress> deliveryAddresses;
    
}

