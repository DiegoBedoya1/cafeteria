package PruebaPractica.Bedoya.Persistance.Crud;

import PruebaPractica.Bedoya.Persistance.Entity.Producto;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductoCrudRepository extends CrudRepository<Producto, Long> {
    Optional<Producto> findById(long id);
}
