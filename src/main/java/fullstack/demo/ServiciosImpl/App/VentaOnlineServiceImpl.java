package fullstack.demo.ServiciosImpl.App;

import fullstack.demo.Servicios.App.StripeService;
import fullstack.demo.Servicios.App.VentaOnlineService;
import fullstack.demo.Servicios.App.CarritoService;
import fullstack.demo.DAO.App.CarritoDAO;
import fullstack.demo.DAO.App.DetalleVentaOnlineDAO;
import fullstack.demo.DAO.App.UsuarioDAO;
import fullstack.demo.DAO.App.VentaOnlineDAO;
import fullstack.demo.DAO.ProductoDAO;
import fullstack.demo.Entidad.App.VentaOnline;
import fullstack.demo.Entidad.App.Carrito;
import fullstack.demo.Entidad.App.DetalleVentaOnline;
import fullstack.demo.Entidad.App.Usuario;
import fullstack.demo.Entidad.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.stripe.exception.StripeException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VentaOnlineServiceImpl implements VentaOnlineService {

    @Autowired private VentaOnlineDAO ventaOnlineDAO;
    @Autowired private DetalleVentaOnlineDAO detalleVentaOnlineDAO;
    @Autowired private ProductoDAO productoDAO;
    @Autowired private CarritoDAO carritoDAO;
    @Autowired private UsuarioDAO usuarioDAO;
    @Autowired private StripeService stripeService;
    @Autowired private CarritoService carritoService;

    @Override
    @Transactional
    public VentaOnline procesarVenta(Integer usuarioId, String paymentIntentId) throws StripeException {
        Usuario usuario = usuarioDAO.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));

        List<Carrito> itemsCarrito = carritoDAO.findByUsuario(usuario);
        if (itemsCarrito.isEmpty()) {
            throw new RuntimeException("El carrito está vacío. No se puede procesar la venta.");
        }

        BigDecimal totalCompra = carritoService.calcularTotal(usuarioId);
        
        stripeService.confirmarPago(paymentIntentId);

        VentaOnline venta = new VentaOnline();
        venta.setUsuario(usuario);
        venta.setFechaVenta(LocalDateTime.now());
        venta.setTotal(totalCompra);
        venta.setMetodoPago("stripe");
        venta = ventaOnlineDAO.save(venta);

        for (Carrito item : itemsCarrito) {
            Producto producto = item.getProducto();
            Integer cantidadComprada = item.getCantidad();

            if (producto.getStock() < cantidadComprada) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getProducto() + ". Stock disponible: " + producto.getStock());
            }

            DetalleVentaOnline detalle = new DetalleVentaOnline();
            detalle.setVentaOnline(venta);
            detalle.setProducto(producto);
            detalle.setCantidad(cantidadComprada);
            
            BigDecimal subtotalItem = producto.getPrecioVenta().multiply(new BigDecimal(cantidadComprada));
            detalle.setSubtotal(subtotalItem);
            detalleVentaOnlineDAO.save(detalle);
            producto.setStock(producto.getStock() - cantidadComprada);
            productoDAO.save(producto);
        }

        carritoDAO.deleteAll(itemsCarrito);
        return venta;
    }

    @Override
@Transactional
public VentaOnline procesarVentaOffline(Integer usuarioId, String metodoPago) {
    Usuario usuario = usuarioDAO.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));

    List<Carrito> itemsCarrito = carritoDAO.findByUsuario(usuario);
    if (itemsCarrito.isEmpty()) {
        throw new RuntimeException("El carrito está vacío. No se puede procesar la venta.");
    }

    BigDecimal totalCompra = carritoService.calcularTotal(usuarioId);

    VentaOnline venta = new VentaOnline();
    venta.setUsuario(usuario);
    venta.setFechaVenta(LocalDateTime.now());
    venta.setTotal(totalCompra);
    venta.setMetodoPago(metodoPago);
    venta = ventaOnlineDAO.save(venta);

    for (Carrito item : itemsCarrito) {
        Producto producto = item.getProducto();
        Integer cantidadComprada = item.getCantidad();

        if (producto.getStock() < cantidadComprada) {
            throw new RuntimeException("Stock insuficiente para el producto: " + producto.getProducto() +
                                       ". Stock disponible: " + producto.getStock());
        }

        DetalleVentaOnline detalle = new DetalleVentaOnline();
        detalle.setVentaOnline(venta);
        detalle.setProducto(producto);
        detalle.setCantidad(cantidadComprada);
        detalle.setSubtotal(producto.getPrecioVenta().multiply(new BigDecimal(cantidadComprada)));
        detalleVentaOnlineDAO.save(detalle);

        producto.setStock(producto.getStock() - cantidadComprada);
        productoDAO.save(producto);
    }

    carritoDAO.deleteAll(itemsCarrito);
    return venta;
}

    @Override
    public List<VentaOnline> listarVentas() {
        return ventaOnlineDAO.findAll();
    }

    @Override
    @Transactional
    public VentaOnline actualizarEstado(Integer id, String estado) {
        VentaOnline venta = ventaOnlineDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        venta.setEstado(estado);
        return ventaOnlineDAO.save(venta);
    }

}