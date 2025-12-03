package fullstack.demo.RestControl.Intranet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fullstack.demo.DAO.Intranet.VentaDAO;
import fullstack.demo.DAO.ProductoDAO;
import fullstack.demo.DAO.Intranet.ClienteDAO;
import fullstack.demo.Entidad.Intranet.Venta;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")

public class DashboardControlador {

    @Autowired
    private VentaDAO ventaDAO;

    @Autowired
    private ProductoDAO productoDAO;

    @Autowired
    private ClienteDAO clienteDAO;

    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        try {
            Long totalSalesCount = ventaDAO.countVentas();
            Long totalProducts = productoDAO.count();
            Long totalCustomers = clienteDAO.count();
            BigDecimal revenue = ventaDAO.sumTotalVentas();

            Map<String, Object> map = new HashMap<>();
            map.put("totalSales", totalSalesCount == null ? 0 : totalSalesCount);
            map.put("totalProducts", totalProducts == null ? 0 : totalProducts);
            map.put("totalCustomers", totalCustomers == null ? 0 : totalCustomers);
            map.put("revenue", revenue == null ? BigDecimal.ZERO : revenue);

            return ResponseEntity.ok(map);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al obtener stats: " + e.getMessage());
        }
    }

    @GetMapping("/activities")
    public ResponseEntity<?> getRecentActivities() {
        try {

            List<Venta> ultimasVentas = ventaDAO.findAllOrderByFechaDesc()
                                                .stream().limit(10).collect(Collectors.toList());

            List<Map<String, Object>> activities = new ArrayList<>();
            for (Venta v : ultimasVentas) {
                Map<String,Object> a = new HashMap<>();
                a.put("id", v.getIdVenta());
                a.put("action", "Venta registrada");
                a.put("time", v.getFechaVenta());
                a.put("type","sale");
                a.put("total", v.getTotal());
                activities.add(a);
            }

            return ResponseEntity.ok(activities);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al obtener activities: " + e.getMessage());
        }
    }
}
