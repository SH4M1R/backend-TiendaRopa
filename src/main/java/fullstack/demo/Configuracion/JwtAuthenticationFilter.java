package fullstack.demo.Configuracion;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
protected void doFilterInternal(HttpServletRequest request,
                                HttpServletResponse response,
                                FilterChain filterChain)
        throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");
    String requestURI = request.getRequestURI();
    // --- DIAGNÓSTICO ---
    System.out.println("------------------------------------------------");
    System.out.println("Petición entrante: " + request.getMethod() + " " + requestURI);
    
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        System.out.println("No hay header Authorization o no empieza con Bearer");
        filterChain.doFilter(request, response);
        return;
    }
    String token = authHeader.substring(7);
    System.out.println("🔑 Token recibido: " + token.substring(0, Math.min(token.length(), 10)) + "...");
    String username = null;
    try {
        username = jwtUtil.extractUsername(token);
        System.out.println("Usuario extraído del token: " + username);
    } catch (Exception e) {
        System.out.println("Error al extraer usuario: " + e.getMessage());
    }
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        // Imprimir roles que Spring espera vs los que tiene el usuario
        System.out.println("Roles en BD para " + username + ": " + userDetails.getAuthorities());

        if (jwtUtil.isTokenValid(token, userDetails)) {
            System.out.println("Token VÁLIDO. Autenticando usuario...");
            // ... resto de tu lógica de autenticación ...
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        } else {
            System.out.println("Token INVÁLIDO según jwtUtil.isTokenValid()");
        }
    }
    System.out.println("------------------------------------------------");
    filterChain.doFilter(request, response);
}
}
