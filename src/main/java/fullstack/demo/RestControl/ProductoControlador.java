package fullstack.demo.RestControl;

import fullstack.demo.Entidad.Producto;
import fullstack.demo.Servicios.ProductoService;
import fullstack.demo.Servicios.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "http://localhost:7500")
public class ProductoControlador {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UploadService uploadService;

    @GetMapping
    public List<Producto> listarProductos() { return productoService.listarProductos(); }

    @GetMapping("/{id}")
    public Producto obtenerProductoPorId(@PathVariable Integer id) { return productoService.obtenerProductoPorId(id);}

    @PostMapping(consumes = {"multipart/form-data"})
    public Producto crearProducto(
            @RequestPart("producto") Producto producto,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen
    ) {
        if (imagen != null && !imagen.isEmpty()) {
            String urlImagen = uploadService.saveUpload(imagen);
            producto.setImagen(urlImagen);
        }
        return productoService.crearProducto(producto);
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public Producto actualizarProducto(
            @PathVariable Integer id,
            @RequestPart("producto") Producto producto,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen
    ) {
        producto.setIdProducto(id);

        if (imagen != null && !imagen.isEmpty()) {
            String urlImagen = uploadService.saveUpload(imagen);
            producto.setImagen(urlImagen);
        }

        return productoService.actualizarProducto(producto);
    }
    
    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Integer id) {
        productoService.eliminarProducto(id);
    }
}