package fullstack.demo.Servicios;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

    // Almacenamiento temporal de OTPs (en producción usar Redis o similar)
    private Map<String, String> otpStorage = new HashMap<>();
    private Map<String, Long> otpExpiration = new HashMap<>();
    
    private static final long OTP_VALIDITY = 5 * 60 * 1000; // 5 minutos

    /**
     * Genera un código OTP de 6 dígitos
     */
    public String generarOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    /**
     * Almacena el OTP para un identificador (email o teléfono)
     */
    public void almacenarOTP(String identificador, String otp) {
        otpStorage.put(identificador, otp);
        otpExpiration.put(identificador, System.currentTimeMillis() + OTP_VALIDITY);
    }

    /**
     * Verifica si el OTP es correcto y no ha expirado
     */
    public boolean verificarOTP(String identificador, String otp) {
        if (!otpStorage.containsKey(identificador)) {
            return false;
        }

        Long expiracion = otpExpiration.get(identificador);
        if (expiracion == null || System.currentTimeMillis() > expiracion) {
            // OTP expirado
            otpStorage.remove(identificador);
            otpExpiration.remove(identificador);
            return false;
        }

        String otpAlmacenado = otpStorage.get(identificador);
        if (otpAlmacenado.equals(otp)) {
            // OTP correcto, eliminar de storage
            otpStorage.remove(identificador);
            otpExpiration.remove(identificador);
            return true;
        }

        return false;
    }

    /**
     * Elimina el OTP de un identificador
     */
    public void eliminarOTP(String identificador) {
        otpStorage.remove(identificador);
        otpExpiration.remove(identificador);
    }

    /**
     * Verifica si existe un OTP válido para el identificador
     */
    public boolean existeOTPValido(String identificador) {
        if (!otpStorage.containsKey(identificador)) {
            return false;
        }

        Long expiracion = otpExpiration.get(identificador);
        if (expiracion == null || System.currentTimeMillis() > expiracion) {
            otpStorage.remove(identificador);
            otpExpiration.remove(identificador);
            return false;
        }

        return true;
    }
}