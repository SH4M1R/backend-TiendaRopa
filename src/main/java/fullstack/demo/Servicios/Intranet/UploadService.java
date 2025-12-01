package fullstack.demo.Servicios.Intranet;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UploadService {

    private final String uploadDir = "src/main/resources/static/upload/";

    public String saveUpload(MultipartFile file) {
        try {
            Path directory = Paths.get(uploadDir);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            Path filepath = directory.resolve(filename);

            Files.write(filepath, file.getBytes());

            return "/upload/" + filename;

        } catch (Exception e) {
            throw new RuntimeException("Error al guardar imagen: " + e.getMessage());
        }
    }
}
