package fullstack.demo.Servicios;


import fullstack.demo.DAO.CodigoVerificacionDAO;
import fullstack.demo.Entidad.CodigoVerificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class VerificacionService {

    @Autowired
    private CodigoVerificacionDAO codigoVerificacionDAO;

    @Autowired
    private EmailService emailService;

    public void generarYEnviarCodigo(String email) {
        String codigo = String.format("%06d", new Random().nextInt(999999));
        LocalDateTime expiracion = LocalDateTime.now().plusMinutes(10);

        codigoVerificacionDAO.deleteByEmail(email); // limpia códigos antiguos
        CodigoVerificacion nuevo = new CodigoVerificacion(null, email, codigo, expiracion);
        codigoVerificacionDAO.save(nuevo);

        emailService.sendVerificationCode(email, codigo);
    }

    public boolean verificarCodigo(String email, String codigo) {
        var opt = codigoVerificacionDAO.findByEmail(email);
        if (opt.isEmpty()) return false;

        CodigoVerificacion cv = opt.get();
        if (cv.getExpiracion().isBefore(LocalDateTime.now())) return false;
        return cv.getCodigo().equals(codigo);
    }
}


