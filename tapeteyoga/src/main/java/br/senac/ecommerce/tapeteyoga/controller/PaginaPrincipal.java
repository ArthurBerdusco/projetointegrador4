package br.senac.ecommerce.tapeteyoga.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.senac.ecommerce.tapeteyoga.service.ProdutoService;

@Controller
@RequestMapping("")
public class PaginaPrincipal {

    @Autowired
    private ProdutoService produtoService;
    
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String landingPage(Model model){
        var listaProdutos = produtoService.buscarProdutosAtivos();
        model.addAttribute("produtoPage",  listaProdutos);
        
        return "landingPage";
    }
}
