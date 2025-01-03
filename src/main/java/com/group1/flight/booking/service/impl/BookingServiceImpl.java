package com.group1.flight.booking.service.impl;

import com.group1.flight.booking.core.AppUtils;
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

import com.group1.flight.booking.service.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    private final MailService mailService;

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
    public Booking create(BookingInfo bookingInfo)
            throws LogicException, MessagingException, DocumentException, FileNotFoundException {
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
        Contact contact = createContact(bookingInfo.getContactInfo());
        bookingInfo.setContactId(contact.getContactId());

        for (int i = 0; i < bookingInfo.getNumOfPassengers(); i ++) {
            createPassengersAndSeats(flightId, bookingInfo.getPassengerInfos()[i], bookingInfo.getSeatInfos()[i]);
        }

        // Save booking info
        Booking booking = new Booking();
        booking.setFlightId(flightId);
        booking.setBookingCode(AppUtils.generateUniqueNumericCode());
        booking.setNumOfPassengers(numOfPassengers);
        booking.setStatus(DataStatus.UNPAID);
        booking.setCreatedAt(new Date(System.currentTimeMillis()));
        booking = bookingDao.save(booking);


        Invoice invoice = createInvoice(booking.getNumOfPassengers() * flight.getBasePrice());
        bookingInfo.setInvoiceId(invoice.getInvoiceId());
        bookingInfo.setInvoiceInfo(invoiceService.getInvoiceInfo(booking.getBookingId()));
        booking = bookingDao.save(booking);

        sendReceiptToEmail(bookingInfo);
        return booking;
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
    public ErrorCode pay(Long bookingId) throws LogicException {
        Booking booking = findByBookingId(bookingId);
        if (ObjectUtils.isEmpty(booking)) {
            throw new LogicException(ErrorCode.DATA_NULL, "Booking does not exist");
        }
        booking.setStatus(DataStatus.PAID);
        bookingDao.save(booking);
        return ErrorCode.SUCCESS;
    }

    private Contact createContact(ContactInfo contactInfo) {
        Contact contact = new Contact();
        contact.setFirstName(contactInfo.getFirstName());
        contact.setLastName(contactInfo.getLastName());
        contact.setPhone(contactInfo.getPhone());
        contact.setEmail(contactInfo.getEmail());
        contact.setCreatedAt(new Date(System.currentTimeMillis()));
        contact.setDeleted(false);
        return contactDao.save(contact);
    }

    private void createPassengersAndSeats(Long flightId, PassengerInfo passengerInfo, SeatInfo seatInfo) {
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

    private Invoice createInvoice(Float totalAmount) {
        Invoice invoice = new Invoice();
        invoice.setTotalAmount(totalAmount);
        invoice.setCreatedAt(new Date(System.currentTimeMillis()));
        invoice.setDeleted(false);
        return invoiceDao.save(invoice);
    }

    private void sendReceiptToEmail(BookingInfo bookingInfo)
            throws MessagingException, DocumentException, FileNotFoundException {
            String body = String.format("Hi %s, %nYour booking request has been successfully confirmed. Please find the receipt in the attached file",
                    bookingInfo.getContactInfo().getFirstName());
            String attachedFilePath = exportReceipt(bookingInfo);
            File attachedFile = new File(attachedFilePath);
            mailService.sendMailWithAttachment(bookingInfo.getContactInfo().getEmail(), "[FB] Flight Receipt", body, attachedFile);
    }

    private String exportReceipt(BookingInfo bookingInfo) throws DocumentException, FileNotFoundException {
        String filePath = String.format("data/receipt/Receipt_%d.pdf", System.currentTimeMillis());


        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        // Header
        Paragraph header = new Paragraph("RECEIPT", FontFactory.getFont(FontFactory.TIMES_BOLD, 16, BaseColor.BLUE));
        header.setAlignment(Element.ALIGN_LEFT);
        header.setSpacingAfter(10);
        document.add(header);

        // Invoice Information: Number and Date on separate rows
        PdfPTable invoiceTable = new PdfPTable(1);
        invoiceTable.setWidthPercentage(100);
        invoiceTable.setSpacingAfter(20);
        invoiceTable.addCell(createCellWithoutBorder("Number: #1819037520398153009", Element.ALIGN_LEFT
        ));
        DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
        invoiceTable.addCell(createCellWithoutBorder(String.format("Date: %s", dateFormat.format(new Date())),
                Element.ALIGN_LEFT));
        document.add(invoiceTable);

        // Payment Information
        document.add(createSection("PAYMENT INFORMATION", new BaseColor(242, 243, 255)));
        PdfPTable paymentTable = new PdfPTable(new float[]{2, 3, 2}); // Three columns: Code, Method, Status
        paymentTable.setWidthPercentage(100);
        paymentTable.setSpacingAfter(20);
        paymentTable.addCell(createCellWithoutBorder("Reservation code: 1210070435", Element.ALIGN_LEFT
        ));
        paymentTable.addCell(createCellWithoutBorder(String.format("Method: %s", "Internet Banking"),
                Element.ALIGN_CENTER));
        paymentTable.addCell(createCellWithoutBorder("Status: Done", Element.ALIGN_RIGHT
        ));
        document.add(paymentTable);

        // Customer Information
        document.add(createSection("CUSTOMER INFORMATION", new BaseColor(242, 243, 255)));
        PdfPTable customerTable = new PdfPTable(1);
        customerTable.setWidthPercentage(100);
        customerTable.setSpacingAfter(20);
        ContactInfo contactInfo = bookingInfo.getContactInfo();
        customerTable.addCell(createCellWithoutBorder(String.format("Full name: %s %s", contactInfo.getFirstName(),
                contactInfo.getLastName()), Element.ALIGN_LEFT));
        customerTable.addCell(createCellWithoutBorder(String.format("Email: %s", contactInfo.getEmail()),
                Element.ALIGN_LEFT));
        customerTable.addCell(createCellWithoutBorder(String.format("Phone number: %s", contactInfo.getPhone()),
                Element.ALIGN_LEFT));
        document.add(customerTable);

        // Passenger Information
        document.add(createSection("PASSENGER INFORMATION", new BaseColor(242, 243, 255)));
        PassengerInfo[] passengerInfos = bookingInfo.getPassengerInfos();
        for (PassengerInfo passengerInfo: passengerInfos) {
            Paragraph passengerInfoPara = new Paragraph(String.format("%s %s", passengerInfo.getFirstName(), passengerInfo.getLastName()),
                    FontFactory.getFont(FontFactory.TIMES_ROMAN, 10));
            passengerInfoPara.setSpacingAfter(20);
            document.add(passengerInfoPara);
        }

        // Transaction Information
        document.add(createSection("TRANSACTION INFORMATION", new BaseColor(242, 243, 255)));
        PdfPTable transactionTable = new PdfPTable(new float[]{1, 2, 4, 1, 2, 2});
        transactionTable.setWidthPercentage(100);
        transactionTable.setSpacingAfter(20);
        transactionTable.addCell(createHeaderCell("Sequence"));
        transactionTable.addCell(createHeaderCell("Category"));
        transactionTable.addCell(createHeaderCell("Description"));
        transactionTable.addCell(createHeaderCell("Quantity"));
        transactionTable.addCell(createHeaderCell("Unit Price"));
        transactionTable.addCell(createHeaderCell("Total Amount"));

        // Add transaction row
        transactionTable.addCell(createCell("1", Element.ALIGN_CENTER));
        transactionTable.addCell(createCell("Flight ticket ", Element.ALIGN_CENTER));
        FlightInfo flightInfo = bookingInfo.getFlightInfo();
        transactionTable.addCell(createCell(String.format("%s %s - %s | %s",
                flightInfo.getAirline().getName(), flightInfo.getDepartureAirportInfo().getName(),
                flightInfo.getDestinationAirportInfo().getName(), new SimpleDateFormat("dd MMMM yyyy").format(flightInfo.getDepartureDate())),
                Element.ALIGN_LEFT));
        transactionTable.addCell(createCell(String.valueOf(flightInfo.getNumOfPassengers()), Element.ALIGN_CENTER));
        transactionTable.addCell(createCell(String.valueOf(flightInfo.getBasePrice()), Element.ALIGN_RIGHT));
        transactionTable.addCell(createCell(String.valueOf(bookingInfo.getNumOfPassengers() * flightInfo.getBasePrice()),
                Element.ALIGN_RIGHT));
        document.add(transactionTable);

        document.close();

        return filePath;
    }

    private static PdfPCell createCell(String content, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(content, FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
        cell.setHorizontalAlignment(alignment);
        return cell;
    }

    private static PdfPCell createCellWithoutBorder(String content, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(content, FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private static PdfPCell createHeaderCell(String content) {
        PdfPCell cell = new PdfPCell(new Phrase(content, FontFactory.getFont(FontFactory.TIMES_BOLD, 10)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new BaseColor(242, 243, 255));
        return cell;
    }

    private static PdfPTable createSection(String title, BaseColor bgColor) {
        PdfPTable sectionTable = new PdfPTable(1);
        sectionTable.setWidthPercentage(100);
        PdfPCell sectionCell = new PdfPCell(new Phrase(title, FontFactory.getFont(FontFactory.TIMES_BOLD, 12, BaseColor.BLACK)));
        sectionCell.setBackgroundColor(bgColor);
        sectionCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        sectionCell.setPadding(5);
        sectionCell.setBorder(Rectangle.NO_BORDER);
        sectionTable.addCell(sectionCell);
        return sectionTable;
    }
}
