package fullstack.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import fullstack.demo.Entidad.App.DetalleVentaOnline;

public interface DetalleVentaOnlineDAO extends JpaRepository <DetalleVentaOnline, Integer>{

}
