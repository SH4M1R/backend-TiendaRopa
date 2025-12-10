package fullstack.demo.RestControl.Intranet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fullstack.demo.Entidad.Intranet.Venta;
import fullstack.demo.Servicios.Intranet.BoletaService;
import fullstack.demo.ServiciosImpl.Intranet.VentaServiceImpl;
import fullstack.demo.DAO.Intranet.VentaDAO;
import fullstack.demo.DTO.Intranet.VentaRequest;
import lombok.Data;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ventas")

public class VentaControlador {
    @Autowired
    private VentaServiceImpl ventaService;
    @Autowired
    private VentaDAO ventaDAO;
    @Autowired
    private BoletaService boletaService;
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarVenta(@RequestBody VentaRequest request) {
        try {
            Venta ventaRegistrada = ventaService.registrarVenta(request);
            return ResponseEntity.ok(ventaRegistrada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error al registrar la venta: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error inesperado: " + e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<VentaResponseDTO>> listarVentas() {
        try {
            List<Venta> ventas = ventaDAO.findAll();
            List<VentaResponseDTO> dtoList = ventas.stream().map(this::convertirADTO).collect(Collectors.toList());
            return ResponseEntity.ok(dtoList);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerVentaPorId(@PathVariable Integer id) {
        try {
            Optional<Venta> ventaOpt = ventaDAO.findById(id);
            if (ventaOpt.isPresent()) {
                VentaResponseDTO dto = convertirADTO(ventaOpt.get());
                return ResponseEntity.ok(dto);
            } else {
                return ResponseEntity.status(404).body("Venta no encontrada con ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al obtener la venta: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/boleta")
    public ResponseEntity<byte[]> descargarBoleta(@PathVariable Integer id) {
        try {
            Venta venta = ventaDAO.findById(id)
                    .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

            byte[] pdf = boletaService.generarBoletaTicketPDF(venta);

            return ResponseEntity.ok()
                    .header("Content-Disposition", "inline; filename=boleta_" + id + ".pdf")
                    .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                    .body(pdf);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    private VentaResponseDTO convertirADTO(Venta venta) {
        VentaResponseDTO dto = new VentaResponseDTO();
        dto.setIdVenta(venta.getIdVenta());
        dto.setClienteNombre(venta.getCliente() != null ? venta.getCliente().getNombre() : "CLIENTE VARIOS");
        dto.setFechaVenta(venta.getFechaVenta());
        dto.setTotal(venta.getTotal());
        dto.setDetalles(
                venta.getDetalles().stream().map(det -> {
                    VentaResponseDTO.DetalleVentaDTO detDTO = new VentaResponseDTO.DetalleVentaDTO();
                    detDTO.setProductoNombre(det.getProducto().getProducto());
                    detDTO.setCantidad(det.getStock());
                    detDTO.setPrecio(det.getProducto().getPrecioVenta());
                    detDTO.setSubtotal(det.getSubtotal());
                    detDTO.setMetodoPago(det.getMetodoPago());
                    detDTO.setMontoPagado(det.getMontoPagado());
                    detDTO.setVuelto(det.getVuelto());
                    detDTO.setCodigoIzipay(det.getCodigoIzipay());
                    detDTO.setNumeroTarjeta(det.getNumeroTarjeta());
                    return detDTO;
                }).collect(Collectors.toList()));
        return dto;
    }

    // --- DTO DE RESPUESTA ---
    @Data
    public static class VentaResponseDTO {
        private Integer idVenta;
        private String clienteNombre;
        private String clienteDocumento;
        private java.time.LocalDateTime fechaVenta;
        private java.math.BigDecimal total;
        private List<DetalleVentaDTO> detalles;

        @Data
        public static class DetalleVentaDTO {
            private String productoNombre;
            private Integer cantidad;
            private java.math.BigDecimal precio;
            private java.math.BigDecimal subtotal;
            private String metodoPago;
            private java.math.BigDecimal montoPagado;
            private java.math.BigDecimal vuelto;
            private String codigoIzipay;
            private String numeroTarjeta;
        }
    }
}
