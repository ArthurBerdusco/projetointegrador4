package br.senac.ecommerce.tapeteyoga.controller.backoffice;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import br.senac.ecommerce.tapeteyoga.model.Usuario;
import br.senac.ecommerce.tapeteyoga.repository.UsuarioRepository;

@Controller
public class EditarUsuarioController {


    @Autowired
    UsuarioRepository repository;


    @GetMapping("/backoffice/editar-usuario")
    public String listarUsuario() {
        return "backoffice/editar-usuario";
    }

    
    @PostMapping("/backoffice/desabilitar-usuario/{id}")
    public String desabilitarUsuario(@PathVariable Long id, Model model){
        Optional<Usuario> optionalUsuario = repository.findById(id);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            usuario.setActive(false);
            repository.save(usuario);
            model.addAttribute("message", "Usuário desabilitado com sucesso!");
        } else {
            model.addAttribute("message", "Usuário não encontrado para desabilitar.");
        }
        return "redirect:/backoffice/listar-usuarios";
    }

    @PostMapping("/backoffice/habilitar-usuario/{id}")
    public String habilitarUsuario(@PathVariable Long id, Model model){

        Optional<Usuario> optionalUsuario = repository.findById(id);

        if (optionalUsuario.isPresent()) {
            
            Usuario usuario = optionalUsuario.get();

            usuario.setActive(true);


            repository.save(usuario);

            model.addAttribute("message", "Usuário habilitado com sucesso!");
        } else {
            model.addAttribute("message", "Usuário não encontrado para habilitar.");
        }

        return "redirect:/backoffice/listar-usuarios";

    }



    //PARADIGMA CHAMADO: REST  ---> CRUD


    
    //GET -  OBTER UMA INFORMAÇÃO
    //POST - ENVIAR UMA INFORMAÇÃO
    //PUT - EDITAR 
    //DELETE - DELETAR



}
