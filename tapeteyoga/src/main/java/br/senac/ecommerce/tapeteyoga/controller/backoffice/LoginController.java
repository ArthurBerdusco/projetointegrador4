package br.senac.ecommerce.tapeteyoga.controller.backoffice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.senac.ecommerce.tapeteyoga.model.Usuario;

@Controller
public class LoginController {


    @GetMapping("/admin")
    public String adminIndex(Usuario usuario) {
        return "backoffice/admin";
    }

    @PostMapping("/backoffice")
    public String processLogin(Usuario usuario, Model model) {
        if(usuario.getSenha().trim().equalsIgnoreCase("1234")){
            return "backoffice/backoffice";
        }
        return "backoffice/admin";
    }
}
