package fullstack.demo.RestControl.Intranet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import fullstack.demo.Entidad.Intranet.Empleado;
import fullstack.demo.Entidad.Intranet.Rol;
import fullstack.demo.Servicios.Intranet.EmpleadoService;
import fullstack.demo.Servicios.Intranet.RolService;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@CrossOrigin(origins = "http://localhost:7500")
public class EmpleadoControlador {

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private RolService rolService;

    @GetMapping
    public List<Empleado> listarEmpleados() { return empleadoService.listarEmpleados(); }

    @GetMapping("/{id}")
    public Empleado obtenerEmpleado(@PathVariable Integer id) { return empleadoService.obtenerEmpleadoPorId(id); }

    @PostMapping 
    public Empleado crearEmpleado(@RequestBody Empleado empleado) {
        Rol rol = rolService.obtenerRolPorId(empleado.getRol().getIdRol());
        empleado.setRol(rol);
        return empleadoService.crearEmpleado(empleado);
    }

    @PutMapping("/{id}")
    public Empleado actualizarEmpleado(@PathVariable Integer id, @RequestBody Empleado empleado) {
        Rol rol = rolService.obtenerRolPorId(empleado.getRol().getIdRol());
        empleado.setRol(rol);
        empleado.setIdEmpleado(id);
        return empleadoService.actualizarEmpleado(empleado);
    }

    @DeleteMapping("/{id}")
    public void eliminarEmpleado(@PathVariable Integer id) {
        empleadoService.eliminarEmpleado(id);
    }
}