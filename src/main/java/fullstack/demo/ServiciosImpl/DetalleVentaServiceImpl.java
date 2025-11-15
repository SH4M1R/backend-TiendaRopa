package fullstack.demo.ServiciosImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fullstack.demo.DAO.DetalleVentaDAO;
import fullstack.demo.Entidad.Intranet.DetalleVenta;
import fullstack.demo.Servicios.DetalleVentaService;

@Service
public class DetalleVentaServiceImpl implements DetalleVentaService {

    @Autowired
    private DetalleVentaDAO detalleVentaDAO;

    @Override
    public DetalleVenta guardar(DetalleVenta detalle) {
        return detalleVentaDAO.save(detalle);
    }
}