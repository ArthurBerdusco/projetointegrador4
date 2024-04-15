package br.senac.ecommerce.tapeteyoga.controller.store;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.senac.ecommerce.tapeteyoga.model.BillingAddress;
import br.senac.ecommerce.tapeteyoga.model.Client;
import br.senac.ecommerce.tapeteyoga.model.DeliveryAddress;
import br.senac.ecommerce.tapeteyoga.repository.ClientRepository;

@Controller
@RequestMapping("cadastro")
public class ClietController {
    
    @Autowired
    ClientRepository clientRepository;


    @PostMapping("")
    public String cadastro(Client client){


        CRIAR DTOS PARA FUNCIOANR;

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
        billingAddress.setClient(client);

        for(DeliveryAddress addresses : client.getDeliveryAddresses()){
            DeliveryAddress deliveryAddress = new DeliveryAddress();
            deliveryAddress.setZipCode(addresses.getZipCode());
            deliveryAddress.setStreet(addresses.getStreet());
            deliveryAddress.setNumber(addresses.getNumber());
            deliveryAddress.setComplement(addresses.getComplement());
            deliveryAddress.setNeighborhood(addresses.getNeighborhood());
            deliveryAddress.setCity(addresses.getCity());
            deliveryAddress.setState(addresses.getState());
            deliveryAddress.setClient(client);
            deliveryAddresses.add(deliveryAddress);
        }

        entity.setBillingAddress(billingAddress);
        entity.setDeliveryAddresses(deliveryAddresses);

        clientRepository.save(entity);

        return "redirect:/login";
    }
    
}
