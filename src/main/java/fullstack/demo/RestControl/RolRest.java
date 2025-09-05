package fullstack.demo.RestControl;

import fullstack.demo.Entidad.Rol;
import fullstack.demo.Servicios.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "http://localhost:5173")
public class RolRest {

    @Autowired
    private RolService rolService;

    @PostMapping
    public Rol crear(@RequestBody Rol rol) {
        return rolService.crearRol(rol);
    }

    @GetMapping
    public List<Rol> listar() {
        return rolService.listarRoles();
    }

    @GetMapping("/{id}")
    public Rol obtener(@PathVariable Integer id) {
        return rolService.obtenerRolPorId(id);
    }

    @PutMapping("/{id}")
    public Rol actualizar(@PathVariable Integer id, @RequestBody Rol rol) {
        rol.setIdRol(id);
        return rolService.actualizarRol(rol);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        rolService.eliminarRol(id);
    }
}
