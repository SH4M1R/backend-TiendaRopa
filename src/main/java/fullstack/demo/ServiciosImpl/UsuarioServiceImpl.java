package fullstack.demo.ServiciosImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fullstack.demo.DAO.UsuarioDAO;
import fullstack.demo.Entidad.App.Usuario;
import fullstack.demo.Servicios.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Override
    public Usuario registrarUsuario(Usuario usuario) {
        return usuarioDAO.save(usuario);
    }

    @Override
    public boolean existeCorreo(String correo) {
        return usuarioDAO.findAll()
                .stream()
                .anyMatch(u -> u.getCorreo().equalsIgnoreCase(correo));
    }

    @Override
    public Usuario buscarPorCorreo(String correo) {
        return usuarioDAO.findAll()
                .stream()
                .filter(u -> u.getCorreo().equalsIgnoreCase(correo))
                .findFirst()
                .orElse(null);
    }
}
