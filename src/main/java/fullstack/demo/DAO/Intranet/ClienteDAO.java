package fullstack.demo.DAO.Intranet;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import fullstack.demo.Entidad.Intranet.Cliente;

public interface ClienteDAO extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByNombre(String nombre);
    Optional<Cliente> findByDocumento(Integer documento);
}