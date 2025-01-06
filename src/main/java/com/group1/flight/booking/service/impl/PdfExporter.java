package com.group1.flight.booking.service.impl;

import com.group1.flight.booking.dto.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PdfExporter {

    private PdfExporter() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static String exportReceipt(BookingInfo bookingInfo) throws DocumentException, FileNotFoundException {
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

    public static String exportETicket(BookingInfo bookingInfo) throws FileNotFoundException, DocumentException {
        String filePath = String.format("data/e_ticket/e_ticket_%d.pdf", System.currentTimeMillis());

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        Paragraph title = new Paragraph("E-ticket", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Paragraph subtitle = new Paragraph("Departure Flight", FontFactory.getFont(FontFactory.HELVETICA, 14));
        subtitle.setAlignment(Element.ALIGN_CENTER);
        subtitle.setSpacingAfter(10);
        document.add(subtitle);

        PdfPTable flightTable = new PdfPTable(2);
        flightTable.setWidthPercentage(100);
        flightTable.setSpacingBefore(10);

        FlightInfo flightInfo = bookingInfo.getFlightInfo();
        addCellToTable(flightTable, "Airline:", Element.ALIGN_LEFT, FontFactory.HELVETICA_BOLD);
        addCellToTable(flightTable, flightInfo.getAirline().getName(), Element.ALIGN_LEFT);

        addCellToTable(flightTable, "Flight Number:", Element.ALIGN_LEFT, FontFactory.HELVETICA_BOLD);
        addCellToTable(flightTable, flightInfo.getFlightId().toString(), Element.ALIGN_LEFT);

        addCellToTable(flightTable, "Date:", Element.ALIGN_LEFT, FontFactory.HELVETICA_BOLD);
        addCellToTable(flightTable, new SimpleDateFormat("EEEE, dd MMMM yyyy").format(flightInfo.getDepartureDate()),
                Element.ALIGN_LEFT);

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        AirportInfo departureAirport = flightInfo.getDepartureAirportInfo();
        addCellToTable(flightTable, "Departure:", Element.ALIGN_LEFT, FontFactory.HELVETICA_BOLD);
        addCellToTable(flightTable, String.format("%s - %s City (%s) %s",
                timeFormat.format(flightInfo.getDepartureDate()), departureAirport.getCityInfo().getName(),
                departureAirport.getAirportCode(), departureAirport.getName()
                ), Element.ALIGN_LEFT);

        AirportInfo destinationAirport = flightInfo.getDestinationAirportInfo();
        addCellToTable(flightTable, "Arrival:", Element.ALIGN_LEFT, FontFactory.HELVETICA_BOLD);
        addCellToTable(flightTable, String.format("%s - %s City (%s) %s",
                timeFormat.format(flightInfo.getDepartureDate()), destinationAirport.getCityInfo().getName(),
                destinationAirport.getAirportCode(), destinationAirport.getName()
        ), Element.ALIGN_LEFT);

        addCellToTable(flightTable, "Booking Code (PNR):", Element.ALIGN_LEFT, FontFactory.HELVETICA_BOLD);
        addCellToTable(flightTable, String.valueOf(bookingInfo.getBookingCode()), Element.ALIGN_LEFT);

        document.add(flightTable);

        Paragraph passengerTitle = new Paragraph("Passenger Details", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14));
        passengerTitle.setSpacingBefore(10);
        passengerTitle.setSpacingAfter(5);
        document.add(passengerTitle);

        PdfPTable passengerTable = new PdfPTable(new float[]{1, 4, 2});
        passengerTable.setWidthPercentage(100);
        passengerTable.setSpacingBefore(5);

        addCellToTable(passengerTable, "No.", Element.ALIGN_CENTER, FontFactory.HELVETICA_BOLD);
        addCellToTable(passengerTable, "Passenger(s)", Element.ALIGN_CENTER, FontFactory.HELVETICA_BOLD);
        addCellToTable(passengerTable, "Route", Element.ALIGN_CENTER, FontFactory.HELVETICA_BOLD);

        String str = String.format("%s - %s", flightInfo.getDepartureAirportInfo().getAirportCode(),
                flightInfo.getDestinationAirportInfo().getAirportCode());
        for (int i = 0; i < bookingInfo.getPassengerInfos().length; i ++) {
            addCellToTable(passengerTable, String.valueOf(i + 1), Element.ALIGN_CENTER);
            PassengerInfo passengerInfo = bookingInfo.getPassengerInfos()[i];
            addCellToTable(passengerTable, String.format("%s %s",passengerInfo.getFirstName(),
                            passengerInfo.getLastName()), Element.ALIGN_LEFT);
            addCellToTable(passengerTable, str, Element.ALIGN_CENTER);
        }


        document.add(passengerTable);

        Paragraph note = new Paragraph("Check-in at least 90 minutes before departure", FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.RED));
        note.setSpacingBefore(10);
        document.add(note);

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

    private static void addCellToTable(PdfPTable table, String content, int alignment) {
        addCellToTable(table, content, alignment, FontFactory.HELVETICA);
    }

    private static void addCellToTable(PdfPTable table, String content, int alignment, String font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, FontFactory.getFont(font, 10)));
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
    }
}
