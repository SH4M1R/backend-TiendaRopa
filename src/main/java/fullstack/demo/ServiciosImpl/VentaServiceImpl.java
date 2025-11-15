package fullstack.demo.ServiciosImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fullstack.demo.DAO.VentaDAO;
import fullstack.demo.DAO.DetalleVentaDAO;
import fullstack.demo.DAO.ProductoDAO;
import fullstack.demo.Entidad.Intranet.Cliente;
import fullstack.demo.Entidad.Intranet.DetalleVenta;
import fullstack.demo.Entidad.Intranet.Venta;
import fullstack.demo.Entidad.Producto;
import fullstack.demo.DTO.VentaRequest;
import fullstack.demo.Servicios.ClienteService;
import java.time.LocalDateTime;

@Service
public class VentaServiceImpl {

    @Autowired
    private VentaDAO ventaDAO;

    @Autowired
    private DetalleVentaDAO detalleDAO;

    @Autowired
    private ProductoDAO productoDAO;

    @Autowired
    private ClienteService clienteService;

    @Transactional
    public Venta registrarVenta(VentaRequest request) {

        // 1️⃣ Obtener o crear cliente
        Cliente cliente = clienteService.obtenerOCrearCliente(request.getCliente());

        // 2️⃣ Crear venta
        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setTotal(request.getTotal());
        venta.setFechaVenta(LocalDateTime.now());

        venta = ventaDAO.save(venta);

        // 3️⃣ Registrar detalles de venta y actualizar stock
        for (VentaRequest.DetalleProductoRequest det : request.getDetalles()) {
            Producto producto = productoDAO.findById(det.getProducto().getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + det.getProducto().getIdProducto()));

            if (producto.getStock() < det.getStock()) {
                throw new RuntimeException("Stock insuficiente para: " + producto.getProducto());
            }

            // Actualizar stock
            producto.setStock(producto.getStock() - det.getStock());
            productoDAO.save(producto);

            // Crear detalle
            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(venta);
            detalle.setProducto(producto);
            detalle.setStock(det.getStock());
            detalle.setSubtotal(det.getSubtotal());
            detalle.setMetodoPago(det.getMetodoPago());
            detalle.setMontoPagado(det.getMontoPagado());
            detalle.setVuelto(det.getVuelto());
            detalle.setCodigoIzipay(det.getCodigoIzipay());
            detalle.setNumeroTarjeta(det.getNumeroTarjeta());

            detalleDAO.save(detalle);
        }

        return venta;
    }
}
