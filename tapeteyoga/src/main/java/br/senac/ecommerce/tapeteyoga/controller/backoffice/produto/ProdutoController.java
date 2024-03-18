package br.senac.ecommerce.tapeteyoga.controller.backoffice.produto;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String listarProdutos(Model model, Authentication authentication,
            @RequestParam(name = "page", defaultValue = "0") int page) {

        // Define o tamanho da página (quantidade de produtos por página)
        int pageSize = 10;

        // Obtém a lista de usuários cadastrados no backoffice.
        Page<Produto> produtosPage = repository
                .findAll(PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id")));

        // Adiciona a lista de usuários ao modelo para exibição na página.
        model.addAttribute("produtos", produtosPage.getContent());
        model.addAttribute("usuarioAutenticado", utils.getUsuarioAutenticado(authentication));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", produtosPage.getTotalPages());

        // Retorna o nome da página de listagem de usuários.
        return "backoffice/produto/lista_produtos";

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
    public String cadastra(@Valid Produto produto, BindingResult result) {
        if (result.hasErrors()) {
            return "backoffice/produto/form_produto";
        }
        repository.save(produto);
        return "redirect:/backoffice/produtos";
    }

    @GetMapping("produtos/{id}/editar")
    public String editarProduto(@PathVariable Long id, Model model) {
        // Busca o produto no banco de dados pelo ID
        Optional<Produto> produtoOptional = repository.findById(id);

        // Verifica se o produto foi encontrado
        if (produtoOptional.isPresent()) {
            // Produto encontrado, adiciona-o ao modelo
            Produto produto = produtoOptional.get();
            model.addAttribute("produto", produto);
            // Retorna o nome da página de edição
            return "backoffice/produto/editar_produto";
        } else {
            // Produto não encontrado, redireciona para página de erro ou retorna uma
            // mensagem de erro
            return "redirect:/backoffice/produtos?error=Produto não encontrado";
        }
    }

    @GetMapping("produtos/{id}/inativar")
    public String inativarProduto(@PathVariable Long id) {
        // Busca o produto no banco de dados pelo ID
        Optional<Produto> produtoOptional = repository.findById(id);

        // Verifica se o produto foi encontrado
        if (produtoOptional.isPresent()) {
            // Produto encontrado, obtém o produto
            Produto produto = produtoOptional.get();

            // Realiza a lógica para inativar o produto (por exemplo, alterar o status)
            produto.setActive(false);

            // Salva as alterações no banco de dados
            repository.save(produto);

            // Redireciona para a página de listagem de produtos
            return "redirect:/backoffice/produtos";
        } else {

            return "redirect:/backoffice/produtos?error=Produto não encontrado";
        }
    }

    @GetMapping("produtos/{id}/reativar")
    public String reativarProduto(@PathVariable Long id) {
        // Busca o produto no banco de dados pelo ID
        Optional<Produto> produtoOptional = repository.findById(id);

        // Verifica se o produto foi encontrado
        if (produtoOptional.isPresent()) {
            // Produto encontrado, obtém o produto
            Produto produto = produtoOptional.get();

            // Realiza a lógica para reativar o produto (por exemplo, alterar o status)
            produto.setActive(true);

            // Salva as alterações no banco de dados
            repository.save(produto);

            // Redireciona para a página de listagem de produtos
            return "redirect:/backoffice/produtos";
        } else {

            return "redirect:/backoffice/produtos?error=Produto não encontrado";
        }
    }

    @GetMapping("produtos/{id}/visualizar")

    public String visualizarProduto(@PathVariable Long id, Model model) {
        // Busca o produto no banco de dados pelo ID
        Optional<Produto> produtoOptional = repository.findById(id);

        // Verifica se o produto foi encontrado
        if (produtoOptional.isPresent()) {
            // Produto encontrado, adiciona-o ao modelo
            Produto produto = produtoOptional.get();
            model.addAttribute("produto", produto);

            // Retorna o nome da página de visualização do produto
            return "backoffice/produto/visualizar_produto";
        } else {

            return "redirect:/backoffice/produtos?error=Produto não encontrado";
        }

    }
    

}
