
package fullstack.demo.RestControl;


import fullstack.demo.Entidad.Usuario;
import fullstack.demo.DAO.UsuarioDAO;
import fullstack.demo.Servicios.VerificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:7500") // o tu puerto React
public class AuthControlador {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private VerificacionService verificacionService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Usuario usuario) {
        Map<String, Object> res = new HashMap<>();

        if (usuarioDAO.findByEmail(usuario.getEmail()).isPresent()) {
            res.put("success", false);
            res.put("message", "El correo ya está registrado.");
            return res;
        }
        if (usuarioDAO.findByUsername(usuario.getUsername()).isPresent()) {
            res.put("success", false);
            res.put("message", "El nombre de usuario ya está en uso.");
            return res;
        }

        usuario.setPassword(encoder.encode(usuario.getPassword()));
        usuario.setVerificado(false);
        usuarioDAO.save(usuario);

        verificacionService.generarYEnviarCodigo(usuario.getEmail());

        res.put("success", true);
        res.put("message", "Usuario registrado. Se envió un código de verificación al correo.");
        return res;
    }

    @PostMapping("/verificar")
    public Map<String, Object> verificar(@RequestBody Map<String, String> datos) {
        String email = datos.get("email");
        String codigo = datos.get("code");

        Map<String, Object> res = new HashMap<>();

        boolean valido = verificacionService.verificarCodigo(email, codigo);
        if (!valido) {
            res.put("success", false);
            res.put("message", "Código incorrecto o expirado.");
            return res;
        }

        Usuario usuario = usuarioDAO.findByEmail(email).orElse(null);
        if (usuario == null) {
            res.put("success", false);
            res.put("message", "Usuario no encontrado.");
            return res;
        }

        usuario.setVerificado(true);
        usuarioDAO.save(usuario);

        res.put("success", true);
        res.put("message", "Cuenta verificada con éxito.");
        return res;
    }

    @PostMapping("/reenviar-codigo")
    public Map<String, Object> reenviarCodigo(@RequestBody Map<String, String> datos) {
        String email = datos.get("email");
        Map<String, Object> res = new HashMap<>();

        if (usuarioDAO.findByEmail(email).isEmpty()) {
            res.put("success", false);
            res.put("message", "No se encontró un usuario con ese correo.");
            return res;
        }

        verificacionService.generarYEnviarCodigo(email);

        res.put("success", true);
        res.put("message", "Se envió un nuevo código de verificación.");
        return res;
    }
}

