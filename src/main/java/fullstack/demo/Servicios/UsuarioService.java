package fullstack.demo.Servicios;

import fullstack.demo.Entidad.App.Usuario;

public interface UsuarioService {

    Usuario registrarUsuario(Usuario usuario);
    boolean existeCorreo(String correo);
    Usuario buscarPorCorreo(String correo);
    Usuario login(String correo, String contrasena);
    Usuario actualizarUsuario(Usuario usuario);
    void eliminarUsuario(Integer idUsuario);
}
