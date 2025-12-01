package fullstack.demo.ServiciosImpl.App;

import fullstack.demo.Servicios.App.StripeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;

@Service
public class StripeServiceImpl implements StripeService {

    @Value("${pasarela.stripe.secret-key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        if (secretKey == null || secretKey.trim().isEmpty()) {
            throw new RuntimeException("La clave secreta de Stripe no está configurada!");
        }
        Stripe.apiKey = secretKey;
    }

    @Override
    public PaymentIntent crearIntencionDePago(long amount, String currency) throws StripeException {
        PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder()
                .setAmount(amount) 
                .setCurrency(currency)
                .addPaymentMethodType("card")
                .build();

        return PaymentIntent.create(createParams);
    }

    @Override
    public void confirmarPago(String paymentIntentId) throws StripeException {
        PaymentIntent intent = PaymentIntent.retrieve(paymentIntentId);

        if (!"succeeded".equals(intent.getStatus())) {
            throw new RuntimeException(
                "El pago con ID " + paymentIntentId + " no se completó. Estado actual: " + intent.getStatus()
            );
        }
    }
}