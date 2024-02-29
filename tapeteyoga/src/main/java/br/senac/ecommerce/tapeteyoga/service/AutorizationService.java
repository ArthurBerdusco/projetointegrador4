package br.senac.ecommerce.tapeteyoga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.senac.ecommerce.tapeteyoga.repository.UsuarioRepository;

public class AutorizationService implements UserDetailsService{


    @Autowired
    UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        return repository.findByEmail(username);

    }
    
}
