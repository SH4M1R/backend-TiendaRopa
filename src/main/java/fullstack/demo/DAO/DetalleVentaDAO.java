package fullstack.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import fullstack.demo.Entidad.Intranet.DetalleVenta;

public interface DetalleVentaDAO extends JpaRepository <DetalleVenta, Integer>{

}
