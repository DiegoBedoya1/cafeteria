package PruebaPractica.Bedoya.web.controller;

import PruebaPractica.Bedoya.Domain.DTO.Invoice;
import PruebaPractica.Bedoya.Domain.Service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/invoices")
public class InvoiceController {
    @Autowired
    private InvoiceService service;

    @GetMapping("/order/{id}")
    public ResponseEntity<Invoice> getInvoiceByOrderId(@PathVariable long id){
        return new ResponseEntity<>(service.getInvoiceByOrderId(id), HttpStatus.OK);
    }
    @GetMapping("/number/{number}")
    public ResponseEntity<Invoice> getInvoiceByNumber(@PathVariable String number){
        return new ResponseEntity<>(service.getInvoiceByNumber(number), HttpStatus.OK);
    }

    @GetMapping("/method/{method}")
    public ResponseEntity<List<Invoice>> getInvoicesByMethod(@PathVariable String method){
        return new ResponseEntity<>(service.getInvoicesByPaymentMethod(method),HttpStatus.OK);
    }
}
