package PruebaPractica.Bedoya.Persistance.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class OrdenProductoPK implements Serializable {
    @Column(name = "orden_id")
    private Long ordenId;

    @Column(name = "producto_id")
    private Long productoId;

    public Long getOrdenId() {
        return ordenId;
    }

    public void setOrdenId(Long ordenId) {
        this.ordenId = ordenId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }
}
