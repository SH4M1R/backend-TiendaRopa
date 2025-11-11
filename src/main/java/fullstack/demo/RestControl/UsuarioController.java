    package fullstack.demo.RestControl;

    import fullstack.demo.Entidad.Usuario;
    import fullstack.demo.Servicios.UsuarioService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;

    import java.util.HashMap;
    import java.util.Map;

    @RestController
    @RequestMapping("/api/usuarios")
    @CrossOrigin(origins = "http://localhost:3000")
    public class UsuarioController {

        @Autowired
        private UsuarioService usuarioService;

        @GetMapping("/perfil")
        public ResponseEntity<Usuario> obtenerPerfil() {
            // En un caso real, obtendrías el ID del usuario del contexto de seguridad
            // Por ahora simulamos con el usuario con ID 1
            Usuario usuario = usuarioService.obtenerUsuarioPorId(1);
            return ResponseEntity.ok(usuario);
        }

        @PutMapping("/perfil")
        public ResponseEntity<Usuario> actualizarPerfil(@RequestBody Usuario usuario) {
            Usuario usuarioActualizado = usuarioService.actualizarUsuario(usuario);
            return ResponseEntity.ok(usuarioActualizado);
        }

        @PutMapping("/cambiar-password")
        public ResponseEntity<Map<String, String>> cambiarPassword(@RequestBody Map<String, String> passwordData) {
            // En un caso real, validarías la contraseña actual
            usuarioService.cambiarPassword(1, passwordData.get("nuevaPassword"));
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Contraseña actualizada correctamente");
            return ResponseEntity.ok(response);
        }

        @PostMapping("/avatar")
        public ResponseEntity<Map<String, String>> uploadAvatar(@RequestParam("avatar") MultipartFile file) {
            // Lógica para guardar el archivo
            String fileName = usuarioService.guardarAvatar(1, file);
            
            Map<String, String> response = new HashMap<>();
            response.put("avatarUrl", "/upload/" + fileName);
            return ResponseEntity.ok(response);
        }
    }