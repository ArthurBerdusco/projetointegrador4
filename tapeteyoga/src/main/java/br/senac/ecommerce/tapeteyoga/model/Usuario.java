package br.senac.ecommerce.tapeteyoga.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
public class Usuario {

    public interface New {
        // Marcador de interface vazio para representar validações para novos objetos
    }
    
    public interface Existing {
        // Marcador de interface vazio para representar validações para objetos existentes
    }

    public Usuario() {
    }

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome completo é obrigatório")
    private String fullname;


    @Column(unique = true)
    @NotBlank(message = "O CPF é obrigatório")
    @Size(min = 11, max = 11, message = "O CPF deve ter exatamente 11 dígitos")
    @Pattern(regexp = "\\d+", message = "O CPF deve conter apenas dígitos numéricos")
    private String cpf;

    private String grupo;

    @Column(unique = true)
    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Formato de e-mail inválido")
    private String username;

    private boolean isActive;

    @NotBlank(message = "A senha é obrigatória", groups = {New.class})
    @Size(min = 4, message = "A senha deve ter pelo menos 4 caracteres", groups = {New.class})
    @Null(message = "A senha não pode ser especificada para um novo usuário", groups = {New.class})
    private String password;

    @NotBlank(message = "A função do usuário é obrigatória")
    private String role;
}


