
package fullstack.demo.ServiciosImpl;

import fullstack.demo.DTO.RegisterDTO;
import fullstack.demo.Entidad.Usuario;
import fullstack.demo.DAO.UsuarioDAO;
import fullstack.demo.Servicios.AuthService;
import fullstack.demo.Servicios.VerificacionService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioDAO usuarioDAO;
    //    private final EmailService emailService;
    private final VerificacionService verificacionService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String registrarUsuario(Usuario usuario) throws MessagingException {
        return "";
    }

    @SneakyThrows
    @Override
    public void registrarUsuario(RegisterDTO registerDTO) {
        if (usuarioDAO.findByUsername(registerDTO.getUsername()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }

        if (usuarioDAO.findByEmail(registerDTO.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(registerDTO.getUsername());
        usuario.setContrasena(passwordEncoder.encode(registerDTO.getContrasena()));
        usuario.setEmail(registerDTO.getEmail());
        usuario.setNombres(registerDTO.getNombres());
        usuario.setApellidos(registerDTO.getApellidos());
        usuario.setVerificado(false);

        usuarioDAO.save(usuario);

        // Genera y envía el código
        verificacionService.enviarCodigo(registerDTO.getEmail());
    }

    @Override
    public boolean verificarCodigo(String identificador, String codigo, String metodo) {
        return false;
    }

    @Override
    public void reenviarCodigo(String identificador, String metodo) throws MessagingException {

    }
}
