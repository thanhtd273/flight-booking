package com.group5.flight.booking.service.impl;

import com.group5.flight.booking.core.DataStatus;
import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dao.BookingDao;
import com.group5.flight.booking.dao.BookingPassengerDao;
import com.group5.flight.booking.dto.ContactInfo;
import com.group5.flight.booking.dto.BookingDetail;
import com.group5.flight.booking.dto.BookingInfo;
import com.group5.flight.booking.dto.FlightInfo;
import com.group5.flight.booking.dto.InvoiceInfo;
import com.group5.flight.booking.dto.PassengerInfo;
import com.group5.flight.booking.model.Contact;
import com.group5.flight.booking.model.Booking;
import com.group5.flight.booking.model.BookingPassenger;
import com.group5.flight.booking.model.Flight;
import com.group5.flight.booking.model.Passenger;
import com.group5.flight.booking.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingDao bookingDao;

    private final FlightService flightService;

    private final ContactService contactService;

    private final PassengerService passengerService;

    private final InvoiceService invoiceService;

    private final BookingPassengerDao bookingPassengerDao;

    @Override
    public List<Booking> getAllBookings() {
        return bookingDao.findALlBookings();
    }

    @Override
    public Booking findByBookingId(Long bookingId) throws LogicException {
        if (ObjectUtils.isEmpty(bookingId)) {
            throw new LogicException(ErrorCode.ID_NULL, "Booking id is empty");
        }
        return bookingDao.findByBookingId(bookingId);
    }

    @Override
    public Booking create(BookingInfo bookingInfo) throws LogicException {
        if (ObjectUtils.isEmpty(bookingInfo)) {
            throw new LogicException(ErrorCode.DATA_NULL, "Booking info is null");
        }
        Long flightId = bookingInfo.getFlightId();
        Integer numOfPassengers = bookingInfo.getNumOfPassengers();
        if (ObjectUtils.isEmpty(flightId) || ObjectUtils.isEmpty(numOfPassengers)) {
            throw new LogicException(ErrorCode.ID_NULL, "Flight id and number of passengers are required");
        }
        Flight flight = flightService.findByFlightId(flightId);
        if (ObjectUtils.isEmpty(flight)) {
            throw new LogicException(ErrorCode.DATA_NULL, "Flight does not exist");
        }

        Booking booking = new Booking();
        booking.setFlightId(flightId);
        booking.setNumOfPassengers(numOfPassengers);
        booking.setStatus(DataStatus.FLIGHT_CHOSEN);
        booking.setCreatedAt(new Date(System.currentTimeMillis()));

        return bookingDao.save(booking);
    }

    @Override
    @Transactional
    public Booking fillOutBookingDetail(Long bookingId, BookingDetail bookingDetail) throws LogicException {
        if (ObjectUtils.isEmpty(bookingDetail) || ObjectUtils.isEmpty(bookingId)) {
            throw new LogicException(ErrorCode.DATA_NULL);
        }
        if (bookingDetail.isAllNull()) {
            throw new LogicException(ErrorCode.BLANK_FIELD, "Contact info and passengers info are required");
        }

        Booking booking = findByBookingId(bookingId);
        if (ObjectUtils.isEmpty(booking)) {
            throw new LogicException(ErrorCode.DATA_NULL, "Booking does not exist");
        }

        if (booking.getNumOfPassengers() != bookingDetail.getPassengers().size()) {
            throw new LogicException(ErrorCode.FAIL, "Number of passengers in booking detail is not equal to reserved seats");
        }

        ContactInfo contactInfo = bookingDetail.getContact();
        Contact contact = contactService.create(contactInfo);
        booking.setContactId(contact.getContactId());
        for (PassengerInfo passengerInfo: bookingDetail.getPassengers()) {
            Passenger passenger = passengerService.create(passengerInfo);
            BookingPassenger bookingPassenger = new BookingPassenger();
            bookingPassenger.setBookingId(bookingId);
            bookingPassenger.setPassengerId(passenger.getPassengerId());
            bookingPassengerDao.save(bookingPassenger);
        }
        booking.setStatus(DataStatus.FILL_OUT_INFO);
        booking.setUpdatedAt(new Date(System.currentTimeMillis()));

        return bookingDao.save(booking);
    }

    @Override
    public ErrorCode delete(Long bookingId) throws LogicException {
        Booking booking = findByBookingId(bookingId);
        if (ObjectUtils.isEmpty(booking))
            return ErrorCode.DATA_NULL;
        booking.setStatus(DataStatus.DELETED);
        booking.setUpdatedAt(new Date(System.currentTimeMillis()));
        bookingDao.save(booking);
        return ErrorCode.SUCCESS;
    }

    @Override
    public BookingInfo getBookingInfo(Long bookingId) throws LogicException {
        Booking booking = findByBookingId(bookingId);
        if (ObjectUtils.isEmpty(booking)) {
            throw new LogicException(ErrorCode.DATA_NULL, "Booking does not exist");
        }
        FlightInfo flight = flightService.getFlightInfo(booking.getFlightId());
        ContactInfo contact = contactService.getContactInfo(booking.getContactId());
        InvoiceInfo invoice = invoiceService.getInvoiceInfo(booking.getInvoiceId());
        return new BookingInfo(booking.getBookingCode(), booking.getFlightId(), flight, booking.getContactId(),
                contact, booking.getInvoiceId(), invoice, booking.getPaymentMethod(),
                booking.getTicketNumber(), booking.getNumOfPassengers());
    }

    @Override
    public ErrorCode exportAndSendOnlineTicket(Long bookingId) throws LogicException {
        return null;
    }

    @Override
    public ErrorCode pay(Long bookingId) throws LogicException {

        return ErrorCode.SUCCESS;
    }
}
