package br.senac.ecommerce.tapeteyoga.controller.backoffice.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.senac.ecommerce.tapeteyoga.controller.backoffice.Utils;
import br.senac.ecommerce.tapeteyoga.model.Produto;
import br.senac.ecommerce.tapeteyoga.repository.ProdutoRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("backoffice")
public class ProdutoController {

    @Autowired
    private Utils utils;

    @Autowired
    private ProdutoRepository repository;


    @GetMapping("produtos")
    public String listaProdutos(Model model, Authentication authentication){
        model.addAttribute("usuarioAutenticado", utils.getUsuarioAutenticado(authentication));
        return "backoffice/produto/lista_produtos";
    }

    @GetMapping("produtos/cadastro")
    public String cadastrar(Produto produto, Model model, Authentication authentication) {
        model.addAttribute("usuarioAutenticado", utils.getUsuarioAutenticado(authentication));
        return "backoffice/produto/form_produto";
    }

    @PostMapping("produto/cadastra")
    public String cadastra(@Valid Produto produto, BindingResult result){
        if(result.hasErrors()){
            return  "backoffice/produto/form_produto";
        }
        repository.save(produto);
        return "redirect:/backoffice/produtos";
    }

    
}
