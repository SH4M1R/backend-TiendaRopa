package fullstack.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import fullstack.demo.Entidad.Cliente;

public interface ClienteDAO extends JpaRepository<Cliente, Integer> {

}
