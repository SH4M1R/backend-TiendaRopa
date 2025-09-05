package fullstack.demo.ServiciosImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fullstack.demo.DAO.CategoriaDAO;
import fullstack.demo.Entidad.Categoria;
import fullstack.demo.Servicios.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService{

    @Autowired
    private CategoriaDAO categoriaDAO;

    @Override
    public List<Categoria> listarCategorias() { return categoriaDAO.findAll();}

    @Override
    public Categoria obtenerCategoriaPorId (Integer idCategoria) { return categoriaDAO.findById(idCategoria).get();}

    @Override
    public Categoria crearCategoria(Categoria categoria) { return categoriaDAO.save(categoria);}

    @Override
    public Categoria actualizarCategoria(Categoria categoria) { return categoriaDAO.save(categoria);}

    @Override
    public void eliminarCategoria(Integer idCategoria) { categoriaDAO.deleteById(idCategoria); }
}
