package com.group5.flight.booking.service;

import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dto.InvoiceInfo;
import com.group5.flight.booking.model.Invoice;

import java.util.List;

public interface InvoiceService {

    List<Invoice> getAllInvoices();

    Invoice findByInvoiceId(Long invoiceId);

    Invoice create(Long bookingId) throws LogicException;

    ErrorCode delete(Long invoiceId);

    InvoiceInfo getInvoiceInfo(Long invoiceId);

}
