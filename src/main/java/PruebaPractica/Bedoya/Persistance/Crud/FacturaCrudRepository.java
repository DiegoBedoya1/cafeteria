package PruebaPractica.Bedoya.Persistance.Crud;

import PruebaPractica.Bedoya.Persistance.Entity.Factura;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FacturaCrudRepository extends CrudRepository<Factura,Long> {
    Optional<Factura> findByOrdenId(long ordenId);
    Optional<Factura> findByNumeroFactura(String numeroFactura);
    List<Factura> findByMetodoPago(String metodoPago);
}
