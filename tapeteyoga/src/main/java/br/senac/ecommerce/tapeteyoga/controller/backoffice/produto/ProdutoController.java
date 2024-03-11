package br.senac.ecommerce.tapeteyoga.controller.backoffice.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.senac.ecommerce.tapeteyoga.controller.backoffice.Utils;
import br.senac.ecommerce.tapeteyoga.model.Usuario;

@Controller
@RequestMapping("backoffice")
public class ProdutoController {

    @Autowired
    private Utils utils;


    @GetMapping("produtos")
    public String listaProdutos(Model model, Authentication authentication){
        model.addAttribute("usuarioAutenticado", utils.getUsuarioAutenticado(authentication));
        return "backoffice/produto/lista_produtos";
    }

    @GetMapping("produtos/cadastro")
    public String cadastrar(Usuario usuario, Model model, Authentication authentication) {
        model.addAttribute("usuarioAutenticado", utils.getUsuarioAutenticado(authentication));
        return "backoffice/produto/form_produto";
    }
    
}
