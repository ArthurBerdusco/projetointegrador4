package br.senac.ecommerce.tapeteyoga.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import br.senac.ecommerce.tapeteyoga.model.ImagemProduto;

public interface ImagemProdutoRepository extends JpaRepository<ImagemProduto, Long>{

    
    ImagemProduto findByNomeArquivoContaining(String nomeArquivo);
    
}