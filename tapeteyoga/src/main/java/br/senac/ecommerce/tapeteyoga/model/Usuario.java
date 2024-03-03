package br.senac.ecommerce.tapeteyoga.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private GrupoUsuario grupo;

    public Usuario(String email, String senha, GrupoUsuario grupo){
        this.email = email;
        this.senha = senha;
        this.grupo = grupo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        if(this.grupo == GrupoUsuario.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_ADMIN"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));

    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return nome;
    }

    public String getEmail(){
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
