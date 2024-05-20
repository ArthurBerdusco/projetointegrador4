package br.senac.ecommerce.tapeteyoga.controller.store;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.senac.ecommerce.tapeteyoga.model.Carrinho;
import br.senac.ecommerce.tapeteyoga.model.Client;
import br.senac.ecommerce.tapeteyoga.model.DeliveryAddress;
import br.senac.ecommerce.tapeteyoga.model.Frete;
import br.senac.ecommerce.tapeteyoga.model.ItemCarrinho;
import br.senac.ecommerce.tapeteyoga.model.ItemPedido;
import br.senac.ecommerce.tapeteyoga.model.Pedido;
import br.senac.ecommerce.tapeteyoga.repository.ClientRepository;
import br.senac.ecommerce.tapeteyoga.repository.DeliveryAddressRepository;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("pagamento")
public class CheckoutController {

    @Autowired
     List<Frete> tiposDeFrete;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    DeliveryAddressRepository deliveryAddressRepository;

    @GetMapping
    public String pagamento(HttpSession session) {

        if (session.getAttribute("UsuarioLogado") == null) {
            return "redirect:/login";
        }


        Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
        if (carrinho == null) {
            carrinho = new Carrinho();
            session.setAttribute("carrinho", carrinho);

        }

        if (session.getAttribute("UsuarioLogado") != null) {
            String emailCliente = (String) session.getAttribute("UsuarioLogado");
            Optional<Client> clienteOptional = clientRepository.findByEmail(emailCliente);
            if (clienteOptional.isPresent()) {
                Client cliente = clienteOptional.get();
                session.setAttribute("client", cliente);
                session.setAttribute("frete", tiposDeFrete);
                session.setAttribute("entrega", cliente.getMainDeliveryAddress());

            }
        }

        return "store/checkout";
    }

    @PostMapping("/concluir-pedido")
public String concluirPedido(HttpSession session, @RequestParam("idEnderecoPrincipal") String idEndereco,
                             @RequestParam("valorFrete") String frete, @RequestParam("formaPagamento") String formaPagamento) {

    DeliveryAddress endereco = deliveryAddressRepository.findById(Long.valueOf(idEndereco)).orElseThrow();
    
    Pedido pedido = new Pedido();
    List<ItemPedido> itensPedido = new ArrayList<>();

    Client client = clientRepository.findById((Long) session.getAttribute("clientId")).orElseThrow();

    Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");

    BigDecimal valorFrete = new BigDecimal(frete);
    BigDecimal totalPedido = carrinho.getTotal().add(valorFrete);


    System.out.println("\n\n\n" + carrinho.getTotal() + "\n\n\n");
    System.out.println("\n\n\n" + valorFrete + "\n\n\n");
    System.out.println("\n\n\n" + totalPedido + "\n\n\n" + " --> TOTAL PEDIDO ANTES");

    if(formaPagamento.equals("Boleto Bancário")) {
        totalPedido = totalPedido.multiply(new BigDecimal(0.95)); // Ajusta o total para pagamento via boleto
    }

    System.out.println("\n\n\n" + totalPedido + "\n\n\n" + " --> TOTAL PEDIDO DEPOIS");

    pedido.setTotal(totalPedido);
    pedido.setCliente(client);
    pedido.setStatus("Aguardando pagamento");
    pedido.setEnderecoEntrega(endereco);
    pedido.setValorFrete(valorFrete);
    pedido.setFormaPagamento(formaPagamento);
    
    pedido.setSerial(ThreadLocalRandom.current().nextLong(1000000000L, 10000000000L));

    // Obtém a data e hora atual
    LocalDateTime dataAtual = LocalDateTime.now();

    // Formata a data e hora no padrão 'dd/MM/yyyy - hh:mm'
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
    String dataHoraFormatada = dataAtual.format(formatter);

    // Define a data formatada no pedido
    pedido.setDataPedido(dataHoraFormatada);

    for (ItemCarrinho itemCarrinho : carrinho.getItens()) {
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setProduto(itemCarrinho.getProduto());
        itemPedido.setPedido(pedido);
        itemPedido.setTotal(itemCarrinho.getTotal());
        itemPedido.setQuantidade(itemCarrinho.getQuantidade());
        itensPedido.add(itemPedido);
    }

    pedido.setItens(itensPedido);

    session.setAttribute("pedido", pedido); // Armazena o pedido na sessão
    //session.removeAttribute("carrinho"); // Remove o carrinho da sessão

    return "redirect:/resumo"; // Redireciona para a página de resumo do pedido
}


    @PostMapping("/addEndereco")
    public String adicionarEndereco(
            @RequestParam("novoCEP") String cep,
            @RequestParam("novoLogradouro") String logradouro,
            @RequestParam("novoNumero") String numero,
            @RequestParam("novoComplemento") String complemento,
            @RequestParam("novoBairro") String bairro,
            @RequestParam("novoCidade") String cidade,
            @RequestParam("novoUF") String uf,
            HttpSession session
            ) {
                Client client = (Client) session.getAttribute("client");

                DeliveryAddress endereco = new DeliveryAddress();
                endereco.setClient(client);
                endereco.setZipCode(cep);
                endereco.setStreet(logradouro);
                endereco.setNumber(numero);
                endereco.setComplement(complemento);
                endereco.setNeighborhood(bairro);
                endereco.setCity(cidade);
                endereco.setState(uf);
                endereco.setActive(true);
                endereco.setDefault(false);

                deliveryAddressRepository.save(endereco);

        return "redirect:/pagamento";
    }

}
