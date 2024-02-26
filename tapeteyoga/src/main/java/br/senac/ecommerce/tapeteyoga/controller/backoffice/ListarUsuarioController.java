package br.senac.ecommerce.tapeteyoga.controller.backoffice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ListarUsuarioController {
    
    @GetMapping("/backoffice/listar-usuarios")
    public String listarUsuario() {
        return "backoffice/listar-usuarios";
    }
    
}
