package PruebaPractica.Bedoya.Domain.Repository;

import PruebaPractica.Bedoya.Domain.DTO.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Optional<List<Order>> getByClientId(long idClient);

    List<Order> saveOrders(long idClient, List<Order> orders);
    Order pagarOrden(long orderId, String method);
    void anularOrden(long id);
}
