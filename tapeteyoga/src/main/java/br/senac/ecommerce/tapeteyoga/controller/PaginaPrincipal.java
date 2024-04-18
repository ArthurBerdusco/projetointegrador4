package br.senac.ecommerce.tapeteyoga.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.senac.ecommerce.tapeteyoga.model.Client;
import br.senac.ecommerce.tapeteyoga.model.ClientDTO;
import br.senac.ecommerce.tapeteyoga.model.Produto;
import br.senac.ecommerce.tapeteyoga.service.ClientService;
import br.senac.ecommerce.tapeteyoga.service.ProdutoService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("")
public class PaginaPrincipal {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ClientService clientService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String landingPage(Model model) {
        var listaProdutos = produtoService.buscarProdutosAtivos();
        model.addAttribute("produtoPage", listaProdutos);
        return "store/index";
    }

    @GetMapping("/login")
    public String login() {
        return "store/login";
    }

    @GetMapping("/cadastro")
    public String register(ClientDTO client) {
        client.getDeliveryAddresses().get(0).setDefault(true);
        return "store/register";
    }

    @GetMapping("/produto")
    public String obterporId(@RequestParam(name = "id", required = false) Long id, Model model) {

        Optional<Produto> produtoVisualizar = produtoService.buscarProdutoPorId(id);

        if (produtoVisualizar.isPresent()) {
            Produto produto = produtoVisualizar.get();
            model.addAttribute("produto", produto);
            return "store/product";
        } else {
            return "index";
        }
    }

}
