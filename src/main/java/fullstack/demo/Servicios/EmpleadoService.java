package fullstack.demo.Servicios;

import java.util.List;
import fullstack.demo.Entidad.Empleado;

public interface EmpleadoService {
    List<Empleado> listarEmpleados();
    Empleado crearEmpleado(Empleado empleado);
    Empleado obtenerEmpleadoPorId(Integer idEmpleado);
    Empleado actualizarEmpleado(Empleado empleado);
    void eliminarEmpleado(Integer idEmpleado);
}
