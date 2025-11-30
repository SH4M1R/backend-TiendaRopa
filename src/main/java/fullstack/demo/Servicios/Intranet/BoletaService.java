package fullstack.demo.Servicios.Intranet;


import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import fullstack.demo.Entidad.Intranet.DetalleVenta;
import fullstack.demo.Entidad.Intranet.Venta;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class BoletaService {

    public byte[] generarBoletaTicketPDF(Venta venta) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            String html = "<html><head><style>"
                    + "@page { size: 80mm 200mm; margin: 10mm; }" // más margen
                    + "body { font-family: monospace; font-size: 12px; margin: 0; padding: 0; }"
                    + "table { width: 100%; border-collapse: collapse; margin-top: 5px; }"
                    + "th, td { padding: 3px 0; font-size: 12px; }"
                    + "th { border-bottom: 1px dashed #000; font-weight: bold; }"
                    + ".total { font-weight: bold; border-top: 1px solid #000; font-size: 13px; }"
                    + ".center { text-align: center; }"
                    + ".empresa { font-size: 14px; font-weight: bold; margin-bottom: 5px; }"
                    + ".bordes { border: 1px solid #000; padding: 5px; }"
                    + "</style></head><body>"
                    + "<div class='bordes'>"
                    + "<div class='center empresa'>ModaStyle</div>"
                    + "<div class='center'>RUC: 12345678901<br/>Av. Comercio 123 - Lima<br/>Tel: (01) 123-4567</div>"
                    + "<p>Boleta N°: <b>" + venta.getIdVenta() + "</b><br/>"
                    + "Fecha: " + venta.getFechaVenta().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "<br/>"
                    + "Cliente: " + (venta.getCliente() != null ? "<b>" + venta.getCliente().getNombre() + "</b>" : "CLIENTE VARIOS") + "<br/>"
                    + "DNI: " + (venta.getCliente() != null ? venta.getCliente().getDocumento() : "---") + "</p>"
                    + "<table><thead><tr><th>Producto</th><th>Total</th></tr></thead><tbody>";

            for (DetalleVenta det : venta.getDetalles()) {
                html += "<tr><td>" + det.getProducto().getProducto() + "<br/>"
                        + "Cant: " + det.getStock() + " x S/ " + String.format("%.2f", det.getProducto().getPrecioVenta()) + "</td>"
                        + "<td style='text-align:right; font-weight:bold;'>S/ " + String.format("%.2f", det.getSubtotal()) + "</td></tr>";
            }

            html += "</tbody><tfoot><tr class='total'><td>Total</td>"
                    + "<td style='text-align:right;'>S/ " + String.format("%.2f", venta.getTotal()) + "</td></tr></tfoot></table>";

            DetalleVenta det0 = venta.getDetalles().get(0);
            html += "<p>Pago: <b>" + det0.getMetodoPago() + "</b><br/>"
                    + "Monto: <b>S/ " + String.format("%.2f", det0.getMontoPagado()) + "</b><br/>"
                    + "Vuelto: <b>S/ " + String.format("%.2f", det0.getVuelto()) + "</b></p>";

            if (det0.getCodigoIzipay() != null) {
                html += "<p>Op. Izipay: <b>" + det0.getCodigoIzipay() + "</b><br/>"
                        + "Tarjeta: <b>" + det0.getNumeroTarjeta() + "</b></p>";
            }

            html += "<div class='center'><b>¡Gracias por su compra!</b></div>"
                    + "</div></body></html>";

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(baos);

            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error generando boleta PDF tipo ticket", e);
        }
    }
}