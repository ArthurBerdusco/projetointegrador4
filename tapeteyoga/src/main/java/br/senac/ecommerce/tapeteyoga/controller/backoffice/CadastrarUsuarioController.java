package br.senac.ecommerce.tapeteyoga.controller.backoffice;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import br.senac.ecommerce.tapeteyoga.model.Usuario;
import br.senac.ecommerce.tapeteyoga.repository.UsuarioRepository;

@RestController
public class CadastrarUsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("backoffice/cadastrar-usuario")
    public ResponseEntity<?> cadastrarUsuario(@ModelAttribute Usuario usuario) {
        // Check if the username is already taken
        Optional<Usuario> user = repository.findByUsername(usuario.getUsername());
        if (user.isPresent()) {
            return ResponseEntity.badRequest().body("Email já cadastrado, tente novamente");
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        Usuario savedUser = repository.save(usuario);

        return ResponseEntity.ok(savedUser);
    }

}
