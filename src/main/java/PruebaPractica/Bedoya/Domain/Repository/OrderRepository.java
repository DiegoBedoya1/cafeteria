package PruebaPractica.Bedoya.Domain.Repository;

import PruebaPractica.Bedoya.Domain.DTO.Invoice;
import PruebaPractica.Bedoya.Domain.DTO.Order;
import PruebaPractica.Bedoya.Domain.DTO.SplitRequest;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Optional<List<Order>> getByClientId(long idClient);

    List<Order> saveOrders(long idClient, List<Order> orders);
    Order pagarOrden(long orderId, String method);
    void anularOrden(long id);
    List<Order> dividir(long facturaPadreId, List<SplitRequest> splits);
}