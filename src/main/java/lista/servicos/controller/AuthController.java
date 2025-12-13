package lista.servicos.controller;

import jakarta.validation.Valid;
import lista.servicos.controller.dto.LoginRequest;
import lista.servicos.controller.dto.RegisterRequest;
import lista.servicos.controller.dto.RegisterResponse;
import lista.servicos.domain.Role;
import lista.servicos.domain.Usuario;
import lista.servicos.repository.UsuarioRepository;
import lista.servicos.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest req) {
        System.out.println("Username: " + req.getUsername());
        System.out.println("Email: " + req.getEmail());
        System.out.println("Password: " + req.getPassword());
        if (req.getEmail() == null || req.getEmail().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RegisterResponse.builder().message("Email é obrigatório").build());
        }
        if (usuarioRepository.findByEmail(req.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(RegisterResponse.builder().message("E-mail já está em uso").build());
        }
        Usuario u = new Usuario(req.getUsername(), req.getEmail(), passwordEncoder.encode(req.getPassword()), Role.USER);
        usuarioRepository.save(u);
        return ResponseEntity.status(HttpStatus.CREATED)
                    .body(RegisterResponse.builder()
                            .message("Usuário registrado com sucesso")
                            .build());
        }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest req) {
        var opt = usuarioRepository.findByEmail(req.getEmail());
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "E-mail ou senha inválidos"));
        }
        Usuario u = opt.get();
        if (!passwordEncoder.matches(req.getPassword(), u.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "E-mail ou senha inválidos"));
        }
        String token = jwtUtil.generateToken(u);
        return ResponseEntity.ok(Map.of("token", token, "role", "ROLE_" + u.getRole().name(), "email", u.getEmail(), "username", u.getUsername()));
    }
}
