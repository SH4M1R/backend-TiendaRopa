package fullstack.demo.Entidad;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "codigos_verificacion")
public class CodigoVerificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String codigo;

    @Column(name = "metodo") // Corregido: era "medtodo"
    private String metodo;

    @Column(nullable = false)
    private LocalDateTime expiracion;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    public void setId(String email) {
    }
}

//package fullstack.demo.Entidad;
//
//
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//
//@Getter
//@Setter
//
//@Data
//@Entity
//@Table(name = "codigos_verificacion")
//public class CodigoVerificacion {
//
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String email;
//    private String codigo;
//    private String medtodo;
//    private LocalDateTime expiracion;
//
//}
