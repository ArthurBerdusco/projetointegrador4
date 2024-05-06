package br.senac.ecommerce.tapeteyoga.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senac.ecommerce.tapeteyoga.model.DeliveryAddress;


public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {
  
    DeliveryAddress findByIsDefaultTrueAndClientId(Long clientId);
    
}
