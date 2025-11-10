package fullstack.demo.Servicios;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import fullstack.demo.Data.DniData;

@Service
public class DniService {

    public DniData buscarPorDni(String dni) throws Exception {
        String url = "https://eldni.com/pe/buscar-por-dni?dni=" + dni;

        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0") 
                .timeout(10000)
                .get();

        Element nombreElement = doc.selectFirst(".nombre-class");
        Element apellidoElement = doc.selectFirst(".apellido-class");

        DniData data = new DniData();
        data.setDni(dni);
        data.setNombre(nombreElement != null ? nombreElement.text() : "No encontrado");
        data.setApellido(apellidoElement != null ? apellidoElement.text() : "No encontrado");

        return data;
    }
}