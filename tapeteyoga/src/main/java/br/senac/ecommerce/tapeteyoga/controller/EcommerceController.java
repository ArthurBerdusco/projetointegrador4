package br.senac.ecommerce.tapeteyoga.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import br.senac.ecommerce.tapeteyoga.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class EcommerceController {

  @Autowired
  private UsuarioRepository repository;


  @GetMapping("/")
  public String index() {
    System.out.println("KKKKKKKKKKKKKKKKKKKKKK");
      return "index";
  }
  

}