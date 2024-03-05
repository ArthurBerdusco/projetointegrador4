package br.senac.ecommerce.tapeteyoga.controller.backoffice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.senac.ecommerce.tapeteyoga.model.Usuario;
import br.senac.ecommerce.tapeteyoga.repository.UsuarioRepository;

import java.util.List;

@Controller
@RequestMapping("/administrador/usuarios")
public class ListarUsuarioController {
    @Autowired
    private UsuarioRepository repository;

    @ModelAttribute("filtro")
    public Usuario getFiltro() {
        return new Usuario(); 
    }
    
    @GetMapping("/backoffice/listar-usuarios")

    public String listarUsuario(Model model) {
        List<Usuario> usuarios = repository.findAll();
        
        if (usuarios.isEmpty()) {
            return null;
        }
      
        model.addAttribute("usuarios", usuarios);
        return "backoffice/listar-usuarios";
    }
    
}

