package fullstack.demo.DAO;
import org.springframework.data.jpa.repository.JpaRepository;

import fullstack.demo.Entidad.Intranet.Compra;

public interface CompraDAO extends JpaRepository< Compra, Integer>{

}
