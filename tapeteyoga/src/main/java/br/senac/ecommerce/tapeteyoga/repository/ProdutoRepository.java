package br.senac.ecommerce.tapeteyoga.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.senac.ecommerce.tapeteyoga.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByNameContainingIgnoreCaseOrderByIdDesc(String name);

    @Query(value = "SELECT (max(id)+1) FROM produto", nativeQuery = true)
    Long getNextProductId();

}
