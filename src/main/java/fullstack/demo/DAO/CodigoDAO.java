package fullstack.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import fullstack.demo.Entidad.CodigoVerificacion;
import java.util.Optional;

public interface CodigoDAO extends JpaRepository<CodigoVerificacion, Long> {
    Optional<CodigoVerificacion> findByIdentificadorAndCodigo(String identificador, String codigo);
    Optional<CodigoVerificacion> findByIdentificador(String identificador);

    Optional<CodigoVerificacion> findByEmail(String email);
}

