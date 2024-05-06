package br.senac.ecommerce.tapeteyoga.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BillingAddressDTO {
    
    private Long id;
    
    @NotBlank(message = "O CEP não pode estar em branco")
    private String zipCode;
    
    @NotBlank(message = "O endereço não pode estar em branco")
    private String street;
    
    @NotBlank(message = "O número não pode estar em branco")
    private String number;
    
    private String complement;
    
    @NotBlank(message = "O bairro não pode estar em branco")
    private String neighborhood;
    
    @NotBlank(message = "A cidade não pode estar em branco")
    private String city;
    
    @NotBlank(message = "O estado não pode estar em branco")
    private String state;

    private Client client;

    private String longitude;

    private String latitude;
}
