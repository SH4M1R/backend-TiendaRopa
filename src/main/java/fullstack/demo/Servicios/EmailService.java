package fullstack.demo.Servicios;

import jakarta.mail.MessagingException;

public interface EmailService {
    String generarYEnviarCodigo(String email) throws MessagingException;

    void enviarCorreoCodigo(String email, String codigo);
}

