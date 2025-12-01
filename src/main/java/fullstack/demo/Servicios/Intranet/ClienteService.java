package fullstack.demo.Servicios.Intranet;

import fullstack.demo.DTO.Intranet.ClienteRequest;
import fullstack.demo.Entidad.Intranet.Cliente;

public interface ClienteService {
    Cliente obtenerOCrearCliente(ClienteRequest dto);
}
