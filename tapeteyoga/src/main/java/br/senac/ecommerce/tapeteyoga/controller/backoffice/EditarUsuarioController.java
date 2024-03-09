package br.senac.ecommerce.tapeteyoga.controller.backoffice;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import br.senac.ecommerce.tapeteyoga.model.Usuario;
import br.senac.ecommerce.tapeteyoga.repository.UsuarioRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("backoffice")
public class EditarUsuarioController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UsuarioRepository repository;


    //Método auxiliar para pegar informação do usuario que está autenticado no sistema
    private Usuario getUsuarioAutenticado(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        return repository.findByUsername(username).orElse(null);
    }

    @GetMapping("editar-usuario/{id}")
    public String handleBackofficeGetUsuario(@PathVariable long id, Model model, Authentication authentication) {
        Usuario userAutenticado = getUsuarioAutenticado(authentication);
        Optional<Usuario> usuario = repository.findById(id);

        if (usuario.isPresent()) {
            model.addAttribute("usuario", usuario.get());
            model.addAttribute("usuarioAutenticado", userAutenticado);
        }

        return "backoffice/form_usuario";
    }

    @PostMapping("trocar-status-usuario/{id}")
    public String habilitaDesabilita(@PathVariable long id) {
        Usuario usuario = repository.findById(id).get();
        usuario.setActive(!usuario.isActive());
        repository.save(usuario);
        return "redirect:/backoffice/listar-usuarios";
    }

    // METODO ÚNICO PARA SALVAR OU EDITAR CADASTRO
    @PostMapping("salvar")
    public String salvar(@Valid Usuario usuario, BindingResult result, Authentication authentication, Model model) {

        Usuario userAutenticado = getUsuarioAutenticado(authentication);
        model.addAttribute("usuarioAutenticado", userAutenticado);
        model.addAttribute("usuario", usuario);

        // Valida o CPF
        if (!ValidaCPF.isCPF(usuario.getCpf())) {
            result.rejectValue("cpf", "error.cpf", "Cpf inválido");
        }

        if (usuario.getId() == null) {
            // Valida se já existe esse email ou cpf
            if (repository.existsByUsername(usuario.getUsername())) {
                result.rejectValue("username", "error.username", "Email já cadastrado");
            }

            if (repository.existsByCpf(usuario.getCpf())) {
                result.rejectValue("cpf", "error.cpf", "Cpf já cadastrado");
            }
        } else {
            // Valida se há outra usuário já cadastrado com esse CPF a não ser ele próprio
            if (repository.existsByCpfAndIdNot(usuario.getCpf(), usuario.getId())) {
                result.rejectValue("cpf", "error.cpf", "Cpf já cadastrado");
            }
        }

        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "backoffice/form_usuario";
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        repository.save(usuario);

        return "redirect:/backoffice/listar-usuarios";
    }

}
