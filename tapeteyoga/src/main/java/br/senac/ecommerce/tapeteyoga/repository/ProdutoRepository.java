package br.senac.ecommerce.tapeteyoga.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import br.senac.ecommerce.tapeteyoga.model.Produto;
import jakarta.persistence.OrderBy;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

    
    List<Produto> findByNameContainingIgnoreCaseOrderByIdDesc(String name);
    
}
