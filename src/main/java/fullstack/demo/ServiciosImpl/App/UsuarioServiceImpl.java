package fullstack.demo.ServiciosImpl.App;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fullstack.demo.DAO.App.UsuarioDAO;
import fullstack.demo.DAO.Intranet.EmpleadoDAO;
import fullstack.demo.Entidad.App.Usuario;
import fullstack.demo.Entidad.Intranet.Empleado;
import fullstack.demo.Servicios.App.EmailService;
import fullstack.demo.Servicios.App.UsuarioService;
import fullstack.demo.Utils.App.CodeGenerator;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmpleadoDAO empleadoDAO;

    @Override
    public Usuario registrarUsuario(Usuario usuario) {
        return usuarioDAO.save(usuario);
    }

    @Override
    public boolean existeCorreo(String correo) {
        return usuarioDAO.findAll()
                .stream()
                .anyMatch(u -> u.getCorreo().equalsIgnoreCase(correo));
    }

    @Override
    public Usuario buscarPorCorreo(String correo) {
        return usuarioDAO.findAll()
                .stream()
                .filter(u -> u.getCorreo().equalsIgnoreCase(correo))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Usuario login(String correo, String contrasena) {
        return usuarioDAO.findByCorreoAndContrasena(correo, contrasena);
    }

    @Override
    public Empleado loginEmpleado(String username, String contrasena) {
        return empleadoDAO.findByUsernameAndContrasena(username, contrasena);
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) { 
        return usuarioDAO.save(usuario); 
    }

    @Override
    public void eliminarUsuario(Integer idUsuario) { 
        usuarioDAO.deleteById(idUsuario); 
    }

    @Override
    public Usuario generarYGuardarCodigo(String correo) {
        Usuario usuario = buscarPorCorreo(correo); 
        
        if (usuario == null) {
            return null;
        }

        String code = CodeGenerator.generateSixDigitCode();
        LocalDateTime expiration = LocalDateTime.now().plusMinutes(10);

        usuario.setCodigoReset(code);
        usuario.setCodigoExpira(expiration);
        usuarioDAO.save(usuario);

        emailService.CodigoCorreo(correo, code);

        return usuario;
    }

    @Override
    public Usuario verificarCodigoYResetearContrasena(String correo, String codigo, String nuevaContrasena) {
        Usuario usuario = buscarPorCorreo(correo);

        if (usuario == null || usuario.getCodigoReset() == null || usuario.getCodigoExpira() == null) {
            return null;
        }

        if (!usuario.getCodigoReset().equals(codigo)) {
            return null;
        }

        if (usuario.getCodigoExpira().isBefore(LocalDateTime.now())) {
            usuario.setCodigoReset(null);
            usuario.setCodigoExpira(null);
            usuarioDAO.save(usuario);
            return null;
        }

        // **Aquí se asigna la nueva contraseña**
        usuario.setContrasena(nuevaContrasena);

        // Limpiamos el código y la fecha de expiración
        usuario.setCodigoReset(null);
        usuario.setCodigoExpira(null);
        
        return usuarioDAO.save(usuario);
    }
}
