package fullstack.demo.Entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    private String nombre;
    private String correo;
    private String direccion;
    private String contrasena;
    private String telefono;
    private String avatarUrl;
    private Boolean activo = true;

    // Campos para configuración de tema
    private String temaPreferido = "claro";
    private String idioma = "es";
}