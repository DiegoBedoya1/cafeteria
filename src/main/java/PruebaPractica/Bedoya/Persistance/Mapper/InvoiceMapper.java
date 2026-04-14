package PruebaPractica.Bedoya.Persistance.Mapper;

import PruebaPractica.Bedoya.Domain.DTO.Invoice;
import PruebaPractica.Bedoya.Persistance.Entity.Factura;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public interface InvoiceMapper {

    @Mappings({
            @Mapping(source = "numeroFactura", target = "invoicesNumber"),
            @Mapping(source = "fechaEmision", target = "EmissionDate"),
            @Mapping(source = "orden", target = "order"),
            @Mapping(source = "metodoPago", target = "paymentMethod")
    })
    Invoice toInvoice(Factura factura);

    @InheritInverseConfiguration
    @Mapping(target = "total", ignore = true)
    Factura toFactura(Invoice invoice);
}
