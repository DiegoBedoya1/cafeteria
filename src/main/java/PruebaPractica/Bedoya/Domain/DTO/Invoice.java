package PruebaPractica.Bedoya.Domain.DTO;

import java.time.LocalDateTime;

public class Invoice {
    private Long id;
    private String invoicesNumber;
    private LocalDateTime issueDate;
    private Double total;
    private String paymentMethod;
    private Boolean state;
    private Order order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoicesNumber() {
        return invoicesNumber;
    }

    public void setInvoicesNumber(String invoicesNumber) {
        this.invoicesNumber = invoicesNumber;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setEmissionDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
