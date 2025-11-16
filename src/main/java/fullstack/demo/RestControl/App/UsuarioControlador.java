package fullstack.demo.RestControl.App;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fullstack.demo.DTO.App.LoginRequest;
import fullstack.demo.Entidad.App.Usuario;
import fullstack.demo.Servicios.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:8000")
public class UsuarioControlador {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registro")
    public String registrarUsuario(@RequestBody Usuario usuario) {

        if (usuarioService.existeCorreo(usuario.getCorreo())) {
            return "Correo ya registrado";
        }

        usuarioService.registrarUsuario(usuario);
        return "Usuario registrado correctamente";
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody LoginRequest request) {
        Usuario usuario = usuarioService.buscarPorCorreo(request.getCorreo());

        if (usuario == null || !usuario.getContrasena().equals(request.getContrasena())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Correo o contraseña incorrectos");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("nombre", usuario.getNombre());
        response.put("correo", usuario.getCorreo());
        response.put("token", "");

        return ResponseEntity.ok(response);
    }

}