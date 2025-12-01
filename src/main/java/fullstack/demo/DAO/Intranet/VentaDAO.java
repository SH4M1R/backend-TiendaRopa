package fullstack.demo.DAO.Intranet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.util.List;
import fullstack.demo.Entidad.Intranet.Venta;

public interface VentaDAO extends JpaRepository<Venta, Integer> {

    @Query("SELECT COUNT(v) FROM Venta v")
    Long countVentas();

    @Query("SELECT COALESCE(SUM(v.total), 0) FROM Venta v")
    BigDecimal sumTotalVentas();

    @Query("SELECT v FROM Venta v ORDER BY v.fechaVenta DESC")
    List<Venta> findAllOrderByFechaDesc();
}
