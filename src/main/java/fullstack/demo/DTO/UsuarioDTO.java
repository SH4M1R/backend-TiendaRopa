package fullstack.demo.DTO;

import lombok.Data;

@Data

public class UsuarioDTO {
    private String username;
    private String nombres;
    private String apellidos;
    private String email;
    private String telefono;
    private String contrasena;
    private String metodo2FA; // "email" o "sms"
}
