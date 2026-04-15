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
    public Optional<Invoice> getByOrder(long orderId) {
        return facturaCrud.findByOrdenId(orderId).map(mapper::toInvoice);

    }
    @Override
    public Optional<Invoice> getByNumber(String invoiceNumber) {
        return facturaCrud.findByNumeroFactura(invoiceNumber).map(mapper::toInvoice);
    }

    @Override
    public List<Invoice> getByMethod(String paymentMethod) {
       return  mapper.toInvoices(facturaCrud.findAllByMetodoPago(paymentMethod));
    }
}
