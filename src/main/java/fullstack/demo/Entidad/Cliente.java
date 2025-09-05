package fullstack.demo.Entidad;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCliente;

    private String nombre;
    private String correo;
    private Integer documento;
}
