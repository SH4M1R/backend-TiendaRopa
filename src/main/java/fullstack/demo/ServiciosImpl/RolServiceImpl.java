package fullstack.demo.ServiciosImpl;

import fullstack.demo.DAO.RolDAO;
import fullstack.demo.Entidad.Rol;
import fullstack.demo.Servicios.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RolServiceImpl implements RolService {

    @Autowired private RolDAO rolDAO;

    @Override
    public List<Rol> ListarRoles() { return rolDAO.findAll();}

    @Override
    public Rol crearRol(Rol rol) { return rolDAO.save(rol);}

    @Override
    public Rol obtenerRolPorId(Integer idRol) { return rolDAO.findById(idRol).get();} 

    @Override
    public Rol actualizarRol(Rol rol) { return rolDAO.save(rol);}   

    @Override
    public void eliminarRol(Integer idRol) { rolDAO.deleteById(idRol); }    

}
