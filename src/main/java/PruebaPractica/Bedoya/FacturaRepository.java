package PruebaPractica.Bedoya;

import PruebaPractica.Bedoya.Domain.DTO.Invoice;
import PruebaPractica.Bedoya.Domain.DTO.Order;
import PruebaPractica.Bedoya.Domain.Repository.InvoiceRepository;
import PruebaPractica.Bedoya.Persistance.Crud.FacturaCrudRepository;
import PruebaPractica.Bedoya.Persistance.Crud.OrdenCrudRepository;
import PruebaPractica.Bedoya.Persistance.Entity.Factura;
import PruebaPractica.Bedoya.Persistance.Entity.Orden;
import PruebaPractica.Bedoya.Persistance.Mapper.InvoiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FacturaRepository implements InvoiceRepository {
    @Autowired
    private FacturaCrudRepository facturaCrud;
    @Autowired
    private InvoiceMapper mapper;

    @Override
    public Optional<Invoice> getByOrder(Order order) {
        Factura factura = facturaCrud.findByOrdenId(order.getOrderId())
                .orElseThrow(() -> new RuntimeException("no existe esa orden"));
        return Optional.of(mapper.toInvoice(factura));

    }
    @Override
    public Optional<Invoice> getByNumber(String invoiceNumber) {
        return Optional.empty();
    }

    @Override
    public List<Invoice> getByMethod(String paymentMethod) {
        return null;
    }
}
