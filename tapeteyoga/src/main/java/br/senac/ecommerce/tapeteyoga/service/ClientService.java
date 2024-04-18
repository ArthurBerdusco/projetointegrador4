package br.senac.ecommerce.tapeteyoga.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.senac.ecommerce.tapeteyoga.model.Client;
import br.senac.ecommerce.tapeteyoga.repository.ClientRepository;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

 
    public Client findByEmail(String email) {
        Optional<Client> clientOptional = repository.findByEmail(email);
        return clientOptional.orElseThrow(() -> new RuntimeException("Cliente não encontrado para o email: " + email));
    }

    public Client save(Client client) {
        return repository.save(client);
    }
    public boolean validarLogin(String email, String password) {
        Client cliente = findByEmail(email);
        if(cliente != null){
            return passwordEncoder.matches(password, cliente.getPassword());
        }
        return false;
    }

}