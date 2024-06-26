package br.senac.ecommerce.tapeteyoga.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

    public boolean validarLogin(String email, String password) {
        Optional<Client> clienteOptional = repository.findByEmail(email);
        if (clienteOptional.isPresent()) {
            Client cliente = clienteOptional.get();
            return passwordEncoder.matches(password, cliente.getPassword());
        }
        return false;
    }

}