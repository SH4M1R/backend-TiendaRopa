package fullstack.demo.RestControl;

import fullstack.demo.Entidad.Compra;
import fullstack.demo.Servicios.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/compras")
@CrossOrigin(origins = "http://localhost:3000")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @GetMapping
    public List<Compra> obtenerTodasLasCompras() {
        return compraService.obtenerTodasLasCompras();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compra> obtenerCompraPorId(@PathVariable Integer id) {
        Optional<Compra> compra = compraService.obtenerCompraPorId(id);
        return compra.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Compra crearCompra(@RequestBody Compra compra) {
        return compraService.crearCompra(compra);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Compra> actualizarCompra(@PathVariable Integer id, @RequestBody Compra compra) {
        Compra compraActualizada = compraService.actualizarCompra(id, compra);
        return compraActualizada != null ? ResponseEntity.ok(compraActualizada) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCompra(@PathVariable Integer id) {
        compraService.eliminarCompra(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/proveedor/{proveedorId}")
    public List<Compra> obtenerComprasPorProveedor(@PathVariable Integer proveedorId) {
        return compraService.obtenerComprasPorProveedor(proveedorId);
    }

    @GetMapping("/empleado/{empleadoId}")
    public List<Compra> obtenerComprasPorEmpleado(@PathVariable Integer empleadoId) {
        return compraService.obtenerComprasPorEmpleado(empleadoId);
    }
}