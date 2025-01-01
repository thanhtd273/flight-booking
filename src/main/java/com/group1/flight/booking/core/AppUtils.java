package com.group1.flight.booking.core;

import com.group1.flight.booking.dto.BookingInfo;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.ObjectUtils;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class AppUtils {

    public static void main(String[] args) {
        BookingInfo bookingInfo = new BookingInfo();
//        bookingInfo.get
        try {
            exportPdf();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

    }

    private AppUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean validateEmail(String email) {
        if (ObjectUtils.isEmpty(email)) {
            return false;
        }
        email = email.trim();
        if (!email.startsWith("postmaster@") && !email.startsWith("root@")) {
            Pattern pattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                return false;
            }
            String localPath = email.substring(0, email.indexOf("@"));
            return localPath.length() >= 5 && localPath.length() <= 32;
        }
        return false;
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        if (ObjectUtils.isEmpty(phoneNumber))
            return false;

        phoneNumber = phoneNumber.trim();
        if (!phoneNumber.startsWith("0") || phoneNumber.length() != 10)
            return false;
        return NumberUtils.isDigits(phoneNumber);
    }

    public static Long[] parseIdsFromStr(String str) {
        return (Long[]) Arrays.stream(str.split("-")).filter(NumberUtils::isCreatable).map(Long::parseLong).toArray();
    }

    public static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd MMM yyyy");
        return dateFormat.format(date);
    }

    public static String formatTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(date);
    }

    public static void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static String[] list2Array(List<String> list) {
        String[] arr = new String[list.size()];
        for (int i = 0; i < list.size(); i ++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    public static void exportPdf() throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("test.pdf"));
        document.open();
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, BaseColor.BLACK);
        Chunk chunk = new Chunk("Hello World", font);
        document.add(chunk);
        document.close();
    }
}