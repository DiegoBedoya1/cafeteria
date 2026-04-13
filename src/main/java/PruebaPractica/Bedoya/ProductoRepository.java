package PruebaPractica.Bedoya;

import PruebaPractica.Bedoya.Domain.DTO.Product;
import PruebaPractica.Bedoya.Domain.Repository.ProductRepository;
import PruebaPractica.Bedoya.Persistance.Crud.ProductoCrudRepository;
import PruebaPractica.Bedoya.Persistance.Entity.Producto;
import PruebaPractica.Bedoya.Persistance.Mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public class ProductoRepository implements ProductRepository {
    @Autowired
    private ProductoCrudRepository crud;

    @Autowired
    private ProductMapper mapper;
    @Override
    public Product save(Product product){
        Producto producto = mapper.toProducto(product);
        return mapper.toProduct(crud.save(producto));
    }
    @Override
    public List<Product> getAll(){
        List<Producto> productos = (List<Producto>) crud.findAll();
        return mapper.toProducts(productos);
    }

    @Override
    public Product update(long id, Product product){
        Producto producto = crud.findById(id)
                .orElseThrow(()-> new RuntimeException("producto no encontrado"));
        producto.setDisponible(product.getAvailable());
        return mapper.toProduct(crud.save(producto));
    }

}
