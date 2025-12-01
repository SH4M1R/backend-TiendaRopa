package fullstack.demo.Servicios.App;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface StripeService {

    PaymentIntent crearIntencionDePago(long amount, String currency) throws StripeException;
    void confirmarPago(String paymentIntentId) throws StripeException;
}