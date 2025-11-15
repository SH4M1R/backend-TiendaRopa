package fullstack.demo.Servicios;

import fullstack.demo.DTO.ClienteRequest;
import fullstack.demo.Entidad.Intranet.Cliente;

public interface ClienteService {
    Cliente obtenerOCrearCliente(ClienteRequest dto);
}
