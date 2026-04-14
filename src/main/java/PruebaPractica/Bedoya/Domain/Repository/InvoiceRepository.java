package PruebaPractica.Bedoya.Domain.Repository;

import PruebaPractica.Bedoya.Domain.DTO.Invoice;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository {
    Optional<Invoice> getByOrder(long orderId);
    Optional <Invoice> getByNumber(String invoiceNumber);
    List<Invoice> getByMethod(String paymentMethod);
}
