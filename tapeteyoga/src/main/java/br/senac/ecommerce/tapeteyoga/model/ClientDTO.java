package br.senac.ecommerce.tapeteyoga.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClientDTO {
    
    private Long id;
    
    @NotBlank(message = "O nome não pode estar em branco")
    @Pattern(regexp = "^(\\p{L}{3,}+\\s|de\\s){1,}\\p{L}{3,}+$", message = "O nome deve conter pelo menos duas palavras com no mínimo 3 letras em cada palavra")
    private String fullName;
    
    @NotBlank(message = "O email não pode estar em branco")
    @Email(message = "O email deve ser válido")
    private String email;
    
    @NotBlank(message = "A senha não pode estar em branco")
    @Size(min = 6, max = 255, message = "A senha deve ter entre 6 e 255 caracteres")
    private String password;
    
    @NotBlank(message = "O CPF não pode estar em branco")
    @CPF(message = "CPF INVÁLIDO")
    private String cpf;
    
    @NotNull(message = "A data de nascimento não pode estar em branco")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy")
    private LocalDate birthDate;
    
    @NotBlank(message = "O gênero não pode estar em branco")
    private String gender;
    
    @Valid
    private BillingAddressDTO billingAddress;
    
    private List<@Valid DeliveryAddressDTO> deliveryAddresses;

    public ClientDTO() {
        // Inicialize a lista de endereços de entrega

        if(deliveryAddresses == null){
            this.deliveryAddresses = new ArrayList<>();
            DeliveryAddressDTO deliveryAddress = new DeliveryAddressDTO();
            deliveryAddress.setZipCode("");
            deliveryAddress.setStreet("");
            deliveryAddress.setNumber("");
            deliveryAddress.setComplement("");
            deliveryAddress.setNeighborhood("");
            deliveryAddress.setCity("");
            deliveryAddress.setState("");
            this.deliveryAddresses.add(deliveryAddress);
        }
        
    }

}
