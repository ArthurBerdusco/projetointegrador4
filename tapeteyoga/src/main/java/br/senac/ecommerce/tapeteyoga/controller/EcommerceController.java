package br.senac.ecommerce.tapeteyoga.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import br.senac.ecommerce.tapeteyoga.repository.UsuarioRepository;

@Controller
public class EcommerceController {

  @Autowired
  private UsuarioRepository repository;


}