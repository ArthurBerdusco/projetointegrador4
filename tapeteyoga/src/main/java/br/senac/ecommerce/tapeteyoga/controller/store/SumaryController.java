package br.senac.ecommerce.tapeteyoga.controller.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.senac.ecommerce.tapeteyoga.model.Carrinho;
import br.senac.ecommerce.tapeteyoga.model.Pedido;
import br.senac.ecommerce.tapeteyoga.repository.PedidoRepository;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/resumo")
public class SumaryController {

    @Autowired
    PedidoRepository pedidoRepository;

    @GetMapping
    public String resumoPedido(HttpSession session) {
        return "store/summary";
    }

    @PostMapping("/concluir-pagamento")
    public String concluirPagamento(HttpSession session) {

        // Obtém o pedido da sessão
        Pedido pedido = (Pedido) session.getAttribute("pedido");

        // Salva o pedido no banco de dados
        pedidoRepository.save(pedido);

        // Limpa o pedido da sessão
        session.removeAttribute("pedido");
        session.setAttribute("carrinho", new Carrinho());

        // Redireciona para a página de confirmação de pagamento
        return "redirect:/pedidos";
    }

}
