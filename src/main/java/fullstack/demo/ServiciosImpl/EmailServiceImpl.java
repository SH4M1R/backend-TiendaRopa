package fullstack.demo.ServiciosImpl;

import fullstack.demo.Entidad.CodigoVerificacion;
import fullstack.demo.DAO.CodigoVerificacionDAO;
import fullstack.demo.Servicios.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final CodigoVerificacionDAO codigoVerificacionDAO;

    @Override
    public String generarYEnviarCodigo(String email) throws MessagingException {
        // Elimina código anterior del mismo email si existe
        codigoVerificacionDAO.deleteByEmail(email);

        String codigo = String.format("%06d", new Random().nextInt(999999));
        LocalDateTime expiracion = LocalDateTime.now().plusMinutes(5);

        CodigoVerificacion cod = new CodigoVerificacion();
        cod.setEmail(email);
        cod.setCodigo(codigo);
        cod.setMetodo("email");
        cod.setExpiracion(expiracion);
        codigoVerificacionDAO.save(cod);

        // Enviar correo
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setSubject("Código de verificación - Rapimoney");
        helper.setText(
                "<h2>Tu código de verificación</h2>" +
                        "<p>Usa este código para verificar tu cuenta:</p>" +
                        "<h1 style='color:#4F46E5'>" + codigo + "</h1>" +
                        "<p>Válido por 5 minutos.</p>",
                true
        );
        mailSender.send(message);

        return codigo;
    }

    @Override
    public void enviarCorreoCodigo(String email, String codigo) {

    }
}

