package fullstack.demo.DTO.Intranet;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class VentaRequest {
    private BigDecimal total;
    private ClienteRequest cliente;
    private List<DetalleProductoRequest> detalles;

    @Data
    public static class DetalleProductoRequest {
        private ProductoId producto;
        private Integer stock;
        private BigDecimal subtotal;
        private String metodoPago;
        private BigDecimal montoPagado;
        private BigDecimal vuelto;
        private String codigoIzipay;
        private String numeroTarjeta;
    }

    @Data
    public static class ProductoId {
        private Integer idProducto;
    }
}
