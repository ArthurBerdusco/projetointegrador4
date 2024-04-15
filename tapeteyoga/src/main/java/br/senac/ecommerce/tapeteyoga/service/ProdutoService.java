package br.senac.ecommerce.tapeteyoga.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.senac.ecommerce.tapeteyoga.model.Produto;
import br.senac.ecommerce.tapeteyoga.repository.ProdutoRepository;
import jakarta.transaction.Transactional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Transactional
    public Optional<Produto> buscarProdutoPorId(Long id) {
        return repository.findById(id);
    }
    public List<Produto> buscarProdutosAtivos(){
        return repository.findByIsActiveTrueOrderByIdDesc();
    }

    }


