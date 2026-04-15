package PruebaPractica.Bedoya.Persistance.Mapper;

import PruebaPractica.Bedoya.Domain.DTO.Invoice;
import PruebaPractica.Bedoya.Persistance.Entity.Factura;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public interface InvoiceMapper {

    @Mappings({
            @Mapping(source = "numeroFactura", target = "invoicesNumber"),
            @Mapping(source = "fechaEmision", target = "issueDate"),
            @Mapping(source = "orden", target = "order"),
            @Mapping(source = "metodoPago", target = "paymentMethod"),
            @Mapping(source = "estado",target = "state"),
            @Mapping(source = "estadoPago", target = "paymentState"),
    })
    Invoice toInvoice(Factura factura);

    @InheritInverseConfiguration
    Factura toFactura(Invoice invoice);
    List<Invoice> toInvoices(List<Factura> facturas);
    List<Factura> toFacturas(List<Invoice> invoices);
}
