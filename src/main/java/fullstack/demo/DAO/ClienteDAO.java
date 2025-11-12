package fullstack.demo.DAO;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import fullstack.demo.Entidad.Cliente;

public interface ClienteDAO extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByNombre(String nombre);
    Optional<Cliente> findByDocumento(Integer documento);
}