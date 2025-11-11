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
    
    @Column(name = "producto")
    private String Producto;
    
    @Column(name = "PrecioCompra")
    private BigDecimal PrecioCompra;
    
    @Column(name = "Descripcion")
    private String Descripcion;
    
    @Column(name = "Estado")
    private Boolean Estado;
    
    @Column(name = "PrecioVenta")
    private BigDecimal PrecioVenta;
    
    @Column(name = "Stock")
    private Integer stock;  // Asegúrate de que esté correctamente mapeado a la columna "Stock" en la base de datos
    
    @Column(name = "Talla")
    private String Talla;
    
    @Column(name = "Color")
    private String Color;
    
    @Column(name = "Imagen")
    private String Imagen;
    
    @Column(name = "Genero")
    private Boolean Genero;  

    @ManyToOne
    @JoinColumn(name = "Categoria_idCategoria", nullable = false)
    @JsonIgnoreProperties("productos")
    private Categoria categoria;
}
