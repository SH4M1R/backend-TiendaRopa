package fullstack.demo.ServiciosImpl;

import fullstack.demo.Entidad.Usuario;
import fullstack.demo.DAO.UsuarioDAO;
import fullstack.demo.Servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Path rootLocation = Paths.get("upload");

    @Override
    public Usuario obtenerUsuarioPorId(Integer id) {
        return usuarioDAO.findById(id).orElse(null);
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) {
        Usuario usuarioExistente = usuarioDAO.findById(usuario.getIdUsuario()).orElse(null);
        if (usuarioExistente != null) {
            usuarioExistente.setNombre(usuario.getNombre());
            usuarioExistente.setCorreo(usuario.getCorreo());
            usuarioExistente.setDireccion(usuario.getDireccion());
            usuarioExistente.setTelefono(usuario.getTelefono());
            usuarioExistente.setTemaPreferido(usuario.getTemaPreferido());
            usuarioExistente.setIdioma(usuario.getIdioma());
            return usuarioDAO.save(usuarioExistente);
        }
        return null;
    }

    @Override
    public void cambiarPassword(Integer usuarioId, String nuevaPassword) {
        Usuario usuario = usuarioDAO.findById(usuarioId).orElse(null);
        if (usuario != null) {
            usuario.setContrasena(passwordEncoder.encode(nuevaPassword));
            usuarioDAO.save(usuario);
        }
    }

    @Override
    public String guardarAvatar(Integer usuarioId, MultipartFile file) {
        try {
            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }

            String fileExtension = getFileExtension(file.getOriginalFilename());
            String fileName = UUID.randomUUID() + fileExtension;
            
            Files.copy(file.getInputStream(), this.rootLocation.resolve(fileName));
            
            // Actualizar usuario con nueva imagen
            Usuario usuario = usuarioDAO.findById(usuarioId).orElse(null);
            if (usuario != null) {
                usuario.setAvatarUrl("/upload/" + fileName);
                usuarioDAO.save(usuario);
            }
            
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo: " + e.getMessage());
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) return ".jpg";
        int lastIndex = fileName.lastIndexOf(".");
        return lastIndex == -1 ? ".jpg" : fileName.substring(lastIndex);
    }

    // Nuevo método para actualizar configuraciones
    public Usuario actualizarConfiguracion(Integer usuarioId, String tema, String idioma) {
        Usuario usuario = usuarioDAO.findById(usuarioId).orElse(null);
        if (usuario != null) {
            usuario.setTemaPreferido(tema);
            usuario.setIdioma(idioma);
            return usuarioDAO.save(usuario);
        }
        return null;
    }
}