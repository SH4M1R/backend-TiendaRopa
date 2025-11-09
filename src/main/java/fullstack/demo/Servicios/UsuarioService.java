package fullstack.demo.Servicios;

import fullstack.demo.DAO.UsuarioDAO;
import fullstack.demo.DTO.UsuarioDTO;

import fullstack.demo.Entidad.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void registrarUsuario(UsuarioDTO usuarioDTO) {
        // Validar si ya existe un usuario con ese username
        if (usuarioDAO.existsByUsername(usuarioDTO.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }
        
        // Validar si ya existe un usuario con ese email
        if (usuarioDAO.existsByEmail(usuarioDTO.getEmail())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        // Crear un nuevo usuario y asignar los datos
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(usuarioDTO.getUsername());
        nuevoUsuario.setNombres(usuarioDTO.getNombres());
        nuevoUsuario.setApellidos(usuarioDTO.getApellidos());
        nuevoUsuario.setEmail(usuarioDTO.getEmail());
        nuevoUsuario.setTelefono(usuarioDTO.getTelefono());

        // Encriptar la contraseña antes de guardarla
        nuevoUsuario.setContrasena(passwordEncoder.encode(usuarioDTO.getContrasena()));
        
        // Asignar método 2FA si está presente
        // if (usuarioDTO.getMetodo2FA() != null && !usuarioDTO.getMetodo2FA().isEmpty()) {
        //     nuevoUsuario.setMetodo2FA(usuarioDTO.getMetodo2FA());
        // }

        // Guardar el usuario
        usuarioDAO.save(nuevoUsuario);
    }

    public Usuario autenticarUsuario(String username, String contrasena) {
        // Intentar buscar por username
        Usuario usuario = usuarioDAO.findByUsername(username).orElse(null);

        // Si no lo encuentra por username, intentar por email (permitir login con email)
        if (usuario == null) {
            usuario = usuarioDAO.findByEmail(username).orElse(null);
        }

        if (usuario == null) {
            return null;
        }

        // Verificar si la cuenta está verificada
        if (!usuario.getVerificado()) {
            throw new RuntimeException("La cuenta no está verificada. Por favor, verifica tu cuenta primero.");
        }

        // Comparar la contraseña en texto plano con la almacenada (BCrypt)
        if (passwordEncoder.matches(contrasena, usuario.getContrasena())) {
            return usuario;
        }

        return null;
    }
}