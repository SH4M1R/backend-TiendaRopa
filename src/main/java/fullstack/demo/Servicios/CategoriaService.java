package fullstack.demo.Servicios;

import java.util.List;
import fullstack.demo.Entidad.Categoria;

public interface CategoriaService {
    List <Categoria> listarCategorias();
    Categoria crearCategoria(Categoria categoria);
    Categoria obtenerCategoriaPorId(Integer idCategoria);
    Categoria actualizarCategoria(Categoria categoria);
    void eliminarCategoria(Integer idCategoria);
}
