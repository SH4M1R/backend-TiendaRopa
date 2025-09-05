package fullstack.demo.Servicios;

import fullstack.demo.Entidad.Rol;
import java.util.List;

public interface RolService {
    List<Rol> ListarRoles();
    Rol crearRol(Rol rol);
    Rol obtenerRolPorId(Integer idRol);
    Rol actualizarRol(Rol rol);
    void eliminarRol(Integer idRol);

}
