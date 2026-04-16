package PruebaPractica.Bedoya.web.controller;

import PruebaPractica.Bedoya.Domain.DTO.Invoice;
import PruebaPractica.Bedoya.Domain.DTO.Order;
import PruebaPractica.Bedoya.Domain.DTO.SplitRequest;
import PruebaPractica.Bedoya.Domain.Service.OrderService;
import PruebaPractica.Bedoya.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @Autowired
    private OrdenRepository ordenRepository;

    @GetMapping("/clients/{id}")
    public ResponseEntity<List<Order>> getOrdersByClient(@PathVariable int id){
        if(service.getByClientId(id).isPresent()){
            return new ResponseEntity<>(service.getByClientId(id).get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/{id}")
    public ResponseEntity<List<Order>> saveOrders(
            @PathVariable long id,
            @RequestBody List<Order> orders
    ){
        return new ResponseEntity<>(service.saveOrders(id,orders),HttpStatus.CREATED);
    }
    @PutMapping("/completar/{id}")
    public ResponseEntity<Order> completarOrden(
            @PathVariable("id") long orderId,
            @RequestParam("metodoPago") String metodoPago) {

        try {
            Order ordenCompletada = ordenRepository.despachcarYFacturar(orderId, metodoPago.toUpperCase());
            return new ResponseEntity<>(ordenCompletada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/pagar/{id}")
    public ResponseEntity<Order> pagarOrden(
            @PathVariable("id") long orderId,
            @RequestParam("metodo") String metodo) {
        try {
            Order ordenPagada = ordenRepository.pagarOrden(orderId, metodo.toUpperCase());
            return new ResponseEntity<>(ordenPagada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/anular/{id}")
    public ResponseEntity<String> anularOrden(@PathVariable("id") long orderId) {
        try {
            return new ResponseEntity<>("Orden anulada correctamente (Eliminación lógica)", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/dividir/{id}")
    public ResponseEntity<List<Order>> dividir(@PathVariable("id") long ordenFacturaId,
                                                 @RequestBody List<SplitRequest> splits){
        return ResponseEntity.ok(ordenRepository.dividir(ordenFacturaId,splits));
    }



}
