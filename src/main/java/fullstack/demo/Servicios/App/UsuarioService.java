package fullstack.demo.Servicios.App;

import fullstack.demo.Entidad.App.Usuario;
import fullstack.demo.Entidad.Intranet.Empleado;

public interface UsuarioService {

    Usuario registrarUsuario(Usuario usuario);
    boolean existeCorreo(String correo);
    Usuario buscarPorCorreo(String correo);
    Usuario login(String correo, String contrasena);
    Usuario actualizarUsuario(Usuario usuario);
    void eliminarUsuario(Integer idUsuario);
    Usuario generarYGuardarCodigo(String correo);
    Usuario verificarCodigoYResetearContrasena(String correo, String codigo, String nuevaContrasena);
    Empleado loginEmpleado(String username, String contrasena);
}
