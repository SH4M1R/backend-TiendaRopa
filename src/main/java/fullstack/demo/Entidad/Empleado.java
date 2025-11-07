package fullstack.demo.Entidad;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "Empleado")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEmpleado;
    private String user;
    private String username;
    private String contrasena;

    @ManyToOne
    @JoinColumn(name = "Rol_idRol")
    @JsonIgnoreProperties("empleados")
    @ToString.Exclude
    private Rol rol;
}