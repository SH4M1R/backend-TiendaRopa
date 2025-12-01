package fullstack.demo.Entidad;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Data
@Table(name = "Producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "producto_idProducto")
    private Integer idProducto;
    private String Producto;
    private BigDecimal PrecioCompra;
    private String Descripcion;
    private Boolean Estado;
    private BigDecimal PrecioVenta;
    private Integer Stock;
    private String Talla;
    private String Color;
    private String Imagen;
    private Boolean Genero;  

    @ManyToOne
    @JoinColumn(name = "Categoria_idCategoria", nullable = false)
    @JsonIgnoreProperties("productos")
    private Categoria categoria;
}