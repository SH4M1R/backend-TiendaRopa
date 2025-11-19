package fullstack.demo.Servicios.App;

import fullstack.demo.Entidad.App.Carrito;
import java.util.List;
import java.math.BigDecimal;

public interface CarritoService {

    Carrito agregarOActualizarProducto(Integer usuarioId, Integer productoId, Integer cantidad);

    List<Carrito> obtenerCarritoPorUsuario(Integer usuarioId);

    void eliminarItem(Integer itemId);

    BigDecimal calcularTotal(Integer usuarioId);

    void vaciarCarrito(Integer usuarioId);
}
