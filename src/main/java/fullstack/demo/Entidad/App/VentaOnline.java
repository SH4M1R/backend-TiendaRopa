package fullstack.demo.Entidad.App;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
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

    @Column(nullable = false)
    private BigDecimal total;

    @Column(nullable = false)
    private LocalDateTime fechaVenta;

    @Column(nullable = false)
    private String metodoPago;

    // NUEVO CAMPO DE ESTADO
    @Column(nullable = false)
    private String estado = "PENDIENTE";

    @ManyToOne
    @JoinColumn(name = "Usuario_idUsuario", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "ventaOnline", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("ventaOnline")   // ← evita recursión infinita
    private List<DetalleVentaOnline> detalles;
}
