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
    try {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom("jpshaiz123@gmail.com");
        mensaje.setTo(toEmail);
        mensaje.setSubject("Código de Recuperación de Contraseña");
        mensaje.setText("Hola,\n\nTu código de 6 dígitos es: >> " + code + " <<");
        mailSender.send(mensaje);
        System.out.println("Correo enviado a " + toEmail);
    } catch (Exception e) {
        e.printStackTrace();
    }
}

}