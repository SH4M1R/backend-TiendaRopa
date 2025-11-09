package fullstack.demo.ServiciosImpl;



// import fullstack.demo.DAO.CodigoDAO;
import fullstack.demo.DAO.UsuarioDAO;
import fullstack.demo.Entidad.Usuario;
import fullstack.demo.Servicios.AuthService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl extends AuthService {

    private final UsuarioDAO usuarioDAO;
    // private final CodigoDAO codigoDAO;
    private final EmailServiceImpl emailService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public void registrarUsuario(Usuario usuario) throws MessagingException {
        usuario.setContrasena(encoder.encode(usuario.getContrasena()));
        usuario.setVerificado(false);
        usuarioDAO.save(usuario);

        emailService.generarYEnviarCodigo(usuario.getEmail());
    }

    @Override
    public boolean verificarCodigo(String identificador, String codigo) {
        return false;
    }

    public void reenviarCodigo(String email) {
    }
}

