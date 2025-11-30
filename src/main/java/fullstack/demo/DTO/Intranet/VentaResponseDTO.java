package fullstack.demo.DTO.Intranet;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class VentaResponseDTO {
    private Integer idVenta;
    private String clienteNombre;
    private String clienteDocumento;
    private LocalDateTime fechaVenta;
    private BigDecimal total;
    private List<DetalleVentaDTO> detalles;

    @Data
    public static class DetalleVentaDTO {
        private String productoNombre;
        private Integer cantidad;
        private BigDecimal precio;
        private BigDecimal subtotal;
        private String metodoPago;
        private BigDecimal montoPagado;
        private BigDecimal vuelto;
        private String codigoIzipay;
        private String numeroTarjeta;
    }
}
