package fullstack.demo.ServiciosImpl;


import fullstack.demo.DAO.UsuarioDAO;
import fullstack.demo.Entidad.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioDAO.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el email: " + email));

        // Convierte tu entidad Usuario en un objeto User de Spring Security
        return User.builder()
                .username(usuario.getEmail()) // o usuario.getUsername() si usas nombre de usuario
                .password(usuario.getContrasena()) // debe estar encriptada
                .roles("USER") // o los roles que correspondan
                .build();
    }
}
