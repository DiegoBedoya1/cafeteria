package PruebaPractica.Bedoya.Persistance.Mapper;

import PruebaPractica.Bedoya.Domain.DTO.Order;
import PruebaPractica.Bedoya.Persistance.Entity.Orden;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface OrderMapper {
    @Mappings({
            @Mapping(source = "id", target = "orderId"),
            @Mapping(source = "cliente", target = "client"),
            @Mapping(source = "productos", target = "products"),
            @Mapping(source = "fecha", target = "date"),
    })
    Order toOrder(Orden orden);

    @InheritInverseConfiguration
    Orden toOrden(Order order);
    List<Order> toOrders(List<Orden> ordenes);
}
