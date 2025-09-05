package fullstack.demo.Servicios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fullstack.demo.DAO.EmpleadoDAO;
import fullstack.demo.Entidad.Empleado;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired EmpleadoDAO empleadoDAO;

    @Override
    public Empleado crearEmpleado(Empleado Empleado) {return empleadoDAO.save(Empleado);}

    @Override
    public List<Empleado> listarEmpleados() {return empleadoDAO.findAll();}

    @Override
    public Empleado obtenerEmpleadoPorId(Integer idEmpleado) {return empleadoDAO.findById(idEmpleado).get();}

    @Override
    public Empleado actualizarEmpleado(Empleado Empleado) {return empleadoDAO.save(Empleado);}

    @Override
    public void eliminarEmpleado(Integer idEmpleado) {empleadoDAO.deleteById(idEmpleado);}
}
