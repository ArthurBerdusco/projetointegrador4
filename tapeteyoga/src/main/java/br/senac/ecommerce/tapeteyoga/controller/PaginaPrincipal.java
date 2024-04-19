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
import br.senac.ecommerce.tapeteyoga.repository.ClientRepository;
import br.senac.ecommerce.tapeteyoga.service.ClientService;
import br.senac.ecommerce.tapeteyoga.service.ProdutoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("")
public class PaginaPrincipal {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)

    public String landingPage(Model model, HttpSession session) {
        if (session.getAttribute("UsuarioLogado") != null) {
            String emailCliente = (String) session.getAttribute("UsuarioLogado");
            Optional<Client> clienteOptional = clientRepository.findByEmail(emailCliente);
            if (clienteOptional.isPresent()) {
                Client cliente = clienteOptional.get();
                String nomeCliente = cliente.getFullName();
                model.addAttribute("nameClient", nomeCliente);
            }
        }
        var listaProdutos = produtoService.buscarProdutosAtivos();
        model.addAttribute("produtoPage", listaProdutos);
        return "store/index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("client", new Client());
        return "store/login";
    }

    @PostMapping("/login")
    public String processamentoLogin(@ModelAttribute("client") Client client, Model model, HttpSession session) {
        boolean valido = clientService.validarLogin(client.getEmail(), client.getPassword());
        if (valido) {
            session.setAttribute("UsuarioLogado", client.getEmail());
            return "redirect:/";
            /* Redirecione a página desejada */
        } else {
            model.addAttribute("Error", "Usuário e/ou senha inválidos");

            return "store/login";
        }
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
