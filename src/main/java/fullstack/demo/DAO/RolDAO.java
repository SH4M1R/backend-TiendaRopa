package fullstack.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import fullstack.demo.Entidad.Rol;

public interface RolDAO extends JpaRepository <Rol, Integer>{

}
