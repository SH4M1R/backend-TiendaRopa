package fullstack.demo.Entidad;

import java.math.BigDecimal;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    private VentaOnline ventaOnline;

    @ManyToOne
    @JoinColumn(name = "Producto_idProducto", nullable = false)
    private Producto producto;
}