package fullstack.demo.Servicios;

import fullstack.demo.DAO.RolDAO;
import fullstack.demo.Entidad.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolDAO rolDAO;

    @Override
    public Rol crearRol(Rol rol) { return rolDAO.save(rol); }

    @Override
    public List<Rol> listarRoles() { return rolDAO.findAll(); }

    @Override
    public Rol obtenerRolPorId(Integer id) { return rolDAO.findById(id).orElse(null); }

    @Override
    public Rol actualizarRol(Rol rol) { return rolDAO.save(rol); }

    @Override
    public void eliminarRol(Integer id) { rolDAO.deleteById(id); }
}
