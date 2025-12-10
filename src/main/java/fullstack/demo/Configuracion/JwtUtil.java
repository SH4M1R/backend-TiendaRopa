package fullstack.demo.Configuracion;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${security.jwt.secret}")
    private String secretKey;

    @Value("${security.jwt.expiration}")
    private long expirationTime;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // GENERACIÓN DEL TOKEN
    public String generarToken(String username, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // EXTRACCIÓN DE DATOS
    public String obtenerUsernameDelToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date obtenerFechaExpiracion(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = obtenerTodosLosClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims obtenerTodosLosClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // VALIDACIÓN DEL TOKEN
    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = obtenerUsernameDelToken(token);
        return (username.equals(userDetails.getUsername()) && !estaExpirado(token));
    }

    private boolean estaExpirado(String token) {
        return obtenerFechaExpiracion(token).before(new Date());
    }

    public String extractUsername(String token) {
        return obtenerUsernameDelToken(token);
    }
}