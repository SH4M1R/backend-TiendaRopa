package fullstack.demo.RestControl.Intranet;
import fullstack.demo.Configuracion.JwtUtil;
import fullstack.demo.DTO.Intranet.LoginDTO;
import fullstack.demo.Entidad.Intranet.Empleado;
import fullstack.demo.Servicios.Intranet.EmpleadoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/auth")

public class AuthControlador {

    @Autowired private EmpleadoService empleadoService;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Empleado empleado = empleadoService.autenticarEmpleado(loginDTO.getUsername(), loginDTO.getContrasena());
        if (empleado == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }

        Map<String, Object> claims = Map.of(
                "rol", empleado.getRol().getRol(),
                "idEmpleado", empleado.getIdEmpleado()
        );

        String token = jwtUtil.generarToken(empleado.getUsername(), claims);

        return ResponseEntity.ok(Map.of(
                "idEmpleado", empleado.getIdEmpleado(),
                "rol", empleado.getRol().getRol(),
                "username", empleado.getUsername(),
                "token", token
        ));
    }
}
