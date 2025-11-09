package fullstack.demo.Configuracion;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import com.twilio.Twilio;




@Configuration
public class TwilioConfig {

    @Value("${twilio.account.sid:}")
    private String accountSid;

    @Value("${twilio.auth.token:}")
    private String authToken;

    @Value("${twilio.phone.number:}")
    private String phoneNumber;

    @Value("${twilio.enabled:false}")
    private boolean enabled;

    @PostConstruct
    public void initTwilio() {
        if (enabled && accountSid != null && !accountSid.isEmpty()
                && authToken != null && !authToken.isEmpty()) {
            Twilio.init(accountSid, authToken);
            System.out.println("✅ Twilio inicializado correctamente");
            System.out.println("📱 Número Twilio: " + phoneNumber);
        } else {
            System.out.println("⚠️ Twilio deshabilitado - usando modo mock");
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
