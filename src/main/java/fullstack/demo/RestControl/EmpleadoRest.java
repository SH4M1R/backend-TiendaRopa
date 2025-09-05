package fullstack.demo.RestControl;

import fullstack.demo.Entidad.Empleado;
import fullstack.demo.Servicios.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@CrossOrigin(origins = "http://localhost:5173")
public class EmpleadoRest {

    @Autowired
    private EmpleadoService empleadoService;

    @PostMapping
    public Empleado crear(@RequestBody Empleado empleado) {
        return empleadoService.crearEmpleado(empleado);
    }

    @GetMapping
    public List<Empleado> listar() {
        return empleadoService.listarEmpleados();
    }

    @GetMapping("/{id}")
    public Empleado obtener(@PathVariable Integer id) {
        return empleadoService.obtenerEmpleadoPorId(id);
    }

    @PutMapping("/{id}")
    public Empleado actualizar(@PathVariable Integer id, @RequestBody Empleado empleado) {
        empleado.setIdEmpleado(id);
        return empleadoService.actualizarEmpleado(empleado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        empleadoService.eliminarEmpleado(id);
    }
}