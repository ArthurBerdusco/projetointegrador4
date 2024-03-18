package br.senac.ecommerce.tapeteyoga.controller.backoffice.produto;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senac.ecommerce.tapeteyoga.controller.backoffice.Utils;
import br.senac.ecommerce.tapeteyoga.model.Produto;
import br.senac.ecommerce.tapeteyoga.repository.ProdutoRepository;
import br.senac.ecommerce.tapeteyoga.service.ProdutoService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("backoffice")
public class ProdutoController {

    @Autowired
    private Utils utils;

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("produtos")
    public String listarProdutos(Model model, Authentication authentication) {

        // Obtém a lista de usuários cadastrados no backoffice.
        List<Produto> produtos = repository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        // Adiciona a lista de usuários ao modelo para exibição na página.
        model.addAttribute("produtos", produtos);
        model.addAttribute("usuarioAutenticado", utils.getUsuarioAutenticado(authentication));

        // Retorna o nome da página de listagem de usuários.
        return "backoffice/produto/lista_produtos";

    }
    @GetMapping("/produto")
    public String obterporId(@RequestParam(name = "id", required = false) Long id, Model model,
            RedirectAttributes redirect) {
                

        Optional<Produto> produtoVisualizar = produtoService.buscarProdutoPorId(id);

        if (produtoVisualizar.isPresent()) {
            Produto produto = produtoVisualizar.get();
            model.addAttribute("produto", produto);
            return "/backoffice/produto/visualizar";
        } else {
            return "redirect:/produto/lista_produto";
        }
    }
    

    @GetMapping("buscar")
    public String procurar(Model model, @RequestParam(name = "name", required = false) String name,
            Authentication authentication) {

        List<Produto> produtos = repository.findByNameContainingIgnoreCaseOrderByIdDesc(name);

        model.addAttribute("produtos", produtos);
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
