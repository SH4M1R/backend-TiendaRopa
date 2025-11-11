package fullstack.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import fullstack.demo.Entidad.Compra;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface CompraDAO extends JpaRepository<Compra, Integer> {
    
    // Corregir nombres de columnas según la base de datos
    @Query("SELECT c FROM Compra c WHERE c.proveedor.idProveedor = :proveedorId")
    List<Compra> findByProveedorIdProveedor(@Param("proveedorId") Integer proveedorId);
    
    @Query("SELECT c FROM Compra c WHERE c.empleado.idEmpleado = :empleadoId")
    List<Compra> findByEmpleadoIdEmpleado(@Param("empleadoId") Integer empleadoId);
    
    @Query("SELECT c FROM Compra c WHERE c.fechaCompra BETWEEN :startDate AND :endDate ORDER BY c.fechaCompra DESC")
    List<Compra> findComprasByFechaRange(@Param("startDate") LocalDateTime startDate, 
                                        @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT COALESCE(SUM(c.total), 0) FROM Compra c WHERE c.fechaCompra BETWEEN :startDate AND :endDate")
    BigDecimal findTotalComprasByFechaRange(@Param("startDate") LocalDateTime startDate, 
                                           @Param("endDate") LocalDateTime endDate);

    // Nuevos métodos para dashboard
    @Query("SELECT COUNT(c) FROM Compra c WHERE c.fechaCompra BETWEEN :startDate AND :endDate")
    Long countByFechaCompraBetween(@Param("startDate") LocalDateTime startDate, 
                                  @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT c FROM Compra c ORDER BY c.fechaCompra DESC LIMIT 5")
    List<Compra> findTop5ByOrderByFechaCompraDesc();
}