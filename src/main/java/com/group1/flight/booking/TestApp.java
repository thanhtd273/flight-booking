package com.group1.flight.booking;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;

public class TestApp {
    public static void main(String[] args) {
        String dest = "data/receipt/TravelokaReceipt.pdf";

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(dest));
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
            invoiceTable.addCell(createCell("Number: #1819037520398153009", Element.ALIGN_LEFT,
                    FontFactory.TIMES_ROMAN, 10, Rectangle.NO_BORDER));
            invoiceTable.addCell(createCell("Date: 21 thg 12 2024, 15:24 (Saturday)",
                    Element.ALIGN_LEFT, FontFactory.TIMES_ROMAN, 10, Rectangle.NO_BORDER));
            document.add(invoiceTable);

            // Payment Information
            document.add(createSection("PAYMENT INFORMATION", new BaseColor(242, 243, 255)));
            PdfPTable paymentTable = new PdfPTable(new float[]{2, 3, 2}); // Three columns: Code, Method, Status
            paymentTable.setWidthPercentage(100);
            paymentTable.setSpacingAfter(20);
            paymentTable.addCell(createCell("Reservation code: 1210070435", Element.ALIGN_LEFT,
                    FontFactory.TIMES_ROMAN, 10, Rectangle.NO_BORDER));
            paymentTable.addCell(createCell("Method: MoMo E-Wallet", Element.ALIGN_CENTER,
                    FontFactory.TIMES_ROMAN, 10, Rectangle.NO_BORDER));
            paymentTable.addCell(createCell("Status: Done", Element.ALIGN_RIGHT,
                    FontFactory.TIMES_ROMAN, 10, Rectangle.NO_BORDER));
            document.add(paymentTable);

            // Customer Information
            document.add(createSection("CUSTOMER INFORMATION", new BaseColor(242, 243, 255)));
            PdfPTable customerTable = new PdfPTable(1);
            customerTable.setWidthPercentage(100);
            customerTable.setSpacingAfter(20);
            customerTable.addCell(createCell("Full name: Dinh Thanh Trinh", Element.ALIGN_LEFT,
                    FontFactory.TIMES_ROMAN, 10, Rectangle.NO_BORDER));
            customerTable.addCell(createCell("Email: hinhtudien@gmail.com", Element.ALIGN_LEFT,
                    FontFactory.TIMES_ROMAN, 10, Rectangle.NO_BORDER));
            customerTable.addCell(createCell("Phone number: +84926272923", Element.ALIGN_LEFT,
                    FontFactory.TIMES_ROMAN, 10, Rectangle.NO_BORDER));
            document.add(customerTable);

            // Passenger Information
            document.add(createSection("PASSENGER INFORMATION", new BaseColor(242, 243, 255)));
            Paragraph passengerInfo = new Paragraph("MR Dinh Thanh Trinh", FontFactory.getFont(FontFactory.TIMES_ROMAN, 10));
            passengerInfo.setSpacingAfter(20);
            document.add(passengerInfo);

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
            transactionTable.addCell(createCell("1", Element.ALIGN_CENTER, FontFactory.TIMES_ROMAN, 10));
            transactionTable.addCell(createCell("Flight ticket ", Element.ALIGN_CENTER, FontFactory.TIMES_ROMAN, 10));
            transactionTable.addCell(createCell("Vietravel Airlines SGN - DAD | 20 thg 1, 2025", Element.ALIGN_LEFT, FontFactory.TIMES_ROMAN, 10));
            transactionTable.addCell(createCell("1", Element.ALIGN_CENTER, FontFactory.TIMES_ROMAN, 10));
            transactionTable.addCell(createCell("1.498.556", Element.ALIGN_RIGHT, FontFactory.TIMES_ROMAN, 10));
            transactionTable.addCell(createCell("1.498.556", Element.ALIGN_RIGHT, FontFactory.TIMES_ROMAN, 10));
            document.add(transactionTable);

            document.close();
            System.out.println("PDF Created successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static PdfPCell createCell(String content, int alignment, String fontName, int fontSize) {
        PdfPCell cell = new PdfPCell(new Phrase(content, FontFactory.getFont(fontName, fontSize)));
        cell.setHorizontalAlignment(alignment);
        return cell;
    }

    private static PdfPCell createCell(String content, int alignment, String fontName, int fontSize, int border) {
        PdfPCell cell = new PdfPCell(new Phrase(content, FontFactory.getFont(fontName, fontSize)));
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(border);
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
