package fullstack.demo.RestControl.Intranet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fullstack.demo.Entidad.Intranet.Venta;
import fullstack.demo.DAO.VentaDAO;
import fullstack.demo.DTO.Intranet.VentaRequest;
import fullstack.demo.ServiciosImpl.VentaServiceImpl;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "http://localhost:7500")
public class VentaControlador {

    @Autowired
    private VentaServiceImpl ventaService;

    @Autowired
    private VentaDAO ventaDAO;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarVenta(@RequestBody VentaRequest request) {
        try {
            Venta ventaRegistrada = ventaService.registrarVenta(request);
            return ResponseEntity.ok(ventaRegistrada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error al registrar la venta: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error inesperado: " + e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarVentas() {
        try {
            List<Venta> ventas = ventaDAO.findAll();
            return ResponseEntity.ok(ventas);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al listar ventas: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerVentaPorId(@PathVariable Integer id) {
        try {
            Optional<Venta> ventaOpt = ventaDAO.findById(id);
            if (ventaOpt.isPresent()) {
                Venta venta = ventaOpt.get();
                return ResponseEntity.ok(venta);
            } else {
                return ResponseEntity.status(404).body("Venta no encontrada con ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al obtener la venta: " + e.getMessage());
        }
    }
}
