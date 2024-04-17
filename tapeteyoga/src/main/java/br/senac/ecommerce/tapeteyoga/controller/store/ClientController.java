package br.senac.ecommerce.tapeteyoga.controller.store;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import br.senac.ecommerce.tapeteyoga.model.BillingAddressDTO;
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
    ClientService clientService;

    @PostMapping("")
    public String cadastro(@Valid ClientDTO client, BindingResult result, Model model) {
      
        if (result.hasErrors()) {
            System.out.println("KKKKKKKKKKKKKKKKKKKKKKK" +  result.getAllErrors());
            return "store/register";
        }

        Client entity = new Client();
        List<DeliveryAddress> deliveryAddresses = new ArrayList<>();

        entity.setFullName(client.getFullName());
        entity.setCpf(client.getCpf());
        entity.setEmail(client.getEmail());
        entity.setBirthDate(client.getBirthDate());
        entity.setGender(client.getGender());
        entity.setPassword(client.getPassword());

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

    @GetMapping("/editar")
    public ModelAndView alterar(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Client client = clientService.findByEmail(email);

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setFullName(client.getFullName());
        clientDTO.setBirthDate(client.getBirthDate());
        clientDTO.setGender(client.getGender());

        ModelAndView modelAndView = new ModelAndView("store/edit-client");
        modelAndView.addObject("clientDTO", clientDTO);
        return modelAndView;
    }
    @PostMapping("/editar")
    public String editClient(@Valid @ModelAttribute("clientDTO") ClientDTO clientDTO, BindingResult result){
        if(result.hasErrors()){
            return "store/edit-client";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emmail = auth.getName();
        Client client = clientService.findByEmail(emmail);

        client.setFullName(clientDTO.getFullName());
        client.setBirthDate(clientDTO.getBirthDate());
        client.setGender(clientDTO.getGender());

        clientService.save(client);

        return "redirect: /store/paginaPrincipal";
    }
  
    @GetMapping("/alterar-senha")
    public String telaAlterarSenhaCliente(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        
        String email = authentication.getName(); 
        Client client = clientService.findByEmail(email); 
        
        if (client == null) {
            throw new RuntimeException("Cliente não encontrado para o email: " + email);
        }
    
        client.setPassword("");
    
        client.setBillingAddress(null);
        client.setDeliveryAddresses(null);
    
        model.addAttribute("client", client);
    
        return "cliente/alterarSenhaCliente";
    }
    @PostMapping("/alterar-senha")
    public String alterarSenhaCliente(@ModelAttribute("client") Client client) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        
        String email = authentication.getName();
        Client existingClient = clientService.findByEmail(email); 
        
        if (existingClient == null) {
            throw new RuntimeException("Cliente não encontrado para o email: " + email);
        }
    
        existingClient.setPassword(client.getPassword());
        
        clientService.save(existingClient);
        
        return "redirect:/store/paginaPrincipal"; 
    }
    

}
