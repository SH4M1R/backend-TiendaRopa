
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

//
//import fullstack.demo.DAO.UserRepository;
//import fullstack.demo.DTO.LoginDTO;
//
//import fullstack.demo.DTO.RegisterRequest;
//import fullstack.demo.DTO.VerifyCodeRequest;
//import fullstack.demo.DTO.VerifyDTO;
//import fullstack.demo.Entidad.Empleado;
//import fullstack.demo.Entidad.User;
//import fullstack.demo.Entidad.Usuario;
//import fullstack.demo.Servicios.*;
//import fullstack.demo.Configuracion.JwtUtil;
//import jakarta.mail.MessagingException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.*;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@CrossOrigin(origins = "http://localhost:7500") // React
//@RestController
//@RequestMapping("/api/auth")
//public class AuthControlador {
//
//    @Autowired
//    private AuthService authService; // ✅ Nuevo servicio de autenticación con 2FA
//
//    @Autowired
//    private UserRepository userRepository;
//////    @Autowired
////    private UsuarioService usuarioService;
////
////    @Autowired
////    private EmpleadoService empleadoService;
//
////    @Autowired
////    private JwtUtil jwtUtil;
//
//    // ===========================
//    // REGISTRO CON ENVÍO DE CÓDIGO
//    // ===========================
////    @PostMapping("/register")
////    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
////        try {
////            authService.registrarUsuario(registerDTO);
////            return ResponseEntity.ok("Usuario registrado. Verifica tu email para activar tu cuenta.");
////        } catch (MessagingException e) {
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////                    .body("Error al enviar correo: " + e.getMessage());
////        } catch (RuntimeException e) {
////            return ResponseEntity.badRequest().body(e.getMessage());
////        } catch (Exception e) {
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////                    .body("Error general en el registro: " + e.getMessage());
////        }
////    }
//
//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
//        try {
//            User user = authService.registerUser(
//                    request.getEmail(),
//                    request.getPassword(),
//                    request.getNombre()
//            );
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("success", true);
//            response.put("message", "Código de verificación enviado a tu email");
//            response.put("email", user.getEmail());
//
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            Map<String, Object> response = new HashMap<>();
//            response.put("success", false);
//            response.put("message", e.getMessage());
//            return ResponseEntity.badRequest().body(response);
//        }
//    }
//
//    // ===========================
//    // VERIFICAR CÓDIGO OTP
//    // ===========================
////    @PostMapping("/verificar")
////    public ResponseEntity<?> verificar(@RequestBody VerifyDTO verifyDTO) {
////        try {
////            boolean valido = authService.verificarCodigo(
////                    verifyDTO.getIdentificador(),
////                    verifyDTO.getCodigo(),
////                    verifyDTO.getMetodo()
////            );
////
////            if (valido) {
////                return ResponseEntity.ok("Cuenta verificada correctamente");
////            } else {
////                return ResponseEntity.badRequest().body("Código inválido o expirado");
////            }
////        } catch (Exception e) {
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////                    .body("Error al verificar código: " + e.getMessage());
////        }
////    }
//
//    //    @PostMapping("/verificar")
////    public ResponseEntity<?> verifyCode(@RequestBody VerifyCodeRequest request) {
////        try {
////            boolean isValid = authService.verifyCode(
////                    request.getEmail(),
////                    request.getCode()
////            );
////
////            Map<String, Object> response = new HashMap<>();
////
////            if (isValid) {
////                response.put("success", true);
////                response.put("message", "Cuenta verificada exitosamente");
////                return ResponseEntity.ok(response);
////            } else {
////                response.put("success", false);
////                response.put("message", "Código inválido");
////                return ResponseEntity.badRequest().body(response);
////            }
////        } catch (Exception e) {
////            Map<String, Object> response = new HashMap<>();
////            response.put("success", false);
////            response.put("message", e.getMessage());
////            return ResponseEntity.badRequest().body(response);
////        }
////    }
////}
//    @PostMapping("/verificar")
//    public ResponseEntity<Map<String, Object>> verificar(@RequestBody Map<String, String> datos) {
//        Map<String, Object> response = new HashMap<>();
//        String email = datos.get("email");
//        String codigo = datos.get("code"); // 👈 Importante: debe coincidir con tu frontend
//
//        boolean valido = authService.verifyCode(email, codigo);
//        if (!valido) {
//            response.put("success", false);
//            response.put("message", "Código incorrecto o expirado.");
//            return ResponseEntity.badRequest().body(response);
//        }
//
//        User user = (User) userRepository.findByEmail(email).orElseThrow();
//        user.setVerified(true);
//        userRepository.save(user);
//
//        response.put("success", true);
//        response.put("message", "Cuenta verificada correctamente.");
//        return ResponseEntity.ok(response);
//    }
//}
//
////    @PostMapping("/reenviar-codigo")
////    public ResponseEntity<Map<String, Object>> reenviar(@RequestBody Map<String, String> datos) {
////        Map<String, Object> response = new HashMap<>();
////        String email = datos.get("email");
////        UserRepository.(email);
////        response.put("success", true);
////        response.put("message", "Se ha reenviado el código al correo.");
////        return ResponseEntity.ok(response);
////    }
////}
//
//    // ===========================
//    // REENVIAR CÓDIGO
//    // ===========================
////    @PostMapping("/reenviar-codigo")
////    public ResponseEntity<?> reenviarCodigo(@RequestBody Map<String, String> body) {
////        try {
////            String identificador = body.get("identificador");
////            String metodo = body.get("metodo");
////
////            if (identificador == null || metodo == null) {
////                return ResponseEntity.badRequest().body("Datos incompletos");
////            }
////
////            authService.reenviarCodigo(identificador, metodo);
////            return ResponseEntity.ok("Código reenviado exitosamente");
////        } catch (Exception e) {
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////                    .body("Error al reenviar código: " + e.getMessage());
////        }
////    }
//
//    // ===========================
//    // LOGIN (Empleado o Usuario)
//    // ===========================
////    @PostMapping("/login")
////    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
////        // Intentar login de empleado
////        try {
////            Empleado empleado = empleadoService.autenticarEmpleado(
////                    loginDTO.getUsername(),
////                    loginDTO.getContrasena()
////            );
////
////            if (empleado != null) {
////                Map<String, Object> claims = Map.of(
////                        "rol", empleado.getRol().getRol(),
////                        "idEmpleado", empleado.getIdEmpleado(),
////                        "tipo", "empleado"
////                );
////                String token = jwtUtil.generarToken(empleado.getUsername(), claims);
////
////                return ResponseEntity.ok(Map.of(
////                        "idEmpleado", empleado.getIdEmpleado(),
////                        "rol", empleado.getRol().getRol(),
////                        "username", empleado.getUsername(),
////                        "token", token,
////                        "tipo", "empleado"
////                ));
////            }
////        } catch (Exception ignored) { }
////
////        // Intentar login de usuario
////        try {
////            Usuario usuario = usuarioService.autenticarUsuario(
////                    loginDTO.getUsername(),
////                    loginDTO.getContrasena()
////            );
////
////            if (usuario != null) {
////                if (!usuario.getVerificado()) {
////                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
////                            .body("Cuenta no verificada. Verifica tu correo.");
////                }
////
////                Map<String, Object> claims = Map.of(
////                        "rol", "USUARIO",
////                        "idUsuario", usuario.getIdUsuario(),
////                        "tipo", "usuario"
////                );
////
////                String token = jwtUtil.generarToken(usuario.getUsername(), claims);
////
////                return ResponseEntity.ok(Map.of(
////                        "idUsuario", usuario.getIdUsuario(),
////                        "rol", "USUARIO",
////                        "username", usuario.getUsername(),
////                        "nombres", usuario.getNombres(),
////                        "apellidos", usuario.getApellidos(),
////                        "email", usuario.getEmail(),
////                        "token", token,
////                        "tipo", "usuario"
////                ));
////            }
////
////            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
////                    .body("Credenciales inválidas");
////
////        } catch (RuntimeException e) {
////            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
////                    .body(e.getMessage());
////        }
////    }
////}
////
