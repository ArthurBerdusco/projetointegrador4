package br.senac.ecommerce.tapeteyoga.controller.backoffice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.senac.ecommerce.tapeteyoga.infra.security.TokenService;
import br.senac.ecommerce.tapeteyoga.model.LoginResponseDTO;
import br.senac.ecommerce.tapeteyoga.model.RegisterDTO;
import br.senac.ecommerce.tapeteyoga.model.Usuario;
import br.senac.ecommerce.tapeteyoga.model.UsuarioDTO;
import br.senac.ecommerce.tapeteyoga.repository.UsuarioRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthBackofficeController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UsuarioRepository repository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/backofficelogin")
    public ResponseEntity login(@RequestBody @Valid UsuarioDTO data) {
        var emailPassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = this.authenticationManager.authenticate(emailPassword);//ERRO
        
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());
        
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        if (this.repository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

        String encriptedSenha = new BCryptPasswordEncoder().encode(data.senha());
        Usuario novoUsuario = new Usuario(data.email(), encriptedSenha, data.grupo());

        this.repository.save(novoUsuario);

        return ResponseEntity.ok().build();
    }
}
