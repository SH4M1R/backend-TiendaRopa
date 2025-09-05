package fullstack.demo.Entidad;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "VentaOnline")
public class VentaOnline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idVentaOnline;

    private BigDecimal total;
    private LocalDateTime fechaVenta;

    @ManyToOne
    @JoinColumn(name = "Usuario_idUsuario", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "ventaOnline", cascade = CascadeType.ALL)
    private List<DetalleVentaOnline> detalles;
}