package fullstack.demo.DTO.App;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ResetCodeRequest {
    
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El formato del correo es inválido")
    private String correo;

    public ResetCodeRequest() {}

    public ResetCodeRequest(String correo) {
        this.correo = correo;
    }
    
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}