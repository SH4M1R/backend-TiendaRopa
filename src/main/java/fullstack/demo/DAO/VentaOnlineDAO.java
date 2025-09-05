package fullstack.demo.DAO;
import org.springframework.data.jpa.repository.JpaRepository;
import fullstack.demo.Entidad.VentaOnline;


public interface VentaOnlineDAO extends JpaRepository <VentaOnline, Integer>{

}
