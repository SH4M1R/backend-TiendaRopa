package fullstack.demo.Servicios;

import jakarta.mail.MessagingException;


//@Service
//public interface EmailService {
//    private final JavaMailSender mailSender;
//
//    @Value("${spring.mail.username}")
//    private String from;
//
//    public EmailService(JavaMailSender mailSender) {
//        this.mailSender = mailSender;
//    }
//
//    public void enviarCodigo(String to, String codigo) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(from);
//        message.setTo(to);
//        message.setSubject("Código de verificación");
//        message.setText("Tu código de verificación es: " + codigo + "\nVálido por 5 minutos.");
//        mailSender.send(message);
//    }
//}



public interface EmailService {
    String generarYEnviarCodigo(String email) throws MessagingException;

    void enviarCodigo(String email, String codigo);
}

