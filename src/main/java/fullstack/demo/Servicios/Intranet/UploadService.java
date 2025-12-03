package fullstack.demo.Servicios.Intranet;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

@Service
public class UploadService {

    @Autowired
    private Cloudinary cloudinary;

    public String saveUpload(MultipartFile file) {
        try {
            // Subir a Cloudinary
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            
            // Retornar la URL segura (https) de la imagen en la nube
            return uploadResult.get("secure_url").toString();

        } catch (IOException e) {
            throw new RuntimeException("Error al subir imagen a Cloudinary: " + e.getMessage());
        }
    }
}
