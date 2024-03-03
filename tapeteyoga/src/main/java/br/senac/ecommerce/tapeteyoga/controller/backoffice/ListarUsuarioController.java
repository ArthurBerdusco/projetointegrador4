package br.senac.ecommerce.tapeteyoga.controller.backoffice;

import br.senac.ecommerce.tapeteyoga.model.Usuario;
import br.senac.ecommerce.tapeteyoga.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ListarUsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @GetMapping("/backoffice/listar-usuarios")
    public String listarUsuarios(Model model) {
        System.out.println("cheguei");
        List<Usuario> usuarios = repository.findAll();
        System.out.println(usuarios);
        model.addAttribute("usuarios", usuarios);
        return "backoffice/listar-usuarios";
    }
}
