package com.group5.flight.booking.core;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class AppUtils {
    private static final String EMAIL_REGEXP = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

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
}