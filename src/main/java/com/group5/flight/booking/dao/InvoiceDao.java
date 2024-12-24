package com.group5.flight.booking.dao;

import com.group5.flight.booking.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceDao extends JpaRepository<Invoice, Long> {
    List<Invoice> findByDeletedFalse();

    Invoice findByInvoiceIdAndDeletedFalse(Long invoiceId);

    Invoice findByBookingIdAndDeletedFalse(Long bookingId);
}
