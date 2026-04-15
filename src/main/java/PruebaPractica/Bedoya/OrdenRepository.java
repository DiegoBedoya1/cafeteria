package PruebaPractica.Bedoya;

import PruebaPractica.Bedoya.Domain.DTO.Order;
import PruebaPractica.Bedoya.Domain.Repository.OrderRepository;
import PruebaPractica.Bedoya.Domain.Service.InvoiceService;
import PruebaPractica.Bedoya.Persistance.Crud.ClienteCrudRepository;
import PruebaPractica.Bedoya.Persistance.Crud.OrdenCrudRepository;
import PruebaPractica.Bedoya.Persistance.Crud.ProductoCrudRepository;
import PruebaPractica.Bedoya.Persistance.Entity.Cliente;
import PruebaPractica.Bedoya.Persistance.Entity.Orden;
import PruebaPractica.Bedoya.Persistance.Entity.OrdenProducto;
import PruebaPractica.Bedoya.Persistance.Entity.Producto;
import PruebaPractica.Bedoya.Persistance.Mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private InvoiceService service;

    @Override
    public Optional<List<Order>> getByClientId(long idClient){
        List<Orden> ordenes = crudOrden.findByClienteId(idClient);
        return Optional.of(mapperOrder.toOrders(ordenes));
    }
    @Override
    @Transactional
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
              if (item.getProduct() == null || item.getProduct().getProductId() == null) {
                  throw new RuntimeException("Error: Cada detalle de la orden debe tener un ID de producto válido.");
              }
              if (item.getQuantity() == null || item.getQuantity() <= 0) {
                  throw new RuntimeException("¡Error! La cantidad del producto no puede ser cero ni negativa.");
              }
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
    @Transactional
    public Order despachcarYFacturar(long orderId,  String method){
        Orden ordenDB = crudOrden.findById(orderId)
                .orElseThrow(() -> new RuntimeException("La orden #" + orderId + " no existe"));
        if("COMPLETADA".equals(ordenDB.getEstadoProceso())){
            throw new RuntimeException("esta orden ya fue completada y facturada");
        }
        ordenDB.setEstadoProceso("COMPLETADA");
        Orden actualizada = crudOrden.save(ordenDB);
        service.emitirFactura(actualizada,method);
        return mapperOrder.toOrder(actualizada);
    }
    @Override
    @Transactional
    public Order pagarOrden(long orderId, String metodoPago) {
        Orden orden = crudOrden.findById(orderId)
                .orElseThrow(() -> new RuntimeException("La orden #" + orderId + " no existe"));

        if ("PAGADA".equals(orden.getEstadoProceso())) {
            throw new RuntimeException("Esta orden ya fue pagada.");
        }

        orden.setEstadoProceso("PAGADA");
        Orden ordenGuardada = crudOrden.save(orden);

        service.registrarPago(orderId, metodoPago);

        return mapperOrder.toOrder(ordenGuardada);
    }
    @Override
    @Transactional
    public void anularOrden(long orderId) {
        Orden orden = crudOrden.findById(orderId)
                .orElseThrow(() -> new RuntimeException("La orden #" + orderId + " no existe"));

        if ("COMPLETADA".equals(orden.getEstadoProceso()) || "PAGADA".equals(orden.getEstadoProceso())) {
            throw new RuntimeException("no puedes anular ni modificar una orden que ya fue facturada.");
        }

        orden.setEstado(false);
        crudOrden.save(orden);
    }
}
