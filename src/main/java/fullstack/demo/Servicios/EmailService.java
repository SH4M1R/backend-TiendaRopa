package fullstack.demo.Servicios;

import jakarta.mail.MessagingException;

public interface EmailService {
    String generarYEnviarCodigo(String email) throws MessagingException;

}

