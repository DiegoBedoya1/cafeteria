package PruebaPractica.Bedoya.Persistance.Mapper;

import PruebaPractica.Bedoya.Domain.DTO.Client;
import PruebaPractica.Bedoya.Domain.DTO.Order;
import PruebaPractica.Bedoya.Persistance.Entity.Cliente;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    @Mappings({
            @Mapping(source = "id",target = "clientId"),
            @Mapping(source = "nombre", target = "name"),
            @Mapping(source = "correo", target = "mail"),
            @Mapping(source = "telefono", target = "cellphone")
    })
    Client toClient(Cliente cliente);

    @InheritInverseConfiguration
    Cliente toCliente(Client client);


}
