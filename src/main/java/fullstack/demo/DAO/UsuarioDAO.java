
package fullstack.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import fullstack.demo.Entidad.Usuario;
import java.util.Optional;

public interface UsuarioDAO extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findByEmail(String email);
	Optional<Usuario> findByUsername(String username);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
}
//package fullstack.demo.DAO;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import fullstack.demo.Entidad.Usuario;
//
//import java.util.Optional;
//
//public interface UsuarioDAO extends JpaRepository<Usuario, Integer> {
//	Optional<Usuario> findByEmail(String email);
//	Optional<Usuario> findByTelefono(String telefono);
//	Optional<Usuario> findByUsername(String username);
//
//	boolean existsByEmail(String email);
//	boolean existsByUsername(String username);
//}
