package PruebaPractica.Bedoya;

import PruebaPractica.Bedoya.Domain.DTO.Order;
import PruebaPractica.Bedoya.Domain.DTO.SplitDetail;
import PruebaPractica.Bedoya.Domain.DTO.SplitRequest;
import PruebaPractica.Bedoya.Domain.Repository.OrderRepository;
import PruebaPractica.Bedoya.Domain.Service.InvoiceService;
import PruebaPractica.Bedoya.Persistance.Crud.ClienteCrudRepository;
import PruebaPractica.Bedoya.Persistance.Crud.OrdenCrudRepository;
import PruebaPractica.Bedoya.Persistance.Crud.ProductoCrudRepository;
import PruebaPractica.Bedoya.Persistance.Entity.*;
import PruebaPractica.Bedoya.Persistance.Mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

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

    @Override
    @Transactional
    public List<Order> dividir(long ordenPadreId, List<SplitRequest> splits){
        Orden padre = crudOrden.findById(ordenPadreId)
                .orElseThrow(() -> new RuntimeException("la orden "+ordenPadreId+ " no existe"));
        int cantidadProductos = padre.getDetalles().stream()
                .mapToInt(OrdenProducto::getCantidad)
                .sum();
        if(cantidadProductos<=1){
            throw new RuntimeException("no se pueden dividir ordenes con solo un elemento");
        }
        if(!padre.getCliente().getEstado()){
            throw new RuntimeException("el cliente no esta disponible");
        }
        if(!padre.getEstado()){
            throw new RuntimeException("esta orden no esta disponible");
        }
        if(padre.getEstadoProceso().equals("DIVIDIDA") || padre.getEstadoProceso().equals("PAGADA")) {
            throw new RuntimeException("No se puede dividir una orden que ya está pagada o dividida");
        }
        List<Orden> ordenes = new ArrayList<>();
        Map<Long, Integer> restanteMap = new HashMap<>();
        for (OrdenProducto op : padre.getDetalles()) {
            restanteMap.put(op.getProducto().getId(), op.getCantidad());
        }
       for(SplitRequest split: splits){
           Orden hija = new Orden();
           hija.setOrdenPadre(padre);
           hija.setFecha(padre.getFecha());
           hija.setOrdenPadreId(padre.getId());
           hija.setCliente(padre.getCliente());
           hija.setEstadoProceso("POR_PAGAR");
           hija.setEstado(true);
           double totalHija = 0.0;
           List<OrdenProducto> detallesHija = new ArrayList<>();
           for(SplitDetail detail: split.getDetails()){
               if(detail.getQuantity()<=0) {
                   throw new RuntimeException("no se admiten cantidades negativas o 0");
               }
               OrdenProducto detallePadre = padre.getDetalles().stream()
                       .filter(d -> d.getProducto().getId().equals(detail.getProductId()))
                       .findFirst()
                       .orElseThrow(()-> new RuntimeException("el  producto no forma parte de la cuenta original"));
               if(detail.getQuantity()> detallePadre.getCantidad()){
                   throw new RuntimeException("estas intentando pagar mas de lo que dice la cuenta");
               }
               int loQueHabia = restanteMap.get(detail.getProductId());
               restanteMap.put(detail.getProductId(), loQueHabia - detail.getQuantity());
               Producto producto = productoCrudRepository.findById(detail.getProductId())
                       .orElseThrow(() -> new RuntimeException("El producto no existe"));
               if(!(producto.getDisponible() || producto.getEstado())){
                   throw new RuntimeException("el producto no esta disponible o no tiene stock");
               }
               OrdenProducto detalle = new OrdenProducto();
               detalle.setOrden(hija);
               detalle.setProducto(producto);
               detalle.setCantidad(detail.getQuantity());
               totalHija+= producto.getPrecio() * detail.getQuantity();
               detallesHija.add(detalle);
           }
           hija.setTotal(totalHija);
           hija.setDetalles(detallesHija);
           Orden guardada = crudOrden.save(hija);
           service.emitirFactura(guardada,"PENDIENTE");
           ordenes.add(guardada);
       }
        List<OrdenProducto> detallesRestante = new ArrayList<>();
        double totalRestante = 0.0;
        Orden ordenRestante = new Orden();
        ordenRestante.setCliente(padre.getCliente());
        ordenRestante.setFecha(padre.getFecha());
        ordenRestante.setEstado(true);
        ordenRestante.setEstadoProceso("PENDIENTE");
        ordenRestante.setOrdenPadreId(padre.getId());

        for (Map.Entry<Long, Integer> entry : restanteMap.entrySet()) {
            if (entry.getValue() > 0) {
                Producto prod = productoCrudRepository.findById(entry.getKey()).get();
                OrdenProducto op = new OrdenProducto();
                op.setOrden(ordenRestante);
                op.setProducto(prod);
                op.setCantidad(entry.getValue());
                detallesRestante.add(op);
                totalRestante += (prod.getPrecio() * entry.getValue());
            }
        }
        if (!detallesRestante.isEmpty()) {
            ordenRestante.setDetalles(detallesRestante);
            ordenRestante.setTotal(totalRestante);
            Orden restanteGuardada = crudOrden.save(ordenRestante);
            ordenes.add(restanteGuardada);
        }
       padre.setEstadoProceso("DIVIDIDA");
       crudOrden.save(padre);
       ordenes.add(0,padre);
       return mapperOrder.toOrders(ordenes);
    }
}



