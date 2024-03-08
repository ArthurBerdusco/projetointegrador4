package br.senac.ecommerce.tapeteyoga.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import br.senac.ecommerce.tapeteyoga.model.Usuario;
import br.senac.ecommerce.tapeteyoga.repository.UsuarioRepository;

@Controller
public class ContentController {

  @Autowired
  UsuarioRepository repository;

  @GetMapping("/backoffice/home")
  public String handleBackofficeHome(Model model, Authentication authentication) {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    String username = userDetails.getUsername();

    Usuario usuario = repository.findByUsername(username).get();
    
    model.addAttribute("usuario", usuario);
    
    return "/backoffice/home_backoffice";
}

  @GetMapping("/backoffice/cadastrar-usuario")
  public String handleBackofficeCadastro(Usuario usuario, Model model, Authentication authentication) {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();

        Usuario userAutenticado = repository.findByUsername(username).get();

        model.addAttribute("usuarioAutenticado", userAutenticado);
    return "/backoffice/form_usuario";

  }

  @GetMapping("/backoffice/listar-produtos")
  public String handleBackofficeProdutos(Authentication authentication, Model model) {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    String username = userDetails.getUsername();

    Usuario usuario = repository.findByUsername(username).get();

    model.addAttribute("usuario", usuario);
    return "/backoffice/produtos_backoffice";
  }

  @GetMapping("/login/backoffice")
  public String handleBackofficeLogin(Usuario usuario) {
    return "backoffice/backoffice_login";
  }



}