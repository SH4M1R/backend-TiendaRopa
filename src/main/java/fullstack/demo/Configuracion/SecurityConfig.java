package fullstack.demo.Configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Value("${cors.allowed-origins}")
private String frontendUrl;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            .authorizeHttpRequests(auth -> auth
                // =============================================================
                // 1. ZONA PÚBLICA (Acceso libre para todos)
                // =============================================================
                .requestMatchers("/").permitAll()

                .requestMatchers("/actuator/health").permitAll()

                .requestMatchers("/error").permitAll()

                .requestMatchers("/upload/**").permitAll()
                
                .requestMatchers("/api/auth/**").permitAll()
                
                .requestMatchers("/api/recuperacion/**").permitAll()

                .requestMatchers("/api/ventas/*/boleta").permitAll()

                .requestMatchers("/api/usuarios/**").permitAll()
                
                .requestMatchers("/api/catalogo/**").permitAll()
                

                .requestMatchers("/api/carrito/**").permitAll()
                .requestMatchers("/api/pago/**").permitAll()

                // =============================================================
                // 3. ZONA INTRANET (Protegida por Roles de Empleados)
                // =============================================================
                
                .requestMatchers(
                    "/api/empleados/**", 
                    "/api/roles/**"
                ).hasRole("ADMINISTRADOR")

                .requestMatchers(
                    "/api/ventas/**", 
                    "/api/dashboard/**"
                ).hasAnyRole("ADMINISTRADOR", "VENDEDOR")

                .requestMatchers(
                    "/api/proveedores/**", 
                    "/api/categorias/**"
                ).hasAnyRole("ADMINISTRADOR", "ALMACENISTA")

                .requestMatchers(
                    "/api/productos/**"
                ).hasAnyRole("ADMINISTRADOR", "VENDEDOR", "ALMACENISTA")

                // =============================================================
                // 4. RESTO DE PETICIONES (Bloqueo por defecto)
                // =============================================================
                .anyRequest().authenticated()
            )

            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Permite el origen configurado en application.properties
        configuration.setAllowedOriginPatterns(List.of(frontendUrl));

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
configuration.setAllowedHeaders(List.of("*"));
configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}