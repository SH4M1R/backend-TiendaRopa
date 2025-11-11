package fullstack.demo.Servicios;

import fullstack.demo.Entidad.Compra;
import java.util.List;
import java.util.Optional;

public interface CompraService {
    List<Compra> obtenerTodasLasCompras();
    Optional<Compra> obtenerCompraPorId(Integer id);
    Compra crearCompra(Compra compra);
    Compra actualizarCompra(Integer id, Compra compra);
    void eliminarCompra(Integer id);
    List<Compra> obtenerComprasPorProveedor(Integer proveedorId);
    List<Compra> obtenerComprasPorEmpleado(Integer empleadoId);
}