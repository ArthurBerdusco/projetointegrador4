package br.senac.ecommerce.tapeteyoga.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import br.senac.ecommerce.tapeteyoga.model.Usuario;

@Controller
public class ContentController {

  @GetMapping("/backoffice/home")
  public String handleBackofficeHome(Usuario usuario) {
    return "/backoffice/home_backoffice";
  }

  @GetMapping("/backoffice/cadastrar-usuario")
  public String handleBackofficeCadastro(Usuario usuario) {
    return "/backoffice/cadastrar-usuario";

  }

  @GetMapping("/backoffice/listar-produtos")
  public String handleBackofficeProdutos(Usuario usuario) {
    return "/backoffice/produtos_backoffice";
  }

  @GetMapping("/login/backoffice")
  public String handleBackofficeLogin(Usuario usuario) {
    return "backoffice/backoffice_login";
  }

}