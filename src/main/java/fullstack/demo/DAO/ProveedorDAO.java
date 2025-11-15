package fullstack.demo.DAO;
import org.springframework.data.jpa.repository.JpaRepository;

import fullstack.demo.Entidad.Intranet.Proveedor;

public interface ProveedorDAO extends JpaRepository <Proveedor, Integer>{

}
