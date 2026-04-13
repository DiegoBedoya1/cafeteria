package PruebaPractica.Bedoya;

import PruebaPractica.Bedoya.Domain.DTO.Client;
import PruebaPractica.Bedoya.Domain.DTO.Order;
import PruebaPractica.Bedoya.Domain.Repository.OrderRepository;
import PruebaPractica.Bedoya.Persistance.Crud.ClienteCrudRepository;
import PruebaPractica.Bedoya.Persistance.Crud.OrdenCrudRepository;
import PruebaPractica.Bedoya.Persistance.Entity.Cliente;
import PruebaPractica.Bedoya.Persistance.Entity.Orden;
import PruebaPractica.Bedoya.Persistance.Mapper.ClientMapper;
import PruebaPractica.Bedoya.Persistance.Mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrdenRepository implements OrderRepository {
    @Autowired
    private OrdenCrudRepository crud;

    @Autowired
    private OrderMapper mapperOrder;

    @Autowired
    private ClientMapper mapperClient;

    @Override
    public Optional<List<Order>> getByClientId(long idClient){
        List<Orden> ordenes = crud.findById(idClient);
        return Optional.of(mapperOrder.toOrders(ordenes));
    }
    @Override
    public List<Order> saveOrders(long id, List<Order> orders){
       Client client = crud.findById(id)
               .map(mapperClient::toClient);
       client.getOrders().add(orders);

    }
}
