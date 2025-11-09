package fullstack.demo.Servicios;

import fullstack.demo.DAO.CodigoDAO;
import fullstack.demo.DAO.UsuarioDAO;
import fullstack.demo.Entidad.CodigoVerificacion;
import fullstack.demo.Entidad.Usuario;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;



@Service
public abstract class AuthService {

    @Autowired private UsuarioDAO usuarioDAO;
    @Autowired private CodigoDAO codigoDAO;
    @Autowired private EmailService emailService;

    public void registrar(Usuario usuario) {
        usuario.setVerificado(false);
        usuarioDAO.save(usuario);

        String codigo = String.format("%06d", new Random().nextInt(999999));
        CodigoVerificacion cv = new CodigoVerificacion();
        cv.setEmail(usuario.getEmail());
        cv.setCodigo(codigo);
        cv.setExpiracion(LocalDateTime.now().plusMinutes(5));
        codigoDAO.save(cv);

        emailService.enviarCodigo(usuario.getEmail(), codigo);
    }

    public boolean verificar(String email, String codigo) {
        Optional<CodigoVerificacion> opt = codigoDAO.findByEmail(email);
        if (opt.isEmpty()) return false;

        CodigoVerificacion cv = opt.get();
        if (cv.getCodigo().equals(codigo) && cv.getExpiracion().isAfter(LocalDateTime.now())) {
            Usuario u = usuarioDAO.findByEmail(email).orElseThrow();
            u.setVerificado(true);
            usuarioDAO.save(u);
            codigoDAO.delete(cv);
            return true;
        }
        return false;
    }

    public abstract void registrarUsuario(Usuario usuario) throws MessagingException;

    public abstract boolean verificarCodigo(String identificador, String codigo);
}

