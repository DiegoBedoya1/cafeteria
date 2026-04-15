package PruebaPractica.Bedoya.Domain.Service;

import PruebaPractica.Bedoya.Domain.DTO.Invoice;
import PruebaPractica.Bedoya.Domain.Repository.InvoiceRepository;
import PruebaPractica.Bedoya.Persistance.Crud.FacturaCrudRepository;
import PruebaPractica.Bedoya.Persistance.Entity.Factura;
import PruebaPractica.Bedoya.Persistance.Entity.Orden;
import PruebaPractica.Bedoya.Persistance.Mapper.InvoiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository repository;

    @Autowired
    private FacturaCrudRepository crud;

    @Autowired
    private InvoiceMapper mapper;

    public Invoice getInvoiceByOrderId(long orderId){
        return repository.getByOrder(orderId)
                .orElseThrow(() -> new RuntimeException("no existe factura para esta orden"));
    }

    public Invoice getInvoiceByNumber(String invoiceNumber){
        return repository.getByNumber(invoiceNumber)
                .orElseThrow(()-> new RuntimeException("no existe factura para "+invoiceNumber));
    }

    public List<Invoice> getInvoicesByPaymentMethod(String paymentMethod){
        return repository.getByMethod(paymentMethod);
    }

    @Transactional
    public Invoice emitirFactura(Orden orden, String metodo){
        if(crud.findByOrdenId(orden.getId()).isPresent()){
            throw new RuntimeException("la orden #"+orden.getId()+ " ya tiene factura emitida");
        }
        long totalFactura = crud.count();
        String numero = String.format("FAC-%06d",++totalFactura);
        Factura factura = new Factura();
        factura.setNumeroFactura(numero);
        factura.setMetodoPago(metodo);
        factura.setOrden(orden);
        factura.setEstado(true);
        factura.setTotal(orden.getTotal());
        factura.setFechaEmision(LocalDateTime.now());
        Factura generada = crud.save(factura);
        return mapper.toInvoice(generada);
    }

    @Transactional
    public void registrarPago(long orderId, String metodoPago) {
        Factura factura = crud.findByOrdenId(orderId)
                .orElseThrow(() -> new RuntimeException("No se encontró una factura para la orden #" + orderId));

        factura.setEstadoPago("PAGADA");
        factura.setMetodoPago(metodoPago);
        crud.save(factura);
    }
}
