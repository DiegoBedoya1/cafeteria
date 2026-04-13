package PruebaPractica.Bedoya.Domain.Repository;


import PruebaPractica.Bedoya.Domain.DTO.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> getAll();
    Product save(Product product);

    Product update(long id, Product product);

}
