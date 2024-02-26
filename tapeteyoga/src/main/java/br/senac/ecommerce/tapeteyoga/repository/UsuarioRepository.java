package br.senac.ecommerce.tapeteyoga.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senac.ecommerce.tapeteyoga.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByEmail(String email);

}
