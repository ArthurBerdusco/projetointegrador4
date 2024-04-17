package br.senac.ecommerce.tapeteyoga.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.senac.ecommerce.tapeteyoga.model.Client;
import br.senac.ecommerce.tapeteyoga.repository.ClientRepository;

@Service
public class ClientService implements UserDetailsService {

    @Autowired
    private ClientRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Client> client = repository.findByEmail(email);
        if (client.isPresent()) {
            var clientObj = client.get();
            return User.builder()
                    .username(clientObj.getEmail())
                    .password(clientObj.getPassword())
                    .roles("Client")
                    .build();
        } else {
            throw new UsernameNotFoundException(email);
        }
    }
    public Client findByEmail(String email) {
        Optional<Client> clientOptional = repository.findByEmail(email);
        return clientOptional.orElseThrow(() -> new RuntimeException("Cliente não encontrado para o email: " + email));
    }

    public Client save(Client client) {
        return repository.save(client);
    }


}