package fullstack.demo.Entidad;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

@Data
@Entity
@Table(name = "codigos_verificacion")
public class CodigoVerificacion {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String codigo;
    private String medtodo;
    private LocalDateTime expiracion;

    public void setIdentificador(String email) {
    }

    public void setMetodo(String email) {
    }
}
