package fullstack.demo.RestControl.App;

import fullstack.demo.Servicios.App.CarritoService;
import fullstack.demo.Servicios.App.StripeService;
import fullstack.demo.Servicios.App.VentaOnlineService;
import fullstack.demo.Entidad.App.VentaOnline;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/pago")
@CrossOrigin(origins = {"http://localhost:8000"})
public class PagoControlador {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private StripeService stripeService;

    @Autowired
    private VentaOnlineService ventaOnlineService;

    public record PaymentIntentResponse(String clientSecret, Long amount, String currency) {}

    public record PaymentRequest(Integer usuarioId, String paymentIntentId, String metodoPago) {}

    @PostMapping("/crear-intencion-pago/{usuarioId}")
    public ResponseEntity<?> crearIntencionPago(@PathVariable Integer usuarioId) {
        try {
            BigDecimal totalDecimal = carritoService.calcularTotal(usuarioId);
            long amountInCents = totalDecimal.multiply(new BigDecimal("100")).longValue();

            if (amountInCents <= 0) {
                return ResponseEntity.badRequest()
                        .body(Map.of("mensaje", "El total de la compra debe ser positivo."));
            }

            PaymentIntent paymentIntent = stripeService.crearIntencionDePago(amountInCents, "pen");

            PaymentIntentResponse response = new PaymentIntentResponse(
                    paymentIntent.getClientSecret(),
                    paymentIntent.getAmount(),
                    paymentIntent.getCurrency()
            );

            return ResponseEntity.ok(response);

        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error con Stripe: " + e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", e.getMessage()));
        }
    }

    @PostMapping("/confirmar-venta")
    public ResponseEntity<?> confirmarVenta(@RequestBody PaymentRequest request) {
        try {
            VentaOnline venta;

            switch (request.metodoPago.toLowerCase()) {
                case "stripe":
                    venta = ventaOnlineService.procesarVenta(request.usuarioId, request.paymentIntentId);
                    venta.setMetodoPago("stripe");
                    break;
                case "efectivo":
                case "transferencia":
                    venta = ventaOnlineService.procesarVentaOffline(request.usuarioId, request.metodoPago);
                    break;
                default:
                    return ResponseEntity.badRequest()
                            .body(Map.of("mensaje", "Método de pago no válido"));
            }

            carritoService.vaciarCarrito(request.usuarioId);

            Map<String, Object> result = new HashMap<>();
            result.put("idVenta", venta.getIdVentaOnline());
            result.put("total", venta.getTotal());
            result.put("metodoPago", venta.getMetodoPago());
            result.put("fecha", venta.getFechaVenta());

            return ResponseEntity.ok(result);

        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("mensaje", "Fallo el pago: " + e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", e.getMessage()));
        }
    }
}