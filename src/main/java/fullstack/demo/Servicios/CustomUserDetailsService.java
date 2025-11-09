package fullstack.demo.Servicios;

import fullstack.demo.DAO.EmpleadoDAO;
import fullstack.demo.Entidad.Empleado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private EmpleadoDAO empleadoDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Empleado empleado = empleadoDAO.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return User.builder()
                .username(empleado.getUsername())
                .password(empleado.getContrasena())
                .roles(empleado.getRol().getRol())
                .build();
    }
}