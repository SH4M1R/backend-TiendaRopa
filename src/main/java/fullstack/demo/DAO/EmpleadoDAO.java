package fullstack.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import fullstack.demo.Entidad.Empleado;

public interface EmpleadoDAO extends JpaRepository <Empleado, Integer>{

}
