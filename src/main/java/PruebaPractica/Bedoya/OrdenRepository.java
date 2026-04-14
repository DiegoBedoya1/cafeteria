package PruebaPractica.Bedoya;

import PruebaPractica.Bedoya.Domain.DTO.Client;
import PruebaPractica.Bedoya.Domain.DTO.Order;
import PruebaPractica.Bedoya.Domain.Repository.OrderRepository;
import PruebaPractica.Bedoya.Persistance.Crud.ClienteCrudRepository;
import PruebaPractica.Bedoya.Persistance.Crud.OrdenCrudRepository;
import PruebaPractica.Bedoya.Persistance.Crud.ProductoCrudRepository;
import PruebaPractica.Bedoya.Persistance.Entity.Cliente;
import PruebaPractica.Bedoya.Persistance.Entity.Orden;
import PruebaPractica.Bedoya.Persistance.Entity.Producto;
import PruebaPractica.Bedoya.Persistance.Mapper.ClientMapper;
import PruebaPractica.Bedoya.Persistance.Mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class OrdenRepository implements OrderRepository {
    @Autowired
    private OrdenCrudRepository crudOrden;

    @Autowired
    private OrderMapper mapperOrder;

    @Autowired
    private ClientMapper mapperClient;

    @Autowired
    private ClienteCrudRepository crudCliente;
    @Autowired
    private ProductoCrudRepository productoCrudRepository;

    @Override
    public Optional<List<Order>> getByClientId(long idClient){
        List<Orden> ordenes = crudOrden.findById(idClient);
        return Optional.of(mapperOrder.toOrders(ordenes));
    }
    @Override
    public List<Order> saveOrders(long id, List<Order> orders){
       Cliente cliente = crudCliente.findById(id)
               .orElseThrow(RuntimeException::new);
       Client client = mapperClient.toClient(cliente);
       orders.forEach(order -> {
          Orden orden = mapperOrder.toOrden(order);
          orden.setCliente(cliente);
          List<Long> idProductos = orden.getProductos().stream()
                  .map(Producto::getId)
                  .toList();
          List<Producto> productosBD = (List<Producto>) productoCrudRepository.findAllById(idProductos);
          double total = productosBD.stream().mapToDouble(Producto::getPrecio).sum();
          orden.setTotal(total);
          orden.setFecha(LocalDate.now());
          orden.setProductos(productosBD);
          Orden guardada = crudOrden.save(orden);
          client.getOrders().add(mapperOrder.toOrder(guardada));
       });
       return client.getOrders();
    }
}
