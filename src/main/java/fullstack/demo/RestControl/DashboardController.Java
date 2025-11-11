package fullstack.demo.RestControl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = {"http://localhost:7500", "http://localhost:3000"})
public class DashboardController {

    @Autowired
    private CompraDAO compraDAO;

    @Autowired
    private ProductoDAO productoDAO;

    @Autowired
    private ProveedorDAO proveedorDAO;

    @Autowired
    private EmpleadoDAO empleadoDAO;

    @GetMapping("/compras/estadisticas")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticasCompras() {
        Map<String, Object> estadisticas = new HashMap<>();

        try {
            LocalDateTime inicioMes = LocalDate.now().withDayOfMonth(1).atStartOfDay();
            LocalDateTime finMes = LocalDateTime.now();
            
            // Estadísticas de compras
            BigDecimal comprasMes = compraDAO.findTotalComprasByFechaRange(inicioMes, finMes);
            Long totalComprasMes = compraDAO.countByFechaCompraBetween(inicioMes, finMes);
            Long totalProveedores = proveedorDAO.count();
            Long totalEmpleados = empleadoDAO.count();

            estadisticas.put("comprasMesActual", comprasMes);
            estadisticas.put("totalComprasMes", totalComprasMes);
            estadisticas.put("totalProveedores", totalProveedores);
            estadisticas.put("totalEmpleados", totalEmpleados);
            estadisticas.put("comprasRecientes", compraDAO.findTop5ByOrderByFechaCompraDesc());

            return ResponseEntity.ok(estadisticas);
        } catch (Exception e) {
            estadisticas.put("error", "Error al obtener estadísticas de compras: " + e.getMessage());
            return ResponseEntity.internalServerError().body(estadisticas);
        }
    }
}