package fullstack.demo.Servicios.App;

import fullstack.demo.Entidad.App.VentaOnline;
import com.stripe.exception.StripeException;

public interface VentaOnlineService {
    VentaOnline procesarVenta(Integer usuarioId, String paymentIntentId) throws StripeException;
    VentaOnline procesarVentaOffline(Integer usuarioId, String metodoPago);
}