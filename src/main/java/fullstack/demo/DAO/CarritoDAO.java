package fullstack.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import fullstack.demo.Entidad.Carrito;

public interface CarritoDAO extends JpaRepository<Carrito, Integer>{

}
