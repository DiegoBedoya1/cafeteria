package PruebaPractica.Bedoya.Domain.Service;

import PruebaPractica.Bedoya.Domain.DTO.Order;
import PruebaPractica.Bedoya.Domain.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;

    public Optional<List<Order>> getByClientId(long clientId) {
        return repository.getByClientId(clientId);
    }

    public List<Order> saveOrders(long id, List<Order> orders){
        return repository.saveOrders(id,orders);
    }
}
