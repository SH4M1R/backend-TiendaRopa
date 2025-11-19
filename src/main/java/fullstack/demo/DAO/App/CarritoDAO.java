package fullstack.demo.DAO.App;

import org.springframework.data.jpa.repository.JpaRepository;
import fullstack.demo.Entidad.App.Carrito;
import fullstack.demo.Entidad.App.Usuario;
import java.util.List;
import java.util.Optional;

public interface CarritoDAO extends JpaRepository<Carrito, Integer>{
    List<Carrito> findByUsuario(Usuario usuario);
    Optional<Carrito> findByUsuarioAndProductoIdProducto(Usuario usuario, Integer productoId);
}