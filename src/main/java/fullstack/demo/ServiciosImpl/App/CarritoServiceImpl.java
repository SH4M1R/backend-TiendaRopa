package fullstack.demo.ServiciosImpl.App;

import fullstack.demo.DAO.ProductoDAO;
import fullstack.demo.DAO.App.CarritoDAO;
import fullstack.demo.DAO.App.UsuarioDAO;
import fullstack.demo.Entidad.App.Carrito;
import fullstack.demo.Entidad.Producto;
import fullstack.demo.Entidad.App.Usuario;
import fullstack.demo.Servicios.App.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CarritoServiceImpl implements CarritoService {

    @Autowired
    private CarritoDAO carritoDAO;

    @Autowired
    private ProductoDAO productoDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Override
    @Transactional
    public Carrito agregarOActualizarProducto(Integer usuarioId, Integer productoId, Integer cantidad) {
        if (cantidad <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor a cero.");
        }

        Usuario usuario = usuarioDAO.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));

        Producto producto = productoDAO.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productoId));

        if (producto.getStock() <= 0) {
            throw new RuntimeException("Producto agotado. Stock disponible: 0");
        }

        Optional<Carrito> carritoItemOpt = carritoDAO.findByUsuarioAndProductoIdProducto(usuario, productoId);
        Carrito carritoItem;

        if (carritoItemOpt.isPresent()) {
            carritoItem = carritoItemOpt.get();
            int nuevaCantidad = carritoItem.getCantidad() + cantidad;

            if (nuevaCantidad > producto.getStock()) {
                nuevaCantidad = producto.getStock();
            }

            carritoItem.setCantidad(nuevaCantidad);
        } else {
            int cantidadFinal = Math.min(cantidad, producto.getStock());

            carritoItem = new Carrito();
            carritoItem.setUsuario(usuario);
            carritoItem.setProducto(producto);
            carritoItem.setCantidad(cantidadFinal);
        }

        return carritoDAO.save(carritoItem);
    }

    @Override
    public List<Carrito> obtenerCarritoPorUsuario(Integer usuarioId) {
        Usuario usuario = usuarioDAO.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));
        return carritoDAO.findByUsuario(usuario);
    }

    @Override
    public void eliminarItem(Integer itemId) {
        carritoDAO.deleteById(itemId);
    }

    @Override
    public BigDecimal calcularTotal(Integer usuarioId) {
        List<Carrito> items = obtenerCarritoPorUsuario(usuarioId);
        BigDecimal total = BigDecimal.ZERO;
        for (Carrito item : items) {
            BigDecimal subtotal = item.getProducto().getPrecioVenta()
                    .multiply(new BigDecimal(item.getCantidad()));
            total = total.add(subtotal);
        }
        return total;
    }

    @Override
    @Transactional
    public void vaciarCarrito(Integer usuarioId) {
        Usuario usuario = usuarioDAO.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));
        List<Carrito> items = carritoDAO.findByUsuario(usuario);
        carritoDAO.deleteAll(items);
    }
}
