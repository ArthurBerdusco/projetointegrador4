package br.senac.ecommerce.tapeteyoga.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import br.senac.ecommerce.tapeteyoga.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmailOrCpf(String email, String cpf);
    boolean existsByCpfAndIdNot(String cpf, Long id);

    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByFullnameContainingIgnoreCase(String fullname);

}
