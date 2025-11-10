
package fullstack.demo.RestControl;

import fullstack.demo.DTO.LoginDTO;
import fullstack.demo.DTO.RegisterDTO;
import fullstack.demo.DTO.VerifyDTO;
import fullstack.demo.Entidad.Empleado;
import fullstack.demo.Entidad.Usuario;
import fullstack.demo.Servicios.*;
import fullstack.demo.Configuracion.JwtUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:7500") // React
@RestController
@RequestMapping("/api/auth")
public class AuthControlador {

    @Autowired
    private AuthService authService; // ✅ Nuevo servicio de autenticación con 2FA

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private JwtUtil jwtUtil;

    // ===========================
    // REGISTRO CON ENVÍO DE CÓDIGO
    // ===========================
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        try {
            authService.registrarUsuario(registerDTO);
            return ResponseEntity.ok("Usuario registrado. Verifica tu email para activar tu cuenta.");
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al enviar correo: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error general en el registro: " + e.getMessage());
        }
    }

    // ===========================
    // VERIFICAR CÓDIGO OTP
    // ===========================
    @PostMapping("/verificar")
    public ResponseEntity<?> verificar(@RequestBody VerifyDTO verifyDTO) {
        try {
            boolean valido = authService.verificarCodigo(
                    verifyDTO.getIdentificador(),
                    verifyDTO.getCodigo(),
                    verifyDTO.getMetodo()
            );

            if (valido) {
                return ResponseEntity.ok("Cuenta verificada correctamente");
            } else {
                return ResponseEntity.badRequest().body("Código inválido o expirado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al verificar código: " + e.getMessage());
        }
    }

    // ===========================
    // REENVIAR CÓDIGO
    // ===========================
    @PostMapping("/reenviar-codigo")
    public ResponseEntity<?> reenviarCodigo(@RequestBody Map<String, String> body) {
        try {
            String identificador = body.get("identificador");
            String metodo = body.get("metodo");

            if (identificador == null || metodo == null) {
                return ResponseEntity.badRequest().body("Datos incompletos");
            }

            authService.reenviarCodigo(identificador, metodo);
            return ResponseEntity.ok("Código reenviado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al reenviar código: " + e.getMessage());
        }
    }

    // ===========================
    // LOGIN (Empleado o Usuario)
    // ===========================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        // Intentar login de empleado
        try {
            Empleado empleado = empleadoService.autenticarEmpleado(
                    loginDTO.getUsername(),
                    loginDTO.getContrasena()
            );

            if (empleado != null) {
                Map<String, Object> claims = Map.of(
                        "rol", empleado.getRol().getRol(),
                        "idEmpleado", empleado.getIdEmpleado(),
                        "tipo", "empleado"
                );
                String token = jwtUtil.generarToken(empleado.getUsername(), claims);

                return ResponseEntity.ok(Map.of(
                        "idEmpleado", empleado.getIdEmpleado(),
                        "rol", empleado.getRol().getRol(),
                        "username", empleado.getUsername(),
                        "token", token,
                        "tipo", "empleado"
                ));
            }
        } catch (Exception ignored) { }

        // Intentar login de usuario
        try {
            Usuario usuario = usuarioService.autenticarUsuario(
                    loginDTO.getUsername(),
                    loginDTO.getContrasena()
            );

            if (usuario != null) {
                if (!usuario.getVerificado()) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body("Cuenta no verificada. Verifica tu correo.");
                }

                Map<String, Object> claims = Map.of(
                        "rol", "USUARIO",
                        "idUsuario", usuario.getIdUsuario(),
                        "tipo", "usuario"
                );

                String token = jwtUtil.generarToken(usuario.getUsername(), claims);

                return ResponseEntity.ok(Map.of(
                        "idUsuario", usuario.getIdUsuario(),
                        "rol", "USUARIO",
                        "username", usuario.getUsername(),
                        "nombres", usuario.getNombres(),
                        "apellidos", usuario.getApellidos(),
                        "email", usuario.getEmail(),
                        "token", token,
                        "tipo", "usuario"
                ));
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales inválidas");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }
    }
}

