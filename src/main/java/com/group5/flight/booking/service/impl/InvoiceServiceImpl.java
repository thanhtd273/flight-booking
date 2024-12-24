package com.group5.flight.booking.service.impl;

import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dao.BookingDao;
import com.group5.flight.booking.dao.InvoiceDao;
import com.group5.flight.booking.dto.InvoiceInfo;
import com.group5.flight.booking.model.Booking;
import com.group5.flight.booking.model.Flight;
import com.group5.flight.booking.model.Invoice;
import com.group5.flight.booking.service.ContactService;
import com.group5.flight.booking.service.FlightService;
import com.group5.flight.booking.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceDao invoiceDao;

    private final BookingDao bookingDao;

    private final FlightService flightService;

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceDao.findByDeletedFalse();
    }

    @Override
    public Invoice findByInvoiceId(Long invoiceId) {
        return invoiceDao.findByInvoiceIdAndDeletedFalse(invoiceId);
    }

    @Override
    public Invoice findByBookingId(Long bookingId) {
        return invoiceDao.findByBookingIdAndDeletedFalse(bookingId);
    }

    @Override
    public Invoice create(Long bookingId) throws LogicException {
        if (ObjectUtils.isEmpty(bookingId)) {
            throw new LogicException(ErrorCode.BLANK_FIELD);
        }


        Invoice invoice = new Invoice();
        Booking booking = bookingDao.findByBookingId(bookingId);
        Flight flight = flightService.findByFlightId(bookingId);
        invoice.setTotalAmount(booking.getNumOfPassengers() * flight.getBasePrice());
        invoice.setCreatedAt(new Date(System.currentTimeMillis()));
        invoice.setDeleted(false);

        return invoiceDao.save(invoice);
    }


    @Override
    public ErrorCode delete(Long invoiceId) {
        Invoice invoice = findByInvoiceId(invoiceId);
        if (ObjectUtils.isEmpty(invoice))
            return ErrorCode.DATA_NULL;
        invoice.setDeleted(true);
        invoice.setUpdatedAt(new Date(System.currentTimeMillis()));
        return ErrorCode.SUCCESS;
    }

    @Override
    public InvoiceInfo getInvoiceInfo(Long invoiceId) {
        Invoice invoice = findByInvoiceId(invoiceId);
        if (ObjectUtils.isEmpty(invoice)) return null;

        return new InvoiceInfo(invoiceId, invoice.getBookingId(), invoice.getTotalAmount());
    }

    @Override
    public InvoiceInfo getInvoiceInfoByBookingId(Long bookingId) {
        Invoice invoice = findByBookingId(bookingId);
        if (ObjectUtils.isEmpty(invoice)) return null;

        return new InvoiceInfo(invoice.getInvoiceId(), bookingId, invoice.getTotalAmount());
    }
}
