package br.senac.ecommerce.tapeteyoga.controller.backoffice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.senac.ecommerce.tapeteyoga.model.Usuario;
import br.senac.ecommerce.tapeteyoga.repository.UsuarioRepository;

@Controller
public class CadastrarUsuarioController {

    @Autowired
    UsuarioRepository repository;
    
    @PostMapping("/backoffice/cadastrar-usuario")
    public String cadastrarUsuario(@RequestBody Usuario usuario){
        repository.save(usuario);
        System.out.println("Usuario cadastrado com sucesso.");
        return "/backoffice/cadastrar-usuario";
    }

}
