package fullstack.demo.Servicios;

import jakarta.mail.MessagingException;

//public interface EmailService {
//    String generarYEnviarCodigo(String email) throws MessagingException;
//
//    void enviarCorreoCodigo(String email, String codigo);
//}


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationCode(String toEmail, String code) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@tuapp.com");
            message.setTo(toEmail);
            message.setSubject("Código de Verificación 2FA");
            message.setText(
                    "Tu código de verificación es: " + code + "\n\n" +
                            "Este código expirará en 10 minutos.\n\n" +
                            "Si no solicitaste este código, por favor ignora este mensaje."
            );

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar el email: " + e.getMessage());
        }
    }
}