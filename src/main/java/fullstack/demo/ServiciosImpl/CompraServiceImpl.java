package fullstack.demo.ServiciosImpl;

import fullstack.demo.Entidad.Compra;
import fullstack.demo.DAO.CompraDAO;
import fullstack.demo.Servicios.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CompraServiceImpl implements CompraService {

    @Autowired
    private CompraDAO compraDAO;

    @Override
    public List<Compra> obtenerTodasLasCompras() {
        return compraDAO.findAll();
    }

    @Override
    public Optional<Compra> obtenerCompraPorId(Integer id) {
        return compraDAO.findById(id);
    }

    @Override
    public Compra crearCompra(Compra compra) {
        compra.setFechaCompra(LocalDateTime.now());
        return compraDAO.save(compra);
    }

    @Override
    public Compra actualizarCompra(Integer id, Compra compra) {
        if (compraDAO.existsById(id)) {
            compra.setIdCompra(id);
            return compraDAO.save(compra);
        }
        return null;
    }

    @Override
    public void eliminarCompra(Integer id) {
        compraDAO.deleteById(id);
    }

    @Override
    public List<Compra> obtenerComprasPorProveedor(Integer proveedorId) {
        return compraDAO.findByProveedorIdProveedor(proveedorId);
    }

    @Override
    public List<Compra> obtenerComprasPorEmpleado(Integer empleadoId) {
        return compraDAO.findByEmpleadoIdEmpleado(empleadoId);
    }
}