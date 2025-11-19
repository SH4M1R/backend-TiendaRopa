package fullstack.demo.DAO.App;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import fullstack.demo.Entidad.App.VentaOnline;

public interface VentaOnlineDAO extends JpaRepository<VentaOnline, Integer> {
    List<VentaOnline> findByUsuario_IdUsuario(Integer idUsuario);
}