package fullstack.demo.DAO;
import org.springframework.data.jpa.repository.JpaRepository;
import fullstack.demo.Entidad.Usuario;

public interface UsuarioDAO extends JpaRepository <Usuario, Integer>{

}
