package fullstack.demo.RestControl.App;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fullstack.demo.Entidad.App.Carrito;
import fullstack.demo.Servicios.App.CarritoService;

@RestController
@RequestMapping("/api/carrito")
@CrossOrigin(origins = {"http://localhost:8000", "http://localhost:7000"})
public class CarritoControlador {

    @Autowired
    private CarritoService carritoService;

    public record CarritoRequest(Integer usuarioId, Integer productoId, Integer cantidad) {}

    @PostMapping("/agregar")
    public ResponseEntity<?> agregar(@RequestBody CarritoRequest request) {
        try {
            Carrito item = carritoService.agregarOActualizarProducto(
                    request.usuarioId,
                    request.productoId,
                    request.cantidad
            );
            return ResponseEntity.ok(item);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/eliminar/{itemId}")
    public ResponseEntity<?> eliminar(@PathVariable Integer itemId) {
        carritoService.eliminarItem(itemId);
        return ResponseEntity.ok(Map.of("mensaje", "Item eliminado"));
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<?> obtener(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(carritoService.obtenerCarritoPorUsuario(usuarioId));
    }

    
}
