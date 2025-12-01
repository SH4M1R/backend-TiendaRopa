package fullstack.demo.RestControl.App;

import fullstack.demo.Entidad.App.VentaOnline;
import fullstack.demo.Servicios.App.VentaOnlineService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pago")
@CrossOrigin(origins = {"http://localhost:8000",})
public class VentaOnlineControlador {

    @Autowired
    private VentaOnlineService ventaOnlineService;

    @PostMapping("/procesar")
    public VentaOnline procesarPago(
            @RequestParam Integer usuarioId,
            @RequestParam String paymentIntentId
    ) throws StripeException {

        return ventaOnlineService.procesarVenta(usuarioId, paymentIntentId);
    }
}
