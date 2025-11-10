package fullstack.demo.RestControl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fullstack.demo.Data.DniData;
import fullstack.demo.Servicios.DniService;

@RestController
@RequestMapping("/api/dni")
public class DniControlador {

    @Autowired
    private DniService dniService;

    @GetMapping("/{dni}")
    public DniData buscarDni(@PathVariable String dni) throws Exception {
        return dniService.buscarPorDni(dni);
    }
}