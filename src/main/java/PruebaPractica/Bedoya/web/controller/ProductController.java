package PruebaPractica.Bedoya.web.controller;

import PruebaPractica.Bedoya.Domain.DTO.Product;
import PruebaPractica.Bedoya.Domain.Service.ProductService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductController {
    @Autowired
    private ProductService service;

    @PostMapping("/new")
    public ResponseEntity<Product> crearProducto(@RequestBody Product product){
        return new ResponseEntity<>(service.save(product), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> obtenerTodos(){
        return new ResponseEntity<>(service.getAll(),HttpStatus.OK);
    }

   @PutMapping("/put/{id}")
    public ResponseEntity<Product> actualizar(
            @PathVariable long id,
            @RequestBody Product product
   ){
        return new ResponseEntity<>(service.update(id,product),HttpStatus.OK);
   }
}
