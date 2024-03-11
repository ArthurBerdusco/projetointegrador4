package br.senac.ecommerce.tapeteyoga.controller.backoffice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import br.senac.ecommerce.tapeteyoga.model.Usuario;
import br.senac.ecommerce.tapeteyoga.repository.UsuarioRepository;

@Component
public class Utils {
    @Autowired
    UsuarioRepository repository;
    
    //Método auxiliar para pegar informação do usuario que está autenticado no sistema
    public Usuario getUsuarioAutenticado(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        return repository.findByEmail(username).orElse(null);
    }

}
