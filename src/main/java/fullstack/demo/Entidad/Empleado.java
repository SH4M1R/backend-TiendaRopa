package fullstack.demo.Entidad;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Empleado")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEmpleado;

    private String nombre;
    private String credenciales;
    private String contrasena;

    @ManyToOne
    @JoinColumn(name = "Rol_idRol")
    private Rol rol;
}
