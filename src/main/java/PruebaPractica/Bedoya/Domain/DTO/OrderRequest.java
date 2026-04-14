package PruebaPractica.Bedoya.Domain.DTO;

import java.util.List;

public class OrderRequest {
    private Long clientId;
    private List<Long> productsIds;

    public Long getClienteId() {
        return clientId;
    }
    public void setClienteId(Long clientId) {
        this.clientId = clientId;
    }

    public List<Long> getProductoIds() {
        return productsIds;
    }
    public void setProductoIds(List<Long> productsIds) {
        this.productsIds = productsIds;
    }
}
