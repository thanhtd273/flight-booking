package com.group1.flight.booking.service.impl;

import com.group1.flight.booking.core.DataStatus;
import com.group1.flight.booking.core.ErrorCode;
import com.group1.flight.booking.core.exception.LogicException;

import com.group1.flight.booking.dao.BookingDao;
import com.group1.flight.booking.dao.ContactDao;
import com.group1.flight.booking.dao.FlightSeatPassengerDao;
import com.group1.flight.booking.dao.InvoiceDao;
import com.group1.flight.booking.dao.PassengerDao;
import com.group1.flight.booking.dto.BookingInfo;
import com.group1.flight.booking.dto.ContactInfo;
import com.group1.flight.booking.dto.InvoiceInfo;
import com.group1.flight.booking.dto.FlightInfo;
import com.group1.flight.booking.dto.PassengerInfo;
import com.group1.flight.booking.dto.SeatInfo;
import com.group1.flight.booking.model.Booking;
import com.group1.flight.booking.model.Contact;
import com.group1.flight.booking.model.Flight;
import com.group1.flight.booking.model.Passenger;
import com.group1.flight.booking.model.FlightSeatPassenger;
import com.group1.flight.booking.model.Invoice;

import com.group1.flight.booking.service.BookingService;
import com.group1.flight.booking.service.ContactService;
import com.group1.flight.booking.service.InvoiceService;
import com.group1.flight.booking.service.FlightService;
import com.group1.flight.booking.service.PassengerService;
import com.group1.flight.booking.service.SeatService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingDao bookingDao;

    private final FlightService flightService;

    private final ContactDao contactDao;

    private final ContactService contactService;

    private final PassengerDao passengerDao;

    private final PassengerService passengerService;

    private final InvoiceService invoiceService;

    private final InvoiceDao invoiceDao;

    private final SeatService seatService;

    private final FlightSeatPassengerDao flightSeatPassengerDao;

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
    @Transactional
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

        // Save contact
        Contact contact = new Contact();
        ContactInfo contactInfo = bookingInfo.getContactInfo();
        contact.setFirstName(contactInfo.getFirstName());
        contact.setLastName(contactInfo.getLastName());
        contact.setPhone(contactInfo.getPhone());
        contact.setEmail(contactInfo.getEmail());
        contact.setCreatedAt(new Date(System.currentTimeMillis()));
        contact.setDeleted(false);
        contact = contactDao.save(contact);
        bookingInfo.setContactId(contact.getContactId());

        for (int i = 0; i < bookingInfo.getNumOfPassengers(); i ++) {
            PassengerInfo passengerInfo = bookingInfo.getPassengerInfos()[i];
            SeatInfo seatInfo = bookingInfo.getSeatInfos()[i];

            // Save passenger info
            Passenger passenger = new Passenger();
            passenger.setNationalityId(passengerInfo.getNationalityId());
            passenger.setFirstName(passengerInfo.getFirstName());
            passenger.setLastName(passengerInfo.getLastName());
            passenger.setBirthday(passengerInfo.getBirthday());
            passenger.setCreatedAt(new Date(System.currentTimeMillis()));
            passenger.setDeleted(false);
            passenger = passengerDao.save(passenger);

            // Save flight seat passenger
            FlightSeatPassenger flightSeatPassenger = new FlightSeatPassenger();
            flightSeatPassenger.setFlightId(flightId);
            flightSeatPassenger.setSeatId(seatInfo.getSeatId());
            flightSeatPassenger.setPassengerId(passenger.getPassengerId());
            flightSeatPassengerDao.save(flightSeatPassenger);
        }

        // Save booking info
        Booking booking = new Booking();
        booking.setFlightId(flightId);
        booking.setNumOfPassengers(numOfPassengers);
        booking.setStatus(DataStatus.UNPAID);
        booking.setCreatedAt(new Date(System.currentTimeMillis()));
        booking = bookingDao.save(booking);

        Invoice invoice = new Invoice();
        invoice.setTotalAmount(booking.getNumOfPassengers() * flight.getBasePrice());
        invoice.setCreatedAt(new Date(System.currentTimeMillis()));
        invoice.setDeleted(false);
        invoice = invoiceDao.save(invoice);

        bookingInfo.setInvoiceId(invoice.getInvoiceId());
        bookingInfo.setInvoiceInfo(invoiceService.getInvoiceInfo(booking.getBookingId()));
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
        InvoiceInfo invoice = invoiceService.getInvoiceInfoByBookingId(bookingId);
        List<FlightSeatPassenger> flightSeatPassengers = flightSeatPassengerDao.findByFlightId(flight.getFlightId());
        PassengerInfo[] passengers = new PassengerInfo[flightSeatPassengers.size()];
        SeatInfo[] seatIds = new SeatInfo[flightSeatPassengers.size()];

        for (int i = 0; i < flightSeatPassengers.size(); i ++) {
            passengers[i] = passengerService.getPassengerInfo(flightSeatPassengers.get(i).getPassengerId());
            seatIds[i] = seatService.getSeatInfo(flightSeatPassengers.get(i).getSeatId(), flight.getFlightId());
        }

        return new BookingInfo(bookingId, booking.getBookingCode(), flight.getFlightId(), flight, flight.getDepartureAirportId(),
                flight.getDestinationAirportId(), flight.getDepartureDate(), contact.getContactId(), contact, booking.getNumOfPassengers(),
                passengers, seatIds, invoice.getInvoiceId(), invoice, booking.getPaymentMethod(), booking.getTicketNumber());
    }

    @Override
    public ErrorCode exportAndSendOnlineTicket(Long bookingId) throws LogicException, FileNotFoundException, DocumentException {
        BookingInfo bookingInfo = getBookingInfo(bookingId);
        // TODO
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(String.format("invoice_%s %s_%d.pdf",
                bookingInfo.getContactInfo().getFirstName(), bookingInfo.getContactInfo().getLastName(), System.currentTimeMillis())));
        return null;
    }

    @Override
    public ErrorCode pay(Long bookingId) throws LogicException {
        Booking booking = findByBookingId(bookingId);
        if (ObjectUtils.isEmpty(booking)) {
            throw new LogicException(ErrorCode.DATA_NULL, "Booking does not exist");
        }
        booking.setStatus(DataStatus.PAID);
        bookingDao.save(booking);
        return ErrorCode.SUCCESS;
    }
}
