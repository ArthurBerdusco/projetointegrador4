package br.senac.ecommerce.tapeteyoga.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senac.ecommerce.tapeteyoga.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long>  {

    public boolean existsByCpf(String cpf);
    public boolean existsByEmail(String email);
    
}
