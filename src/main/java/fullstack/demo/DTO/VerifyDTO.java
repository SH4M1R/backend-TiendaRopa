package fullstack.demo.DTO;

import lombok.Data;

@Data
public class VerifyDTO {
    private String identificador;
    private String codigo;
    private String metodo;
}