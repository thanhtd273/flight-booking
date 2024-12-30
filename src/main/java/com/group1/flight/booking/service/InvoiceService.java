package com.group1.flight.booking.service;

import com.group1.flight.booking.core.ErrorCode;
import com.group1.flight.booking.core.exception.LogicException;
import com.group1.flight.booking.dto.InvoiceInfo;
import com.group1.flight.booking.model.Invoice;

import java.util.List;

public interface InvoiceService {

    List<Invoice> getAllInvoices();

    Invoice findByInvoiceId(Long invoiceId);

    Invoice findByBookingId(Long bookingId);

    Invoice create(Long bookingId) throws LogicException;

    ErrorCode delete(Long invoiceId);

    InvoiceInfo getInvoiceInfo(Long invoiceId);

    InvoiceInfo getInvoiceInfoByBookingId(Long bookingId);

}
