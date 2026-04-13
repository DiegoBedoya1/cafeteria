package PruebaPractica.Bedoya.Persistance.Mapper;

import PruebaPractica.Bedoya.Domain.DTO.Product;
import PruebaPractica.Bedoya.Persistance.Entity.Producto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mappings({
            @Mapping(source = "id", target = "productId"),
            @Mapping(source = "nombre", target = "name"),
            @Mapping(source = "precio", target = "price"),
            @Mapping(source = "disponible", target = "available")
    })
    Product toProduct(Producto producto);

    @InheritInverseConfiguration
    Producto toProducto(Product product);

    List<Product> toProducts(List<Producto> productos);
}
