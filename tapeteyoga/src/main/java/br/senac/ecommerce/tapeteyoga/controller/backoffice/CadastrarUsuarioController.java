package br.senac.ecommerce.tapeteyoga.controller.backoffice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.senac.ecommerce.tapeteyoga.model.Usuario;
import br.senac.ecommerce.tapeteyoga.repository.UsuarioRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("backoffice")
public class CadastrarUsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("cadastrar-usuario")
    public String cadastrarUsuario(@Valid Usuario usuario, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "backoffice/form_usuario";
        }

        if (repository.existsByUsernameOrCpf(usuario.getUsername(), usuario.getCpf())) {
            redirectAttributes.addFlashAttribute("error", "Email ou CPF já cadastrado, tente novamente");
            return "backoffice/form_usuario";
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setActive(true);

        repository.save(usuario);

        return "redirect:/backoffice/listar-usuarios";
    }

}
