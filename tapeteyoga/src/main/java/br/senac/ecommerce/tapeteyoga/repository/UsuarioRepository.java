package br.senac.ecommerce.tapeteyoga.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import br.senac.ecommerce.tapeteyoga.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByUsernameOrCpf(String username, String cpf);
    boolean existsByUsername(String username);
    Optional<Usuario> findByUsername(String username);

}
