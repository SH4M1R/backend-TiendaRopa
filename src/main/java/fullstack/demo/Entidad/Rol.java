package fullstack.demo.Entidad;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "Rol")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRol;

    private String rol;
    @OneToMany(mappedBy = "rol")
    private List<Empleado> empleados;
}
