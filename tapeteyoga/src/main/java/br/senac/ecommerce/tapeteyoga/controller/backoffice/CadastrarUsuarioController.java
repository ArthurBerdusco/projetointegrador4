package br.senac.ecommerce.tapeteyoga.controller.backoffice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.senac.ecommerce.tapeteyoga.model.Usuario;
import br.senac.ecommerce.tapeteyoga.repository.UsuarioRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CadastrarUsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;


    @GetMapping("/backoffice/cadastrar-usuario")
    public String usuario(Usuario usuario, Model model) {
        return "/backoffice/cadastrar-usuario";
    }

    @PostMapping("/backoffice/cadastrar-usuario")
    public String cadastrarUsuario(Usuario usuario, @RequestParam("confirmaSenha") String confirmaSenha, RedirectAttributes attr) {

        String grupo = "administrador".equals(usuario.getGrupo()) ? "administrador" : "estoquista";
        usuario.setGrupo(grupo);

        if (!usuario.getSenha().equals(confirmaSenha)) {
            attr.addFlashAttribute("senhaErro", "As senhas não são iguais.");
            return "redirect:/backoffice/cadastrar-usuario";
        }



       usuarioRepository.save(usuario);
        return "redirect:/backoffice/cadastrar-usuario";
    }

}
