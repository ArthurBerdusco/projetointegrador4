package br.senac.ecommerce.tapeteyoga.controller.backoffice;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.senac.ecommerce.tapeteyoga.model.Usuario;
import br.senac.ecommerce.tapeteyoga.repository.UsuarioRepository;

@Controller
public class LoginController {

    @Autowired
    UsuarioRepository repository;

    @GetMapping("/admin")
    public String adminIndex(Usuario usuario) {
        return "backoffice/admin";
    }

    @PostMapping("/backoffice")
    public String processLogin(Usuario usuario, Model model) {
        Optional<Usuario> optionalDbUser = Optional.ofNullable(repository.findByEmail(usuario.getEmail()));
    
        if (optionalDbUser.isPresent()) {
            Usuario dbUser = optionalDbUser.get();
            if (dbUser.getSenha().equals(usuario.getSenha())) {
                return "backoffice/backoffice";
            }
        }
    
        return "backoffice/admin";
    }
    
}
