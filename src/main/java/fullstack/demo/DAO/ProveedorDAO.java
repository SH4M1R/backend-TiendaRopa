package fullstack.demo.DAO;
import org.springframework.data.jpa.repository.JpaRepository;
import fullstack.demo.Entidad.Proveedor;

public interface ProveedorDAO extends JpaRepository <Proveedor, Integer>{

}
