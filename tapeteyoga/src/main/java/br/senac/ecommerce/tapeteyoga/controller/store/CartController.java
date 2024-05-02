package br.senac.ecommerce.tapeteyoga.controller.store;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.senac.ecommerce.tapeteyoga.model.Carrinho;
import br.senac.ecommerce.tapeteyoga.model.ImagemProduto;
import br.senac.ecommerce.tapeteyoga.model.ItemCarrinho;
import br.senac.ecommerce.tapeteyoga.model.Produto;
import br.senac.ecommerce.tapeteyoga.repository.ImagemProdutoRepository;
import br.senac.ecommerce.tapeteyoga.repository.ProdutoRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    ImagemProdutoRepository imgRepository;
    
    @GetMapping("/carrinho")
    public String seeCart(HttpSession session){
        return "store/cart";
    }

    @PostMapping("/carrinho")
    public String addInCart(HttpSession session, @RequestParam("produtoId") Long produtoId, @RequestParam("quantidade") int quantidade){

        boolean produtoJaAdicionado = false;

        Produto produto = produtoRepository.findById(produtoId).orElseThrow();

        ImagemProduto imgProduto = imgRepository.findByProdutoIdAndPrincipalTrue(produtoId);

        Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
        List<ItemCarrinho> itens = carrinho.getItens();

        if(itens == null){
            itens = new ArrayList<>();
        }

        for(ItemCarrinho item : itens){

            if(item.getProduto().getId().equals(produtoId)){
                item.setQuantidade(item.getQuantidade() + quantidade);
                item.setProduto(produto);
                item.setCarrinho(carrinho);
                item.setImagem(imgProduto.getNomeArquivo());
                produtoJaAdicionado=true;
            }
            
            
        }

        if(!produtoJaAdicionado){
            ItemCarrinho item = new ItemCarrinho();
            item.setCarrinho(carrinho);
            item.setProduto(produto);
            item.setQuantidade(quantidade);
            item.setImagem(imgProduto.getNomeArquivo());
            itens.add(item);
           
        }
       
        carrinho.setItens(itens);
        carrinho.atualizarTotal();
        session.setAttribute("carrinho", carrinho);

        return "redirect:/produto?id="+produtoId.toString();
    }

    @PostMapping("/item")
    public String incrementarProduto(HttpSession session, @RequestParam("itemId") Long itemId){
     
        Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");

        List<ItemCarrinho> itens = carrinho.getItens();

        for(ItemCarrinho item : itens){
            if(itemId == item.getProduto().getId()){
                item.setQuantidade(item.getQuantidade() + 1);
            }
        }
        

        System.out.println("\n\n\n" + "ANTES DE INCREMENTAR" + carrinho.getItens() + "\n\n\n");
        carrinho.setItens(itens);

        carrinho.atualizarTotal();
        System.out.println("\n\n\n" + "DEPOIS DE INCREMENTAR" + carrinho.getItens() + "\n\n\n");
        session.setAttribute("carrinho", carrinho);

        return "redirect:/carrinho";
        
    }

    @PostMapping("/itemreduzir")
    public String decrementarProduto(HttpSession session, @RequestParam("itemId") Long itemId){
        
        Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");

        List<ItemCarrinho> itens = carrinho.getItens();

        for(ItemCarrinho item : itens){
            if(itemId == item.getProduto().getId()){
                item.setQuantidade(item.getQuantidade() - 1);
            }

        }

        carrinho.setItens(itens);
        carrinho.atualizarTotal();
        session.setAttribute("carrinho", carrinho);

        return "redirect:/carrinho";
        
    }

    @PostMapping("/remover")
    public String removerDoCarrinho(HttpSession session, @RequestParam("itemId") Long itemId) {
    
        Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
    
        List<ItemCarrinho> itens = carrinho.getItens();
    
        // Itera sobre os itens do carrinho
        Iterator<ItemCarrinho> iterator = itens.iterator();
        while (iterator.hasNext()) {
            ItemCarrinho item = iterator.next();
            if (itemId.equals(item.getProduto().getId())) {
                // Remove o item do carrinho
                iterator.remove();
                // Atualiza o subtotal do carrinho
                
                carrinho.removerValor(item.getTotal());
                break; // Se encontrar o item, não é necessário continuar iterando
            }

            
        }

    
        session.setAttribute("carrinho", carrinho);
    
        return "redirect:/carrinho";
    }
    
    


}
