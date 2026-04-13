package PruebaPractica.Bedoya.Persistance.Crud;

import PruebaPractica.Bedoya.Persistance.Entity.Cliente;
import PruebaPractica.Bedoya.Persistance.Entity.Orden;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteCrudRepository extends CrudRepository<Cliente, Long> {
    Optional<Cliente> findById(long id);
}
