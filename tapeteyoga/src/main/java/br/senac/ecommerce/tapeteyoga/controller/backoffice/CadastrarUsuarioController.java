package br.senac.ecommerce.tapeteyoga.controller.backoffice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CadastrarUsuarioController {
    
    @GetMapping("/backoffice/cadastrar-usuario")
    public String cadastrarUsuario(){
        return "/backoffice/cadastrar-usuario";
    }

}
