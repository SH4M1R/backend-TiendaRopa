package fullstack.demo.Servicios;

import java.util.List;
import fullstack.demo.Entidad.Cliente;

public interface ClienteService {
    List<Cliente> listarClientes();
    Cliente crearCliente(Cliente cliente);
    Cliente obtenerClientePorId(Integer idCliente);
    Cliente actualizarCliente(Cliente cliente);
    void eliminarCliente(Integer idCliente);
}
