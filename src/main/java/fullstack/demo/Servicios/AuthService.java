package fullstack.demo.Servicios;

import fullstack.demo.DTO.RegisterDTO;
import fullstack.demo.Entidad.Usuario;
import jakarta.mail.MessagingException;

public interface AuthService {


    /**
     * Registra un nuevo usuario en el sistema y envía un código 2FA
     * al método elegido (email o SMS).
     *
     * @param usuario Objeto con los datos del nuevo usuario.
     * @return Mensaje confirmando el registro.
     * @throws MessagingException Si ocurre un error al enviar el correo.
     */
    String registrarUsuario(Usuario usuario) throws MessagingException;
    void registrarUsuario(RegisterDTO dto) throws MessagingException;
    boolean verificarCodigo(String identificador, String codigo, String metodo);
    void reenviarCodigo(String identificador, String metodo) throws MessagingException;

}

