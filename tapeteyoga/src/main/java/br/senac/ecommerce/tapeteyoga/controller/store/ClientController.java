package br.senac.ecommerce.tapeteyoga.controller.store;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.senac.ecommerce.tapeteyoga.model.BillingAddress;
import br.senac.ecommerce.tapeteyoga.model.Client;
import br.senac.ecommerce.tapeteyoga.model.ClientDTO;
import br.senac.ecommerce.tapeteyoga.model.DeliveryAddress;
import br.senac.ecommerce.tapeteyoga.model.DeliveryAddressDTO;
import br.senac.ecommerce.tapeteyoga.repository.ClientRepository;
import br.senac.ecommerce.tapeteyoga.service.ClientService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("cadastro")
public class ClientController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("")
    public String cadastro(@Valid ClientDTO client, BindingResult result) {

        System.out.println("\n\n\n\n" + "NÃO ERA PRA ENTRAR AQUI " + " \n\n\n\n");

        if (clientRepository.existsByCpf(client.getCpf())) {
            result.rejectValue("cpf", "error.cpf", "CPF já cadastrado");
        }

        if (clientRepository.existsByEmail(client.getEmail()) && !client.getEmail().trim().isBlank()) {
            result.rejectValue("email", "error.email", "Email já cadastrado");
        }

        if (result.hasErrors()) {
            return "store/register";
        }
    

        Client entity = new Client();
        List<DeliveryAddress> deliveryAddresses = new ArrayList<>();

        entity.setFullName(client.getFullName());
        entity.setCpf(client.getCpf());
        entity.setEmail(client.getEmail());
        entity.setBirthDate(client.getBirthDate());
        entity.setGender(client.getGender());
        entity.setPassword(passwordEncoder.encode(client.getPassword()));

        BillingAddress billingAddress = new BillingAddress();
        billingAddress.setZipCode(client.getBillingAddress().getZipCode());
        billingAddress.setStreet(client.getBillingAddress().getStreet());
        billingAddress.setNumber(client.getBillingAddress().getNumber());
        billingAddress.setComplement(client.getBillingAddress().getComplement());
        billingAddress.setNeighborhood(client.getBillingAddress().getNeighborhood());
        billingAddress.setCity(client.getBillingAddress().getCity());
        billingAddress.setState(client.getBillingAddress().getState());
        billingAddress.setClient(entity);

        for (DeliveryAddressDTO addresses : client.getDeliveryAddresses()) {
            DeliveryAddress deliveryAddress = new DeliveryAddress();
            deliveryAddress.setZipCode(addresses.getZipCode());
            deliveryAddress.setStreet(addresses.getStreet());
            deliveryAddress.setNumber(addresses.getNumber());
            deliveryAddress.setComplement(addresses.getComplement());
            deliveryAddress.setNeighborhood(addresses.getNeighborhood());
            deliveryAddress.setCity(addresses.getCity());
            deliveryAddress.setState(addresses.getState());
            deliveryAddress.setClient(entity);
            deliveryAddresses.add(deliveryAddress);
        }

        entity.setBillingAddress(billingAddress);
        entity.setDeliveryAddresses(deliveryAddresses);

        clientRepository.save(entity);

        return "redirect:/login";
    }

    @PostMapping("{id}")
    public String update(@PathVariable Long id, @Valid ClientDTO client, BindingResult result) {

        System.out.println("\n\n\n\n" + "CHEGUEI AQUI " + " \n\n\n\n");

        if (result.hasErrors()) {
            System.out.println("\n\n\n\n" +result.getAllErrors() + "\n\n\n\n");
            return "store/register"; // Retornar para a página de edição com mensagens de erro, se houver
        }

        // Recuperar o cliente existente do repositório
        Client existingClient = clientRepository.findById(id).orElseThrow();

        // Preencher o objeto Client com os dados atualizados do ClientDTO
        existingClient.setFullName(client.getFullName());
        existingClient.setCpf(client.getCpf());
        existingClient.setEmail(client.getEmail());
        existingClient.setBirthDate(client.getBirthDate());
        existingClient.setGender(client.getGender());
        existingClient.setPassword(passwordEncoder.encode(client.getPassword())); // Se necessário, atualize a senha

        clientRepository.save(existingClient);

        System.out.println("\n\n\n\n" + "SALVEI " + " \n\n\n\n");

        return "redirect:/login";
    }

    @GetMapping("{id}")
    public String getClientForm(@PathVariable Long id, Model model) {


        Client entity = clientRepository.findById(id).orElseThrow();
        System.out.println("\n\n\n\n" +  entity + " \n\n\n\n");
        model.addAttribute("clientDTO", entity);

        return "store/register";
    }

}
