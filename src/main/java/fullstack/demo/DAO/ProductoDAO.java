package fullstack.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import fullstack.demo.Entidad.Producto;

public interface ProductoDAO extends JpaRepository <Producto, Integer>{

}
