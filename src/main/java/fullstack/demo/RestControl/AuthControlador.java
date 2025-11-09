package fullstack.demo.RestControl;


import fullstack.demo.Entidad.Usuario;
import fullstack.demo.Servicios.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:7500")
@RestController
@RequestMapping("/api/auth")
public class AuthControlador{

    @Autowired private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Usuario user) {
        try {
            authService.registrar(user);
            return ResponseEntity.ok("Usuario registrado. Revisa tu correo para el código de verificación.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error en el registro: " + e.getMessage());
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestParam String email, @RequestParam String codigo) {
        boolean ok = authService.verificar(email, codigo);
        return ok ? ResponseEntity.ok("Cuenta verificada con éxito.")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Código inválido o expirado.");
    }
}


//package fullstack.demo.RestControl;
//import fullstack.demo.Configuracion.JwtUtil;
//import fullstack.demo.DTO.LoginDTO;
//import fullstack.demo.DTO.UsuarioDTO;
//import fullstack.demo.Entidad.Empleado;
//import fullstack.demo.Entidad.Usuario;
//import fullstack.demo.Servicios.*;
//// OtpService removed for now (2FA omitted)
//// import fullstack.demo.DAO.UsuarioDAO;
//// import fullstack.demo.Entidad.Usuario;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import java.util.*;
//
//
//@RestController
//@RequestMapping("/api/auth")
//@CrossOrigin(origins = "http://localhost:7500")
//public class AuthControlador {
//
//    @Autowired private EmpleadoService empleadoService;
//    @Autowired private UsuarioService usuarioService;
//    @Autowired private JwtUtil jwtUtil;
//    @Autowired private OtpService otpService;
//    @Autowired private EmailService emailService;
//    @Autowired private SmsService smsService;
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
//        // Primero intentar autenticar como empleado
//        Empleado empleado = empleadoService.autenticarEmpleado(loginDTO.getUsername(), loginDTO.getContrasena());
//
//        if (empleado != null) {
//            // Login exitoso como empleado
//            Map<String, Object> claims = Map.of(
//                    "rol", empleado.getRol().getRol(),
//                    "idEmpleado", empleado.getIdEmpleado(),
//                    "tipo", "empleado"
//            );
//
//            String token = jwtUtil.generarToken(empleado.getUsername(), claims);
//
//            return ResponseEntity.ok(Map.of(
//                    "idEmpleado", empleado.getIdEmpleado(),
//                    "rol", empleado.getRol().getRol(),
//                    "username", empleado.getUsername(),
//                    "token", token,
//                    "tipo", "empleado"
//            ));
//        }
//
//        // Si no es empleado, intentar autenticar como usuario
//        try {
//            Usuario usuario = usuarioService.autenticarUsuario(loginDTO.getUsername(), loginDTO.getContrasena());
//
//            if (usuario != null) {
//                // Verificar si la cuenta está verificada
//                if (!usuario.getVerificado()) {
//                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                            .body("Cuenta no verificada. Por favor verifica tu cuenta primero.");
//                }
//
//                // Login exitoso como usuario
//                Map<String, Object> claims = Map.of(
//                        "rol", "USUARIO",
//                        "idUsuario", usuario.getIdUsuario(),
//                        "tipo", "usuario"
//                );
//
//                String token = jwtUtil.generarToken(usuario.getUsername(), claims);
//
//                return ResponseEntity.ok(Map.of(
//                        "idUsuario", usuario.getIdUsuario(),
//                        "rol", "USUARIO",
//                        "username", usuario.getUsername(),
//                        "nombres", usuario.getNombres(),
//                        "apellidos", usuario.getApellidos(),
//                        "email", usuario.getEmail(),
//                        "token", token,
//                        "tipo", "usuario"
//                ));
//            }
//
//            // Si no es ni empleado ni usuario
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
//        }
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<?> registrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
//        try {
//            // Registrar usuario (sin verificar aún)
//            Usuario usuario = usuarioService.registrarUsuario(usuarioDTO);
//
//            // Generar código OTP
//            String codigo = otpService.generarOTP();
//
//            // Determinar método de verificación (siempre email por ahora)
//            String metodo = "email";
//
//            // Almacenar OTP y enviar por email
//            otpService.almacenarOTP(usuario.getEmail(), codigo);
//            emailService.enviarCodigoVerificacion(usuario.getEmail(), codigo);
//
//            // Retornar JSON con los datos necesarios
//            Map<String, Object> response = new HashMap<>();
//            response.put("mensaje", "Usuario registrado. Código enviado por email");
//            response.put("metodo", "email");
//            response.put("identificador", usuario.getEmail());
//            response.put("idUsuario", usuario.getIdUsuario());
//
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            Map<String, Object> errorResponse = new HashMap<>();
//            errorResponse.put("error", e.getMessage());
//            return ResponseEntity.badRequest().body(errorResponse);
//        }
//    }
//
//    @PostMapping("/verificar")
//    public ResponseEntity<?> verificarCuenta(@RequestBody VerificacionDTO verificacionDTO) {
//        try {
//            // Verificar el código OTP
//            boolean esValido = otpService.verificarOTP(
//                    verificacionDTO.getIdentificador(),
//                    verificacionDTO.getCodigo()
//            );
//
//            if (!esValido) {
//                return ResponseEntity.badRequest().body("Código inválido o expirado");
//            }
//
//            // Marcar usuario como verificado
//            Usuario usuario;
//            if ("email".equalsIgnoreCase(verificacionDTO.getMetodo())) {
//                usuario = usuarioService.obtenerUsuarioPorEmail(verificacionDTO.getIdentificador());
//            } else {
//                usuario = usuarioService.obtenerUsuarioPorTelefono(verificacionDTO.getIdentificador());
//            }
//
//            if (usuario == null) {
//                return ResponseEntity.badRequest().body("Usuario no encontrado");
//            }
//
//            usuarioService.verificarUsuario(usuario.getIdUsuario());
//
//            // Enviar email de bienvenida
//            emailService.enviarEmailBienvenida(
//                    usuario.getEmail(),
//                    usuario.getNombres() + " " + usuario.getApellidos()
//            );
//
//            return ResponseEntity.ok(Map.of(
//                    "mensaje", "Cuenta verificada exitosamente",
//                    "username", usuario.getUsername()
//            ));
//
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
//        }
//    }
//
//    @PostMapping("/reenviar-codigo")
//    public ResponseEntity<?> reenviarCodigo(@RequestBody Map<String, String> datos) {
//        try {
//            String identificador = datos.get("identificador");
//            String metodo = datos.get("metodo");
//
//            if (identificador == null || metodo == null) {
//                return ResponseEntity.badRequest().body("Faltan datos: identificador o metodo");
//            }
//
//            // Generar nuevo código
//            String codigo = otpService.generarOTP();
//            otpService.almacenarOTP(identificador, codigo);
//
//            // Enviar según el método
//            if ("sms".equalsIgnoreCase(metodo)) {
//                smsService.enviarCodigoVerificacion(identificador, codigo);
//            } else {
//                emailService.enviarCodigoVerificacion(identificador, codigo);
//            }
//
//            return ResponseEntity.ok(Map.of(
//                    "mensaje", "Código reenviado exitosamente"
//            ));
//
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
//        }
//    }
//}
//package fullstack.demo.RestControl;
//
//import fullstack.demo.Entidad.Usuario;
//import fullstack.demo.ServiciosImpl.AuthServiceImpl;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import jakarta.mail.MessagingException;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/auth")
//@CrossOrigin(origins = "*")
//@RequiredArgsConstructor
//public class AuthControlador {
//
//    private final AuthServiceImpl authService;
//
//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody Usuario usuario) throws MessagingException {
//        authService.registrarUsuario(usuario);
//        return ResponseEntity.ok(Map.of("mensaje", "Usuario registrado. Código enviado al email."));
//    }
//
//    @PostMapping("/verificar")
//    public ResponseEntity<?> verificar(@RequestBody Map<String, String> body) {
//        String identificador = body.get("identificador");
//        String codigo = body.get("codigo");
//
//        boolean exito = authService.verificarCodigo(identificador, codigo);
//        if (exito)
//            return ResponseEntity.ok(Map.of("mensaje", "Cuenta verificada correctamente"));
//        else
//            return ResponseEntity.badRequest().body(Map.of("error", "Código inválido o expirado"));
//    }
//
//    @PostMapping("/reenviar-codigo")
//    public ResponseEntity<?> reenviarCodigo(@RequestBody Map<String, String> body) throws MessagingException {
//        String email = body.get("identificador");
//        authService.reenviarCodigo(email);
//        return ResponseEntity.ok(Map.of("mensaje", "Código reenviado"));
//    }
//}


