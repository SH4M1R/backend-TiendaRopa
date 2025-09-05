package fullstack.demo.Entidad;

import java.util.List;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Categoria")

public class Categoria {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCategoria;

    @Column(nullable = false, length = 45)
    private String categoria;

    @OneToMany(mappedBy = "categoria")
    private List<Producto> productos;
}
