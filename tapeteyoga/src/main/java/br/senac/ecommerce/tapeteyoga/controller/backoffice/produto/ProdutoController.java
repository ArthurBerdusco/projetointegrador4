package br.senac.ecommerce.tapeteyoga.controller.backoffice.produto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;

import br.senac.ecommerce.tapeteyoga.controller.backoffice.Utils;
import br.senac.ecommerce.tapeteyoga.model.ImagemProduto;
import br.senac.ecommerce.tapeteyoga.model.Produto;
import br.senac.ecommerce.tapeteyoga.repository.ImagemProdutoRepository;
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

    @Autowired
    private ImagemProdutoRepository imgRepository;

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

    @GetMapping("/produto")
    public String obterporId(@RequestParam(name = "id", required = false) Long id, Model model,
            Authentication authentication) {
        model.addAttribute("usuarioAutenticado", utils.getUsuarioAutenticado(authentication));
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

    @GetMapping("produtos/{id}")
    public String handleBackofficeGetProduto(@PathVariable Long id, Model model, Authentication authentication) {
        Produto produto = repository.findById(id).orElseThrow();
        model.addAttribute("produto", produto);
        model.addAttribute("imagens", produto.getImagens());
        model.addAttribute("usuarioAutenticado", utils.getUsuarioAutenticado(authentication));
        return "backoffice/produto/form_produto";
    }

    @GetMapping("produtos/cadastro")
    public String cadastrar(Produto produto, Model model, Authentication authentication) {
        model.addAttribute("usuarioAutenticado", utils.getUsuarioAutenticado(authentication));
        return "backoffice/produto/form_produto";
    }

    @PostMapping("produto/cadastra")
    public String cadastra(@RequestParam(name = "arquivo", required = false) MultipartFile[] files,
            @Valid Produto produto,
            BindingResult result) {

        if (result.hasErrors()) {
        }

        List<ImagemProduto> imagensEntities = new ArrayList<>();

        if (files != null) {
            for (int i = 0; i < files.length; i++) {

                MultipartFile file = files[i];

                if (file.isEmpty()) {
                    continue; // Ignora arquivos vazios
                }

                String nomeOriginal = file.getOriginalFilename();
                String extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
                String nomeArquivo = (repository.getNextProductId()) + "-" + (i + 1) + extensao;

                try {
                    String uploadDir = "src/main/resources/static/img/produtos/";
                    Path uploadPath = Paths.get(uploadDir);
                    if (!Files.exists(uploadPath)) {
                        Files.createDirectories(uploadPath);
                    }

                    Path destino = uploadPath.resolve(nomeArquivo);
                    Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

                    ImagemProduto imgEntity = new ImagemProduto();
                    imgEntity.setNomeArquivo(nomeArquivo);
                    imgEntity.setOrdenacao(produto.getImagens().get(i).getOrdenacao());
                    imgEntity.setPrincipal(produto.getImagens().get(i).isPrincipal());
                    imgEntity.setProduto(produto);
                    imagensEntities.add(imgEntity);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        produto.setImagens(imagensEntities);
        repository.save(produto);
        return "redirect:/backoffice/produtos";
    }

    @PostMapping("produto/edita")
    public String edita(@RequestParam("arquivo") MultipartFile[] files, @Valid Produto produto, BindingResult result)
            throws Exception {

        Path uploadPath;
        List<ImagemProduto> imagens = imgRepository.findByProdutoId(produto.getId());

        if (result.hasErrors()) {
            // Handle validation errors
        }

        try {
            String uploadDir = "src/main/resources/static/img/produtos/";
            uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "NÃO FOI POSSIVEL CRIAR DIRETORIO";
        }

        for (int i = 0; i < files.length; i++) {

            MultipartFile file = files[i];

            if (i < imagens.size()) {
                ImagemProduto imagemProd = imagens.get(i);

                // Edição apenas dos dados da imagem
                if (file.isEmpty() && imagemProd.getId() != null) {
                    imagemProd.setOrdenacao(produto.getImagens().get(i).getOrdenacao());
                    imagemProd.setPrincipal(produto.getImagens().get(i).isPrincipal());
                    produto.setImagens(imagens);
                    repository.save(produto);
                }
            }

            if (file.isEmpty()) {
                continue; // Ignore empty files
            }

            ImagemProduto alterarImagem = imgRepository.findByNomeArquivoContaining(produto.getId() + "-" + (i + 1));
            String nomeOriginal = file.getOriginalFilename();
            String extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
            String nomeArquivo = produto.getId() + "-" + (i + 1) + extensao;

            Path destino = uploadPath.resolve(nomeArquivo);

            // Alteração de imagem
            if (!file.getOriginalFilename().trim().equals("") && alterarImagem != null) {

                Files.write(destino, file.getBytes());
                alterarImagem.setNomeArquivo(nomeArquivo);
                alterarImagem.setOrdenacao(produto.getImagens().get(i).getOrdenacao());
                alterarImagem.setPrincipal(produto.getImagens().get(i).isPrincipal());
                produto.setImagens(imagens);
                repository.save(produto);
            }
        }

        return "redirect:/backoffice/produtos";
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

}
