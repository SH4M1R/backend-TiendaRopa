//package fullstack.demo.ServiciosImpl;
//
//import fullstack.demo.DAO.CodigoVerificacionDAO;
//import fullstack.demo.Entidad.CodigoVerificacion;
//import fullstack.demo.Servicios.VerificacionService;
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import lombok.RequiredArgsConstructor;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//import java.util.Random;
//
//@Service
//@RequiredArgsConstructor
//public class VerificacionServiceImpl implements VerificacionService {
//
//    private final CodigoVerificacionDAO codigoVerificacionDAO;
//    private final JavaMailSender mailSender;
//
//    @Override
//    public void enviarCodigo(String email) throws MessagingException {
//        String codigo = String.format("%06d", new Random().nextInt(999999));
//        LocalDateTime expiracion = LocalDateTime.now().plusMinutes(5);
//
//        CodigoVerificacion cv = new CodigoVerificacion();
//        cv.setEmail(email);
//        cv.setCodigo(codigo);
//        cv.setMetodo("email");
//        cv.setExpiracion(expiracion);
//        codigoVerificacionDAO.save(cv);
//
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//        helper.setTo(email);
//        helper.setSubject("Código de verificación - Rapimoney");
//        helper.setText("<h3>Tu código es: <b>" + codigo + "</b></h3><p>Expira en 5 minutos.</p>", true);
//        mailSender.send(message);
//    }
//
//    @Override
//    public boolean verificarCodigo(String email, String codigo) {
//        Optional<CodigoVerificacion> opt = codigoVerificacionDAO.findByEmailAndCodigo(email, codigo);
//
//        if (opt.isEmpty()) {
//            return false; // no encontrado
//        }
//
//        CodigoVerificacion cv = opt.get();
//        if (cv.getExpiracion().isBefore(LocalDateTime.now())) {
//            codigoVerificacionDAO.delete(cv); // elimina el expirado
//            return false;
//        }
//
//        codigoVerificacionDAO.delete(cv); // lo elimina una vez usado
//        return true;
//    }
//}
//
