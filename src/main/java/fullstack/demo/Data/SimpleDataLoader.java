package fullstack.demo.Data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import fullstack.demo.DAO.EmpleadoDAO;
import fullstack.demo.DAO.RolDAO;
import fullstack.demo.Entidad.Intranet.Empleado;
import fullstack.demo.Entidad.Intranet.Rol;

import org.springframework.beans.factory.annotation.Autowired;

@Component
public class SimpleDataLoader implements CommandLineRunner {

    @Autowired
    private RolDAO rolDAO;

    @Autowired
    private EmpleadoDAO empleadoDAO;

    @Override
    public void run(String... args) throws Exception {
        if (rolDAO.count() == 0) {
            Rol rolAdmin = new Rol();
            rolAdmin.setRol("ADMINISTRADOR");

            Rol rolVendedor = new Rol();
            rolVendedor.setRol("VENDEDOR");

            Rol rolAlmacen = new Rol();
            rolAlmacen.setRol("ALMACENISTA");

            rolDAO.save(rolAdmin);
            rolDAO.save(rolVendedor);
            rolDAO.save(rolAlmacen);

            Empleado admin = new Empleado();
            admin.setUser("Juan Pérez");
            admin.setUsername("admin");
            admin.setContrasena("admin123");
            admin.setRol(rolAdmin);

            Empleado vendedor = new Empleado();
            vendedor.setUser("María López");
            vendedor.setUsername("vendedor");
            vendedor.setContrasena("venta123");
            vendedor.setRol(rolVendedor);

            Empleado almacenista = new Empleado();
            almacenista.setUser("Carlos Ramos");
            almacenista.setUsername("almacenista");
            almacenista.setContrasena("almacen123");
            almacenista.setRol(rolAlmacen);

            empleadoDAO.save(admin);
            empleadoDAO.save(vendedor);
            empleadoDAO.save(almacenista);
        } else {
            System.out.println("Los datos iniciales ya existen, no se cargaron nuevamente.");
        }
    }
}