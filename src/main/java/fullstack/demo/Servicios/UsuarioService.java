package fullstack.demo.Servicios;

import fullstack.demo.Entidad.Usuario;
import org.springframework.web.multipart.MultipartFile;

public interface UsuarioService {
    Usuario obtenerUsuarioPorId(Integer id);
    Usuario actualizarUsuario(Usuario usuario);
    void cambiarPassword(Integer usuarioId, String nuevaPassword);
    String guardarAvatar(Integer usuarioId, MultipartFile file);
}