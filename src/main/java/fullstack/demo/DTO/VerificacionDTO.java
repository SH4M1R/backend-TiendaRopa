package fullstack.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificacionDTO {
    private String identificador; // email o telefono
    private String codigo;
    private String metodo; // "email" o "sms"
}