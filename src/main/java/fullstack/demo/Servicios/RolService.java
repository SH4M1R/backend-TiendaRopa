package fullstack.demo.Servicios;

import java.util.List;

import fullstack.demo.Entidad.Intranet.Rol;

public interface RolService {
    List<Rol> listarRoles();
    Rol crearRol(Rol rol);
    Rol obtenerRolPorId(Integer idRol);
    Rol actualizarRol(Rol rol);
    void eliminarRol(Integer idRol);
}