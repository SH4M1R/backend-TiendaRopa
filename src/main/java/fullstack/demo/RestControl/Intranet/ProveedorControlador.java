package fullstack.demo.RestControl.Intranet;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fullstack.demo.Entidad.Intranet.Proveedor;
import fullstack.demo.Servicios.Intranet.ProveedorService;

@RestController
@RequestMapping("/api/proveedores")
@CrossOrigin(origins = "http://localhost:7500")
public class ProveedorControlador {
@Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public List<Proveedor> listarProveedores() {
        return proveedorService.listarProveedores();
    }

    @GetMapping("/{id}")
    public Proveedor obtenerProveedorPorId(@PathVariable Integer id) {
        return proveedorService.obtenerProveedorPorId(id);
    }

    @PostMapping
    public Proveedor crearProveedor(@RequestBody Proveedor Proveedor) {
        return proveedorService.crearProveedor(Proveedor);
    }

    @PutMapping("/{id}")
    public Proveedor actualizarProveedor(@PathVariable Integer id, @RequestBody Proveedor Proveedor) {
        Proveedor.setIdProveedor(id);
        return proveedorService.actualizarProveedor(Proveedor);
    }

    @DeleteMapping("/{id}")
    public void eliminarProveedor(@PathVariable Integer id) {
        proveedorService.eliminarProveedor(id);
    }
}