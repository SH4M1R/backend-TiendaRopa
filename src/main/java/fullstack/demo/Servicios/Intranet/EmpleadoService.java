package fullstack.demo.Servicios.Intranet;

import java.util.List;

import fullstack.demo.Entidad.Intranet.Empleado;

public interface EmpleadoService {
    List<Empleado> listarEmpleados();
    Empleado crearEmpleado(Empleado empleado);
    Empleado obtenerEmpleadoPorId(Integer idEmpleado);
    Empleado actualizarEmpleado(Empleado empleado);
    void eliminarEmpleado(Integer idEmpleado);
    Empleado autenticarEmpleado(String usuario, String contrasena);
}