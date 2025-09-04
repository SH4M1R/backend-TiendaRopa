package fullstack.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import fullstack.demo.Entidad.DetalleVenta;

public interface DetalleVentaDAO extends JpaRepository <DetalleVenta, Integer>{

}
