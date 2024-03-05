package br.senac.ecommerce.tapeteyoga.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Usuario {

    public Usuario(){

    }

    public Usuario(String username, String password){
        this.username = username;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullname;
    private String cpf;
    private String email;
    private String senha;
    private String grupo;
    private String username;//EMAIL
    private boolean isActive;
    private String password;
    private String role;

    

}
