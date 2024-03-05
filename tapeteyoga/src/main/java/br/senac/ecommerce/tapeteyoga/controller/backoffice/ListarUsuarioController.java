package br.senac.ecommerce.tapeteyoga.controller.backoffice;

import br.senac.ecommerce.tapeteyoga.model.Usuario;
import br.senac.ecommerce.tapeteyoga.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ListarUsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @GetMapping("/backoffice/listar-usuarios")
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = repository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "backoffice/listar-usuarios";
    }

    @GetMapping("/backoffice/procurar")
    public String procurar(Model model, @RequestParam(name = "fullname", required = false) String fullname){
        List<Usuario> usuarios = repository.findByFullnameContainingIgnoreCase(fullname);
        model.addAttribute("usuarios", usuarios);
        return "backoffice/listar-usuarios";
    }
}
