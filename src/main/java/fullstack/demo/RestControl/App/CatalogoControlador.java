package fullstack.demo.RestControl.App;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fullstack.demo.Entidad.Categoria;
import fullstack.demo.Entidad.Producto;
import fullstack.demo.Servicios.CategoriaService;
import fullstack.demo.Servicios.ProductoService;

@RestController
@RequestMapping("/api/catalogo")
@CrossOrigin(origins = "http://localhost:8000")
public class CatalogoControlador {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/productos")
    public List<Producto> listarProductos() {
        return productoService.listarProductos();
    }

    @GetMapping("/productos/categoria/{idCategoria}")
    public List<Producto> listarProductosPorCategoria(@PathVariable Integer idCategoria) {
        if (idCategoria == 0) {
            return productoService.listarProductos();
        }
        return productoService.listarProductos().stream()
                .filter(p -> p.getCategoria().getIdCategoria().equals(idCategoria))
                .toList();
    }

    @GetMapping("/categorias")
    public List<Categoria> listarCategorias() {
        return categoriaService.listarCategorias();
    }
}