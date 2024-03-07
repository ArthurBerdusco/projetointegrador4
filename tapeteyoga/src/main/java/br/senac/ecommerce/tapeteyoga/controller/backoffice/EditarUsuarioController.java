package br.senac.ecommerce.tapeteyoga.controller.backoffice;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.senac.ecommerce.tapeteyoga.model.Usuario;
import br.senac.ecommerce.tapeteyoga.model.Usuario.Existing;
import br.senac.ecommerce.tapeteyoga.repository.UsuarioRepository;

@Controller
@RequestMapping("backoffice")
public class EditarUsuarioController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UsuarioRepository repository;

    @GetMapping("editar-usuario/{id}")
    public String handleBackofficeGetUsuario(@PathVariable Long id, Model model) {

        Optional<Usuario> usuario = repository.findById(id);

        if (usuario.isPresent()) {
            model.addAttribute("usuario", usuario.get());
        }

        return "backoffice/form_usuario";
    }

    @PostMapping("editar-usuario/{id}")
    public String handleBackofficeEditar(@PathVariable Long id, @Validated({ Existing.class }) Usuario usuario,
            BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "backoffice/form_usuario";
        }

        // Verificar se o CPF já está cadastrado para outro usuário
        if (repository.existsByCpfAndIdNot(usuario.getCpf(), id)) {
            redirectAttributes.addFlashAttribute("error", "CPF já cadastrado para outro usuário. Tente novamente.");
            return "redirect:/backoffice/editar-usuario/" + id;
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        repository.save(usuario);

        return "redirect:/backoffice/listar-usuarios";

    }

    @PostMapping("desabilitar-usuario/{id}")
    public String desabilitarUsuario(@PathVariable Long id, Model model) {

        Optional<Usuario> optionalUsuario = repository.findById(id);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            usuario.setActive(false);
            repository.save(usuario);
            model.addAttribute("message", "Usuário desabilitado com sucesso!");
        } else {
            model.addAttribute("message", "Usuário não encontrado para desabilitar.");
        }
        return "redirect:/backoffice/listar-usuarios";
    }

    @PostMapping("habilitar-usuario/{id}")
    public String habilitarUsuario(@PathVariable Long id, Model model) {

        Optional<Usuario> optionalUsuario = repository.findById(id);

        if (optionalUsuario.isPresent()) {

            Usuario usuario = optionalUsuario.get();

            usuario.setActive(true);

            repository.save(usuario);

            model.addAttribute("message", "Usuário habilitado com sucesso!");
        } else {
            model.addAttribute("message", "Usuário não encontrado para habilitar.");
        }

        return "redirect:/backoffice/listar-usuarios";

    }

}
