package fullstack.demo.ServiciosImpl;

import fullstack.demo.DAO.CodigoDAO;
import fullstack.demo.Entidad.CodigoVerificacion;
import fullstack.demo.Servicios.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

//@Service
//@RequiredArgsConstructor
//public class EmailServiceImpl extends EmailService {
//
//    private final JavaMailSender mailSender;
//    private final CodigoDAO codigoDAO;
//
//    @Override
//    public String generarYEnviarCodigo(String email) throws MessagingException {
//        String codigo = String.format("%06d", new Random().nextInt(999999));
//        LocalDateTime expiracion = LocalDateTime.now().plusMinutes(5);
//
//        // Guardar en BD
//        CodigoVerificacion cod = new CodigoVerificacion();
//        cod.setIdentificador(email);
//        cod.setCodigo(codigo);
//        cod.setMetodo("email");
//        cod.setExpiracion(expiracion);
//        codigoRepo.save(cod);
//
//        // Enviar correo
//        var message = mailSender.createMimeMessage();
//        var helper = new MimeMessageHelper(message, true);
//
//        helper.setTo(email);
//        helper.setSubject("Código de verificación - Rapimoney");
//        helper.setText("<h2>Tu código de verificación</h2>" +
//                "<p>Usa este código para verificar tu cuenta:</p>" +
//                "<h1 style='color:#4F46E5'>" + codigo + "</h1>" +
//                "<p>Válido por 5 minutos.</p>", true);
//
//        mailSender.send(message);
//
//        return codigo;
//    }
//}

@Service
@RequiredArgsConstructor
abstract class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final CodigoDAO codigoDAO;

    @Override
    public String generarYEnviarCodigo(String email) throws MessagingException {
        String codigo = String.format("%06d", new Random().nextInt(999999));
        LocalDateTime expiracion = LocalDateTime.now().plusMinutes(5);

        // Crear y enviar el correo
        var message = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(message, true);

        helper.setTo(email);
        helper.setSubject("Código de verificación - Rapimoney");
        helper.setText(
                "<h2>Tu código de verificación</h2>" +
                        "<p>Usa este código para verificar tu cuenta:</p>" +
                        "<h1 style='color:#4F46E5; font-size: 32px; letter-spacing: 2px;'>" + codigo + "</h1>" +
                        "<p>Válido por 5 minutos.</p>", true
        );

        // Guardar el código en la base de datos
        CodigoVerificacion cod = new CodigoVerificacion();
        cod.setIdentificador(email);
        cod.setCodigo(codigo);
        cod.setMetodo("email");
        cod.setExpiracion(expiracion);
        codigoDAO.save(cod);

        mailSender.send(message);

        return codigo;
    }
}

