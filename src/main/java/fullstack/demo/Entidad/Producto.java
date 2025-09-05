package fullstack.demo.Entidad;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "Producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cancha_id")
    private Integer idProducto;
    private String Producto;
    private BigDecimal PrecioCompra;
    private BigDecimal PrecioVenta;
    private Integer Stock;
    private String Descripcion;
    private String Talla;
    private String Color;
    private Boolean Genero;  

    @ManyToOne
    @JoinColumn(name = "Categoria_idCategoria", nullable = false)
    private Categoria categoria;
}