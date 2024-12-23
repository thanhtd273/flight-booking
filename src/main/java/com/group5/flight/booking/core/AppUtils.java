package com.group5.flight.booking.core;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class AppUtils {
    public static void main(String[] args) {
        System.out.print(formatDate(new Date()));
    }

    private AppUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean validateEmail(String email) {
        if (ObjectUtils.isEmpty(email)) {
            return false;
        } else {
            email = email.trim();
            if (!email.startsWith("postmaster@") && !email.startsWith("root@")) {
                Pattern pattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
                Matcher matcher = pattern.matcher(email);
                if (!matcher.matches()) {
                    return false;
                } else {
                    String localPath = email.substring(0, email.indexOf("@"));
                    return localPath.length() >= 5 && localPath.length() <= 32;
                }
            } else {
                return false;
            }
        }
    }

    public static Long[] parseIdsFromStr(String str) {
        return (Long[]) Arrays.stream(str.split("-")).filter(NumberUtils::isCreatable).map(Long::parseLong).toArray();
    }

    public static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd MMM yyyy");
        return dateFormat.format(date);
    }

    public static String[] list2Array(List<String> list) {
        String[] arr = new String[list.size()];
        for (int i = 0; i < list.size(); i ++) {
            arr[i] = list.get(i);
        }
        return arr;
    }
}