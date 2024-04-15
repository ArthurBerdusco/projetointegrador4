package br.senac.ecommerce.tapeteyoga.model;

import java.time.LocalDate;
import java.util.List;

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
    @Pattern(regexp = "^(\\p{L}{3,}+\\s){1,}\\p{L}{3,}+$", message = "O nome deve conter pelo menos duas palavras com no mínimo 3 letras em cada palavra")
    private String fullName;
    
    @NotBlank(message = "O email não pode estar em branco")
    @Email(message = "O email deve ser válido")
    private String email;
    
    @NotBlank(message = "A senha não pode estar em branco")
    @Size(min = 6, max = 255, message = "A senha deve ter entre 6 e 255 caracteres")
    private String password;
    
    @NotBlank(message = "O CPF não pode estar em branco")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "O CPF deve estar no formato '999.999.999-99'")
    private String cpf;
    
    @NotNull(message = "A data de nascimento não pode estar em branco")
    private LocalDate birthDate;
    
    @NotBlank(message = "O gênero não pode estar em branco")
    private String gender;
    
    private BillingAddress billingAddress;
    
    private List<DeliveryAddress> deliveryAddresses;

}
