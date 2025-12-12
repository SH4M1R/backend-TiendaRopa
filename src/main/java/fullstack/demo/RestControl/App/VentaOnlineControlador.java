package fullstack.demo.RestControl.App;

import fullstack.demo.Entidad.App.VentaOnline;
import fullstack.demo.Servicios.App.VentaOnlineService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pago")
@CrossOrigin(origins = {"http://localhost:8000"})
public class VentaOnlineControlador {

    @Autowired
    private VentaOnlineService ventaOnlineService;

    @PostMapping("/procesar")
    public VentaOnline procesarPago(
            @RequestParam Integer usuarioId,
            @RequestParam String paymentIntentId
    ) throws StripeException {

        VentaOnline venta = ventaOnlineService.procesarVenta(usuarioId, paymentIntentId);
        venta.setEstado("PENDIENTE");  // Asegura que toda venta nueva comienza pendiente
        return venta;
    }

    // NUEVO: LISTAR TODAS LAS VENTAS (para delivery)
    @GetMapping("/listar")
    public List<VentaOnline> listarVentas() {
        return ventaOnlineService.listarVentas();
    }

    @PutMapping("/estado/{id}")
    public VentaOnline actualizarEstado(
            @PathVariable Integer id,
            @RequestParam String estado
    ) {
        return ventaOnlineService.actualizarEstado(id, estado);
    }

}
