package br.senac.ecommerce.tapeteyoga.controller.backoffice;

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
        if (repository.findByUsername(usuario.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Email já cadastrado, tente novamente");
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        Usuario savedUser = repository.save(usuario);

        return ResponseEntity.ok(savedUser);

    }

    @PostMapping("/backoffice/cadastrar-usuario")
    public String cadastrarUsuario(Usuario usuario, @RequestParam("confirmaSenha") String confirmaSenha, RedirectAttributes attr) {

        String grupo = "administrador".equals(usuario.getGrupo()) ? "administrador" : "estoquista";
        usuario.setGrupo(grupo);

        if (!usuario.getSenha().equals(confirmaSenha)) {
            attr.addFlashAttribute("senhaErro", "As senhas não são iguais.");
            return "redirect:/backoffice/cadastrar-usuario";
        }



       usuarioRepository.save(usuario);
        return "redirect:/backoffice/cadastrar-usuario";
    }

}
