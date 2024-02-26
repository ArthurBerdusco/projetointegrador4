package br.senac.ecommerce.tapeteyoga.controller.backoffice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EditarUsuarioController {

    @GetMapping("/backoffice/editar-usuario")
    public String listarUsuario() {
        return "backoffice/editar-usuario";
    }

}
