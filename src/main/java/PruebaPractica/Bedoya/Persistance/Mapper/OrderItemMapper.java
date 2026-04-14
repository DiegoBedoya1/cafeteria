package PruebaPractica.Bedoya.Persistance.Mapper;

import PruebaPractica.Bedoya.Domain.DTO.OrderItem;
import PruebaPractica.Bedoya.Persistance.Entity.OrdenProducto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface OrderItemMapper {
    @Mappings({
            @Mapping(source = "producto", target = "product"),
            @Mapping(source = "cantidad", target = "quantity")
    })
    OrderItem toOrderItem(OrdenProducto ordenProducto);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "orden", ignore = true),
            @Mapping(target = "id", ignore = true)
    } )
    OrdenProducto toOrdenProducto(OrderItem orderItem);

    List<OrderItem> toOrderItems(List<OrdenProducto> ordenProductos);
    List<OrdenProducto> toOrdenProductos(List<OrderItem> ordenItems);


}
