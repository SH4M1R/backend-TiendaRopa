package fullstack.demo.Entidad;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class EmpleadoDetails implements UserDetails {

    private final Empleado empleado;

    public EmpleadoDetails(Empleado empleado) {
        this.empleado = empleado;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + empleado.getRol().getRol()));
    }

    @Override
    public String getPassword() {
        return empleado.getContrasena();
    }

    @Override
    public String getUsername() {
        return empleado.getUsername();
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}