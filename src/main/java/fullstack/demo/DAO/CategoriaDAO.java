package fullstack.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import fullstack.demo.Entidad.Categoria;

public interface CategoriaDAO extends JpaRepository<Categoria, Integer>{

}
