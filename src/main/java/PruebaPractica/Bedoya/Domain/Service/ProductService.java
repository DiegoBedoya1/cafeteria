package PruebaPractica.Bedoya.Domain.Service;

import PruebaPractica.Bedoya.Domain.DTO.Product;
import PruebaPractica.Bedoya.Domain.Repository.ProductRepository;
import PruebaPractica.Bedoya.Persistance.Crud.ProductoCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;
    public List<Product> getAll(){
        return repository.getAll();
    }

    public Product save(Product product){
        return repository.save(product);
    }

    public Product update(long id, Product product){
        return repository.update(id,product);
    }
}
