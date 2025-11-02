package fullstack.demo.Servicios;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class UploadService {

    private final String uploadDir = "src/main/resources/static/upload/";

    public String saveUpload(MultipartFile file) {
        try {
            if (file != null && !file.isEmpty()) {
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    throw new IOException("El archivo no es una imagen válida.");
                }

                byte[] bytes = file.getBytes();
                String encodedName = URLEncoder.encode(
                    Objects.requireNonNull(file.getOriginalFilename()), StandardCharsets.UTF_8
                );
                Path path = Paths.get(uploadDir + encodedName);

                if (!Files.exists(path.getParent())) {
                    Files.createDirectories(path.getParent());
                }

                Files.write(path, bytes);

                return "/upload/" + encodedName;
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al guardar el archivo: " + e.getMessage());
        }
    }

    public void deleteUpload(String fileName) {
        try {
            if (fileName != null) {
                File file = new File(uploadDir + fileName);
                if (file.exists()) {
                    file.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}