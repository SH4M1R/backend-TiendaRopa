package fullstack.demo.Servicios.App;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(final JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void CodigoCorreo(String toEmail, String code) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom("jpshaiz123@gmail.com");
        mensaje.setTo(toEmail);
        mensaje.setSubject("Código de Recuperación de Contraseña");
        mensaje.setText("Hola,\n\n"
                      + "Tu código de 6 dígitos para resetear tu contraseña es: \n\n"
                      + ">> " + code + " <<\n\n"
                      + "Este código expirará en 10 minutos."
                      + "\n\nSaludos.");
        mailSender.send(mensaje);
    }
}