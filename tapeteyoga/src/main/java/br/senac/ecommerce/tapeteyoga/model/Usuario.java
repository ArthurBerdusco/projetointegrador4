package br.senac.ecommerce.tapeteyoga.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Usuario {
    String nome;
    String senha;
    String email;
    String grupo;
    boolean desabilitado;
}
