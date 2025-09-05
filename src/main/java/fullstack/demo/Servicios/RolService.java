package fullstack.demo.Servicios;

import fullstack.demo.Entidad.Rol;
import java.util.List;

public interface RolService {
    Rol crearRol(Rol rol);
    List<Rol> listarRoles();
    Rol obtenerRolPorId(Integer id);
    Rol actualizarRol(Rol rol);
    void eliminarRol(Integer id);
}
