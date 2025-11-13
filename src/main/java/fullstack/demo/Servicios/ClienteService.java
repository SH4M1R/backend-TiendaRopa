package fullstack.demo.Servicios;

import fullstack.demo.DTO.ClienteRequest;
import fullstack.demo.Entidad.Cliente;

public interface ClienteService {
    Cliente obtenerOCrearCliente(ClienteRequest dto);
}
