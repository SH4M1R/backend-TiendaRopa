package fullstack.demo.Servicios;

import java.util.List;
import fullstack.demo.Entidad.Proveedor;

public interface ProveedorService {
    List<Proveedor> listarProveedores();
    Proveedor crearProveedor(Proveedor proveedor);
    Proveedor obtenerProveedorPorId(Integer idProveedor);
    Proveedor actualizarProveedor(Proveedor proveedor);
    void eliminarProveedor(Integer idProveedor);
}
