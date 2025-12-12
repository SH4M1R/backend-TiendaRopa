package fullstack.demo.RestControl.App;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fullstack.demo.Configuracion.JwtUtil;
import fullstack.demo.DTO.App.LoginRequest;
import fullstack.demo.Entidad.App.Usuario;
import fullstack.demo.Servicios.App.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:8000")
public class UsuarioControlador {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        if (usuarioService.existeCorreo(usuario.getCorreo())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("mensaje", "Correo ya registrado"));
        }
        Usuario registrado = usuarioService.registrarUsuario(usuario);
        return ResponseEntity.ok(Map.of(
                "mensaje", "Usuario registrado correctamente",
                "usuario", registrado
        ));
    }

    @PostMapping("/login")
public ResponseEntity<?> loginUsuario(@RequestBody LoginRequest request) {

    // 1. Intento login como Usuario APP
    Usuario usuario = usuarioService.login(request.getCorreo(), request.getContrasena());

    if (usuario != null) {
        Map<String, Object> claims = Map.of(
            "idUsuario", usuario.getIdUsuario(),
            "nombre", usuario.getNombre(),
            "rol", "cliente"     // 🔥 Esto es importante
        );

        String token = jwtUtil.generarToken(usuario.getCorreo(), claims);

        return ResponseEntity.ok(Map.of(
            "token", token,
            "usuario", Map.of(
                "idUsuario", usuario.getIdUsuario(),
                "nombre", usuario.getNombre(),
                "correo", usuario.getCorreo(),
                "rol", "cliente"
            )
        ));
    }

    // 2. Intento login como Empleado DELIVERY
    var empleado = usuarioService.loginEmpleado(request.getCorreo(), request.getContrasena());

    if (empleado != null) {
        // Validar rol DELIVERY
        if (!empleado.getRol().getRol().equalsIgnoreCase("DELIVERY")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("mensaje", "El empleado no tiene rol DELIVERY"));
        }

        Map<String, Object> claims = Map.of(
            "idUsuario", empleado.getIdEmpleado(),
            "nombre", empleado.getUsername(),
            "rol", "delivery"
        );

        String token = jwtUtil.generarToken(empleado.getUsername(), claims);

        return ResponseEntity.ok(Map.of(
            "token", token,
            "usuario", Map.of(
                "idUsuario", empleado.getIdEmpleado(),
                "nombre", empleado.getUsername(),
                "correo", empleado.getUsername(),
                "rol", "delivery"
            )
        ));
    }

    // 3. Si ninguno coincide
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("mensaje", "Correo o contraseña incorrectos"));
}


    @GetMapping("/perfil")
    public ResponseEntity<?> obtenerPerfil(@RequestParam String correo) {
        Usuario usuario = usuarioService.buscarPorCorreo(correo);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "No se encontró el correo del usuario"));
        }
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/actualizar/{idUsuario}")
    public ResponseEntity<?> actualizarUsuario(
            @PathVariable Integer idUsuario,
            @RequestBody Map<String, Object> datos
    ) {
        String correoActual = datos.getOrDefault("correoActual", "").toString();
        Usuario usuario = usuarioService.buscarPorCorreo(correoActual);

        if (usuario == null || !usuario.getIdUsuario().equals(idUsuario)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Usuario no encontrado"));
        }

        if (datos.containsKey("nombre")) usuario.setNombre((String) datos.get("nombre"));
        if (datos.containsKey("direccion")) usuario.setDireccion((String) datos.get("direccion"));

        if (datos.containsKey("telefono") && datos.get("telefono") != null) {
            usuario.setTelefono(datos.get("telefono").toString());
        }

        if (datos.containsKey("contrasena") && datos.get("contrasena") != null) {
            usuario.setContrasena((String) datos.get("contrasena"));
        }

        Usuario actualizado = usuarioService.actualizarUsuario(usuario);

        Map<String, Object> responseBody = Map.of(
                "mensaje", "Perfil actualizado correctamente",
                "usuario", Map.of(
                        "idUsuario", actualizado.getIdUsuario(),
                        "nombre", actualizado.getNombre(),
                        "correo", actualizado.getCorreo(),
                        "direccion", actualizado.getDireccion() != null ? actualizado.getDireccion() : "",
                        "telefono", actualizado.getTelefono() != null ? actualizado.getTelefono() : ""
                )
        );

        return ResponseEntity.ok(responseBody);
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Integer idUsuario) {
        usuarioService.eliminarUsuario(idUsuario);
        return ResponseEntity.ok(Map.of("mensaje", "Usuario eliminado correctamente"));
    }

}