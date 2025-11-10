package fullstack.demo.Servicios;

import fullstack.demo.DAO.CodigoVerificacionDAO;
import fullstack.demo.Entidad.CodigoVerificacion;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;



import jakarta.mail.MessagingException;

public interface VerificacionService {

    void enviarCodigo(String email) throws MessagingException;

    boolean verificarCodigo(String email, String codigo);
}


//public interface VerificacionService {
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    @Autowired
//    private CodigoVerificacionDAO codigoVerificacionDAO;
//
//    public void enviarCodigo(String email) throws MessagingException {
//        String codigo = String.format("%06d", new Random().nextInt(999999));
//        LocalDateTime expiracion = LocalDateTime.now().plusMinutes(10);
//
//        codigoVerificacionDAO.deleteByEmail(email); // Limpia códigos anteriores
//
//        CodigoVerificacion cv = new CodigoVerificacion();
//        cv.setEmail(email);
//        cv.setCodigo(codigo);
//        cv.setExpiracion(expiracion);
//        codigoVerificacionDAO.save(cv);
//
//        SimpleMailMessage mensaje = new SimpleMailMessage();
//        mensaje.setTo(email);
//        mensaje.setSubject("Tu código de verificación");
//        mensaje.setText("Tu código de verificación es: " + codigo);
//        mailSender.send(mensaje);
//    }
//
//    public boolean verificarCodigo(String email, String codigo) {
//        Optional<CodigoVerificacion> resultado = codigoVerificacionDAO.findByEmailAndCodigo(email, codigo);
//        if (resultado.isPresent() && resultado.get().getExpiracion().isAfter(LocalDateTime.now())) {
//            codigoVerificacionDAO.deleteByEmail(email); // Código usado
//            return true;
//        }
//        return false;
//    }
//}
