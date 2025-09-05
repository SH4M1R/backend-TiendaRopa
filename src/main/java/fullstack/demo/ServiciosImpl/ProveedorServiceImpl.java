package fullstack.demo.ServiciosImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fullstack.demo.DAO.ProveedorDAO;
import fullstack.demo.Entidad.Proveedor;
import fullstack.demo.Servicios.ProveedorService;

@Service
public class ProveedorServiceImpl implements ProveedorService{

    @Autowired
    private ProveedorDAO proveedorDAO;

    @Override
    public List<Proveedor> listarProveedores() { return proveedorDAO.findAll();}

    @Override
    public Proveedor obtenerProveedorPorId(Integer idProveedor) {return proveedorDAO.findById(idProveedor).get();}

    @Override
    public Proveedor crearProveedor(Proveedor proveedor) { return proveedorDAO.save(proveedor); }

    @Override
    public Proveedor actualizarProveedor(Proveedor proveedor) { return proveedorDAO.save(proveedor); }

    @Override
    public void eliminarProveedor(Integer idProveedor) { proveedorDAO.deleteById(idProveedor); }

}
