package fullstack.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import fullstack.demo.Entidad.CodigoVerificacion;
import java.util.Optional;

public interface CodigoVerificacionDAO extends JpaRepository<CodigoVerificacion, Long> {
    Optional<CodigoVerificacion> findByEmailAndCodigo(String email, String codigo);

    @Transactional // IMPORTANTE: Necesario para operaciones de eliminación
    void deleteByEmail(String email);
}


