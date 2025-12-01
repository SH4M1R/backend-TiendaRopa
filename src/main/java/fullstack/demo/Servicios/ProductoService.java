package fullstack.demo.Servicios;

import java.util.List;
import fullstack.demo.Entidad.Producto;

public interface ProductoService {
    List<Producto> listarProductos();
    Producto crearProducto(Producto producto);
    Producto obtenerProductoPorId(Integer idProducto);
    Producto actualizarProducto(Producto producto);
    void eliminarProducto(Integer idProducto);
}
