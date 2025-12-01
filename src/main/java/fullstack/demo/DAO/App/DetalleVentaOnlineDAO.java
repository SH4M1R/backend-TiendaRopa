package fullstack.demo.DAO.App;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import fullstack.demo.Entidad.App.DetalleVentaOnline;

public interface DetalleVentaOnlineDAO extends JpaRepository<DetalleVentaOnline, Integer> {
    List<DetalleVentaOnline> findByVentaOnline_IdVentaOnline(Integer idVentaOnline);
}