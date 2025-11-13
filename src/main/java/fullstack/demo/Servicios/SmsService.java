package fullstack.demo.Servicios;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Value("${twilio.account_sid:}")
    private String accountSid;

    @Value("${twilio.auth_token:}")
    private String authToken;

    @Value("${twilio.phone_number:}")
    private String fromNumber;

    @PostConstruct
    public void init() {
        if (accountSid != null && !accountSid.isBlank() && authToken != null && !authToken.isBlank()) {
            Twilio.init(accountSid, authToken);
        }
    }

    public void enviarOtp(String telefono, String codigo) {
        if (accountSid == null || accountSid.isBlank() || authToken == null || authToken.isBlank() || fromNumber == null || fromNumber.isBlank()) {
            // Simulate SMS in development if Twilio not configured
            System.out.println("[SIMULATED SMS] To=" + telefono + " Code=" + codigo);
            return;
        }

        Message.creator(
                new PhoneNumber(telefono),
                new PhoneNumber(fromNumber),
                "Tu código de verificación es: " + codigo
        ).create();
    }

}
