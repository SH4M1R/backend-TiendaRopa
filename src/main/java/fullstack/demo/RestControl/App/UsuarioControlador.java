package fullstack.demo.RestControl.App;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import fullstack.demo.DAO.UsuarioDAO;
import fullstack.demo.Entidad.App.Usuario;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioControlador {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @PostMapping("/registro")
    public Usuario registrarUsuario(@RequestBody Usuario usuario) {
        return usuarioDAO.save(usuario);
    }

    @GetMapping("/listar")
    public List<Usuario> listarUsuarios() {
        return usuarioDAO.findAll();
    }

    @GetMapping("/{id}")
    public Usuario obtenerUsuario(@PathVariable Integer id) {
        return usuarioDAO.findById(id).orElse(null);
    }
}
