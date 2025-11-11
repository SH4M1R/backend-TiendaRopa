package fullstack.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import fullstack.demo.Entidad.Producto;

import java.util.List;

public interface ProductoDAO extends JpaRepository<Producto, Integer> {
    
    Long countByStockLessThan(Integer stock);
    
    // Método corregido con JPQL sin el uso de LIMIT, se usa setMaxResults() para limitar los resultados
    @Query("SELECT p FROM Producto p WHERE p.stock IS NOT NULL ORDER BY p.stock DESC")
List<Producto> findTop5ByOrderByStockDesc();

    List<Producto> findTop5ByOrderByIdProductoDesc();
}
