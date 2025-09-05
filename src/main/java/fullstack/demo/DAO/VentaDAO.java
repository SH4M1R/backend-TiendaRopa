package fullstack.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import fullstack.demo.Entidad.Venta;

public interface VentaDAO extends JpaRepository <Venta, Integer>{

}
