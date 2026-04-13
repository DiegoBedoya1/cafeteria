package PruebaPractica.Bedoya.Persistance.Entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "orden_producto")
public class OrdenProducto {
    @EmbeddedId
    private OrdenProductoPK id;

    public OrdenProductoPK getId() {
        return id;
    }

    public void setId(OrdenProductoPK id) {
        this.id = id;
    }
}
