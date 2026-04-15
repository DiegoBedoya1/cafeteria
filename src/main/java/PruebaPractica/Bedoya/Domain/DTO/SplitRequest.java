package PruebaPractica.Bedoya.Domain.DTO;

import java.util.List;

public class SplitRequest {
    private String paymentMethod;
    private List<SplitDetail> details;

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<SplitDetail> getDetails() {
        return details;
    }

    public void setDetails(List<SplitDetail> details) {
        this.details = details;
    }
}
