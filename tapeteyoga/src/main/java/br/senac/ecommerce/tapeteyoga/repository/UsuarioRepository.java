package br.senac.ecommerce.tapeteyoga.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import br.senac.ecommerce.tapeteyoga.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByUsernameOrCpf(String username, String cpf);
    boolean existsByCpfAndIdNot(String cpf, Long id);

    boolean existsByUsername(String username);
    boolean existsByCpf(String cpf);

    Optional<Usuario> findByUsername(String username);

    List<Usuario> findByFullnameContainingIgnoreCase(String fullname);

}
