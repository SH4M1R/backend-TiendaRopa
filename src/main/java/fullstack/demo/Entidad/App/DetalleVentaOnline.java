package fullstack.demo.Entidad.App;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fullstack.demo.Entidad.Producto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DetalleVentaOnline")
public class DetalleVentaOnline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetalleVentaOnline;

    private Integer cantidad;
    private BigDecimal subtotal;

    @ManyToOne
    @JoinColumn(name = "VentaOnline_idVentaOnline", nullable = false)
    @JsonIgnore   // ← evita loop ventaOnline → detalles → ventaOnline
    private VentaOnline ventaOnline;

    @ManyToOne
    @JoinColumn(name = "Producto_idProducto", nullable = false)
    private Producto producto;
}
