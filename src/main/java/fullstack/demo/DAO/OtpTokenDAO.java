package fullstack.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import fullstack.demo.Entidad.OtpToken;
import java.util.Optional;
import java.util.List;

public interface OtpTokenDAO extends JpaRepository<OtpToken, Long> {
    Optional<OtpToken> findByEmailAndCodigoAndUsado(String email, String codigo, boolean usado);
    List<OtpToken> findByEmailAndUsado(String email, boolean usado);
}
