package PruebaPractica.Bedoya.Domain.DTO;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private Long orderId;
    private Client client;
    private List<OrderItem> details;
    private LocalDate date;
    private Double total;
    private Boolean state = false;
    private String stateProcess;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<OrderItem> getDetails() {
        return details;
    }

    public void setDetails(List<OrderItem> details) {
        this.details = details;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getStateProcess() {
        return stateProcess;
    }

    public void setStateProcess(String stateProcess) {
        this.stateProcess = stateProcess;
    }

}
