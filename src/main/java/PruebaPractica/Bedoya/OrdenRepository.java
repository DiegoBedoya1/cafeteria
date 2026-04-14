package PruebaPractica.Bedoya;

import PruebaPractica.Bedoya.Domain.DTO.Client;
import PruebaPractica.Bedoya.Domain.DTO.Order;
import PruebaPractica.Bedoya.Domain.Repository.OrderRepository;
import PruebaPractica.Bedoya.Persistance.Crud.ClienteCrudRepository;
import PruebaPractica.Bedoya.Persistance.Crud.OrdenCrudRepository;
import PruebaPractica.Bedoya.Persistance.Crud.ProductoCrudRepository;
import PruebaPractica.Bedoya.Persistance.Entity.Cliente;
import PruebaPractica.Bedoya.Persistance.Entity.Orden;
import PruebaPractica.Bedoya.Persistance.Entity.OrdenProducto;
import PruebaPractica.Bedoya.Persistance.Entity.Producto;
import PruebaPractica.Bedoya.Persistance.Mapper.ClientMapper;
import PruebaPractica.Bedoya.Persistance.Mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private ClienteCrudRepository crudCliente;
    @Autowired
    private ProductoCrudRepository productoCrudRepository;

    @Override
    public Optional<List<Order>> getByClientId(long idClient){
        List<Orden> ordenes = crudOrden.findByClienteId(idClient);
        return Optional.of(mapperOrder.toOrders(ordenes));
    }
    @Override
    public List<Order> saveOrders(long id, List<Order> orders){
      Cliente cliente = crudCliente.findById(id)
              .orElseThrow(() -> new RuntimeException("el cliente no existe"));
      if(!cliente.getEstado()){
          throw new RuntimeException("el cliente no esta activo");
      }
      List<Order> respuestaNuevasOrdenes = new ArrayList<>();
      orders.forEach(order -> {
          if(order.getDetails() == null || order.getDetails().isEmpty()){
              throw new RuntimeException("No puedes crear una orden sin productos");
          }
          Orden orden = new Orden();
          orden.setCliente(cliente);
          orden.setEstado(true);
          orden.setFecha(LocalDate.now());
          orden.setEstadoProceso("PENDIENTE");
          List<OrdenProducto> detalles = new ArrayList<>();
          final double[] totalAcumulado = {0.0};
          order.getDetails().forEach(item -> {
              Producto productoDB = productoCrudRepository.findById(item.getProduct().getProductId())
                      .orElseThrow(() -> new RuntimeException("el producto no existe"));
              if(!productoDB.getEstado() || !productoDB.getDisponible()){
                  throw new RuntimeException("el producto "+ productoDB.getNombre() + " esta agotado o no disponible");
              }
              OrdenProducto nuevoDetalle = new OrdenProducto();
              nuevoDetalle.setProducto(productoDB);
              nuevoDetalle.setCantidad(item.getQuantity());
              nuevoDetalle.setOrden(orden);
              totalAcumulado[0]+= productoDB.getPrecio() * item.getQuantity();
              detalles.add(nuevoDetalle);
          });
          orden.setDetalles(detalles);
          orden.setTotal(totalAcumulado[0]);
          Orden guardada = crudOrden.save(orden);
          respuestaNuevasOrdenes.add(mapperOrder.toOrder(guardada));
      });
      return respuestaNuevasOrdenes;
    }
}
