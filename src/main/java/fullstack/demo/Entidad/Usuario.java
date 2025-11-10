package fullstack.demo.Entidad;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = false)
    private String email;

    private String telefono;

    @Column(nullable = false)
    private String contrasena;

    @Column(name = "metodo_2fa")
    private String metodo2FA;

    @Column(nullable = false)
    private Boolean verificado = false;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
}
//package fullstack.demo.Entidad;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import lombok.*;
//
//@Getter
//@Setter
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "Usuario")
//public class Usuario {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id_usuario")
//    private Integer idUsuario;
//
//    @Column(unique = true, nullable = false)
//    private String username;
//
//    @Column(nullable = false)
//    private String nombres;
//
//    @Column(nullable = false)
//    private String apellidos;
//
//    @Column(unique = true, nullable = false)
//    private String email;
//
//    private String telefono;
//
//    @Column(nullable = false)
//    private String contrasena;
//
//    // Campo para verificación de cuenta
//    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
//    private Boolean verificado = false;
//
//}