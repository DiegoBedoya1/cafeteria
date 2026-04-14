package PruebaPractica.Bedoya.Persistance.Crud;

import PruebaPractica.Bedoya.Persistance.Entity.Orden;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrdenCrudRepository  extends CrudRepository<Orden,Long> {
    List<Orden> findByClienteId(long idCliente);
}
