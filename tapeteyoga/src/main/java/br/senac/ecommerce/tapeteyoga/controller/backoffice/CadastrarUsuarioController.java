package br.senac.ecommerce.tapeteyoga.controller.backoffice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import br.senac.ecommerce.tapeteyoga.model.Usuario;
import br.senac.ecommerce.tapeteyoga.repository.UsuarioRepository;

@RestController
public class CadastrarUsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/backoffice/cadastrar-usuario")
    public ModelAndView cadastrarUsuario(Usuario usuario) {
        ModelAndView modelAndView = new ModelAndView();

        boolean userExists = repository.existsByUsernameOrCpf(usuario.getUsername(), usuario.getCpf());

        if (userExists) {
            modelAndView.setViewName("redirect:/backoffice/cadastrar-usuario?error=Email ou CPF já cadastrado, tente novamente");
        } else {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            usuario.setActive(true);

            repository.save(usuario);

            modelAndView.setViewName("redirect:/backoffice/listar-usuarios");

        }

        return modelAndView;
    }

    @GetMapping("/setup")
    public RedirectView cadastrarUsuario(Model model) {

        Usuario usuario = new Usuario();

        usuario.setUsername("admin@root.com");
        usuario.setPassword(passwordEncoder.encode("admin"));
        usuario.setRole("ADMIN");
        usuario.setActive(true);

        boolean userExists = repository.existsByUsername(usuario.getUsername());

        if (userExists) {
            model.addAttribute("mensagem", "Erro, usuário admin já cadastrado");
            return new RedirectView("/backoffice/backoffice_login");
        }

        repository.save(usuario);

        return new RedirectView("/backoffice/backoffice_login");
    }

}
