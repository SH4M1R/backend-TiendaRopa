package fullstack.demo.RestControl.App;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fullstack.demo.DTO.App.ResetCodeRequest;
import fullstack.demo.DTO.App.PasswordResetRequest;
import fullstack.demo.Entidad.App.Usuario;
import fullstack.demo.Servicios.App.UsuarioService;

@RestController
@RequestMapping("/api/recuperacion")
@CrossOrigin(origins = "http://localhost:8000")
public class RecuperacionControlador {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/solicitar-codigo")
    public ResponseEntity<Map<String, String>> solicitarCodigo(@RequestBody ResetCodeRequest request) {
        try {
            Usuario usuario = usuarioService.generarYGuardarCodigo(request.getCorreo());
            
            if (usuario == null) {
                return ResponseEntity.ok(Map.of("mensaje", "Si el correo existe, se ha enviado un código."));
            }

            return ResponseEntity.ok(Map.of("mensaje", "Código de recuperación enviado exitosamente."));
        } catch (Exception e) {
            System.err.println("Error al enviar el correo: " + e.getMessage());
            return ResponseEntity.internalServerError()
                .body(Map.of("mensaje", "Error al enviar el código. Inténtelo más tarde."));
        }
    }

    @PostMapping("/resetear-contrasena")
    public ResponseEntity<Map<String, String>> resetearContrasena(@RequestBody PasswordResetRequest request) {
        if (request.getNuevaContrasena() == null || request.getNuevaContrasena().length() < 6) {
             return ResponseEntity.badRequest()
                    .body(Map.of("mensaje", "La nueva contraseña debe tener al menos 6 caracteres."));
        }

        Usuario usuarioActualizado = usuarioService.verificarCodigoYResetearContrasena(
            request.getCorreo(), 
            request.getCodigo(), 
            request.getNuevaContrasena()
        );

        if (usuarioActualizado != null) {
            return ResponseEntity.ok(Map.of("mensaje", "Contraseña actualizada correctamente."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("mensaje", "Código de verificación inválido o expirado."));
        }
    }
}