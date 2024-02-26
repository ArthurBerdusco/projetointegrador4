package br.senac.ecommerce.tapeteyoga.controller.backoffice;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.senac.ecommerce.tapeteyoga.model.Usuario;

@Controller
public class CadastrarUsuarioController {
    
    @GetMapping("/backoffice/cadastrar-usuario")
    public String cadastrarUsuario(Usuario usuario, Model model){
        return "/backoffice/cadastrar-usuario";
    }

}
