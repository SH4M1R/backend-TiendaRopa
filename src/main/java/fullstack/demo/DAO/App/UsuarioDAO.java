package fullstack.demo.DAO.App;

import org.springframework.data.jpa.repository.JpaRepository;
import fullstack.demo.Entidad.App.Usuario;

public interface UsuarioDAO extends JpaRepository<Usuario, Integer> {

    Usuario findByCorreo(String correo);
    Usuario findByCorreoAndContrasena(String correo, String contrasena);
}
