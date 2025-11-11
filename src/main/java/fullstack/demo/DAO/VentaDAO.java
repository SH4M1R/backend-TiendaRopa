package fullstack.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import fullstack.demo.Entidad.Venta;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface VentaDAO extends JpaRepository<Venta, Integer> {
    
    @Query("SELECT SUM(v.total) FROM Venta v WHERE v.fechaVenta BETWEEN :start AND :end")
    BigDecimal sumTotalByFechaVentaBetween(@Param("start") LocalDateTime start, 
                                          @Param("end") LocalDateTime end);
    
    Long countByFechaVentaBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT MONTH(v.fechaVenta) as mes, SUM(v.total) as total " +
           "FROM Venta v " +
           "WHERE v.fechaVenta >= :startDate " +
           "GROUP BY MONTH(v.fechaVenta) " +
           "ORDER BY mes")
    List<Object[]> findVentasGroupedByMonth(@Param("startDate") LocalDateTime startDate);
    
    List<Venta> findTop5ByOrderByFechaVentaDesc();
}