package br.senac.ecommerce.tapeteyoga.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senac.ecommerce.tapeteyoga.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
    
}
