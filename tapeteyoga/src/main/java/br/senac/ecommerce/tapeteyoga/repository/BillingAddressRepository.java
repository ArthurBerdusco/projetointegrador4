package br.senac.ecommerce.tapeteyoga.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import br.senac.ecommerce.tapeteyoga.model.BillingAddress;


public interface BillingAddressRepository extends JpaRepository<BillingAddress, Long> {
  
}
