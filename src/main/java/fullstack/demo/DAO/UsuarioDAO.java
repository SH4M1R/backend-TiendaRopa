package fullstack.demo.DAO;
import org.springframework.data.jpa.repository.JpaRepository;

import fullstack.demo.Entidad.App.Usuario;

public interface UsuarioDAO extends JpaRepository <Usuario, Integer>{

}
