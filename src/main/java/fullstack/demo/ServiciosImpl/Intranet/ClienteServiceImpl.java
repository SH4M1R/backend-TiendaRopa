package fullstack.demo.ServiciosImpl.Intranet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fullstack.demo.DAO.Intranet.ClienteDAO;
import fullstack.demo.DTO.Intranet.ClienteRequest;
import fullstack.demo.Entidad.Intranet.Cliente;
import fullstack.demo.Servicios.Intranet.ClienteService;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteDAO clienteDAO;

    @Override
    public Cliente obtenerOCrearCliente(ClienteRequest dto) {

        if (dto == null || dto.getNombre() == null || dto.getNombre().isEmpty()) {
            Optional<Cliente> clienteVarios = clienteDAO.findByNombre("CLIENTE VARIOS");
            if (clienteVarios.isPresent()) return clienteVarios.get();

            Cliente nuevo = new Cliente();
            nuevo.setNombre("CLIENTE VARIOS");
            nuevo.setDocumento(0);
            return clienteDAO.save(nuevo);
        }

        if (dto.getDocumento() != null) {
            Optional<Cliente> existente = clienteDAO.findByDocumento(dto.getDocumento());
            if (existente.isPresent()) return existente.get();
        }

        Cliente nuevo = new Cliente();
        nuevo.setNombre(dto.getNombre());
        nuevo.setDocumento(dto.getDocumento());
        return clienteDAO.save(nuevo);
    }
}
