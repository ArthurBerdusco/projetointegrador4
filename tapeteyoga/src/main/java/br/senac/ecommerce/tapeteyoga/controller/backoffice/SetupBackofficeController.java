package br.senac.ecommerce.tapeteyoga.controller.backoffice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import br.senac.ecommerce.tapeteyoga.model.Usuario;
import br.senac.ecommerce.tapeteyoga.repository.UsuarioRepository;

@Controller
public class SetupBackofficeController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/setup")
    public RedirectView cadastrarUsuario(Model model) {

        Usuario usuario = new Usuario();

        usuario.setUsername("admin@root.com");
        usuario.setFullname("admin");
        usuario.setCpf("99999999999");
        usuario.setPassword(passwordEncoder.encode("admin"));
        usuario.setRole("Administrador");
        usuario.setActive(true);

        boolean userExists = repository.existsByUsername(usuario.getUsername());

        if (userExists) {
            model.addAttribute("mensagem", "Erro, usuário admin já cadastrado");
            return new RedirectView("/login/backoffice");
        }

        repository.save(usuario);

        return new RedirectView("/login/backoffice");
    }
}
