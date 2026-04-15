package PruebaPractica.Bedoya.Persistance.Mapper;

import PruebaPractica.Bedoya.Domain.DTO.Order;
import PruebaPractica.Bedoya.Persistance.Entity.Orden;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ClientMapper.class,OrderItemMapper.class})
public interface OrderMapper {
    @Mappings({
            @Mapping(source = "id",target = "orderId"),
            @Mapping(source = "cliente", target = "client"),
            @Mapping(source = "detalles", target = "details"),
            @Mapping(source = "fecha", target = "date"),
            @Mapping(source = "estado", target = "state"),
            @Mapping(source = "estadoProceso", target = "stateProcess"),
            @Mapping(source = "ordenPadreId", target = "parentOrderId")
    })
    Order toOrder(Orden orden);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "total", ignore = true),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "fecha",ignore = true)
    })
    Orden toOrden(Order order);
    List<Order> toOrders(List<Orden> ordenes);
}
