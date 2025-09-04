package fullstack.demo.Entidad;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "DetalleVenta")
public class DetalleVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetalleVenta;

    private Integer stock;
    private BigDecimal subtotal;
    private BigDecimal montoPagado;
    private BigDecimal vuelto;
    private String codigoIzipay;
    private String numeroTarjeta;
    private String metodoPago;

    @ManyToOne
    @JoinColumn(name = "Venta_idVenta")
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "Producto_idProducto")
    private Producto producto;
}
