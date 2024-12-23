package com.group5.flight.booking.core;

import java.util.Map;

public final class Constants {
    private Constants() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static final String FB_FONT = "sanserif";

    public static final String FONT_NAME = "sanserif";

    public static final String SIGNUP_SCREEN = "SignUp";

    public static final String LOGIN_SCREEN = "Login";

    public static final String FLIGHT_DETAIL_SCREEN = "FlightDetail";

    public static final String FLIGHT_SEARCHER_SCREEN = "FlightSearcher";

    public static final String FLIGHT_LIST_SCREEN = "FlightList";

    public static final String FLIGHT_SEAT_SCREEN = "FlightSeat";

    public static final String CODE_VERIFIER = "CodeVerifier";

    public static final String FLIGHT_PAYER = "FlightPayer";

    public static final String MIDNIGHT_TO_MORNING = "00:00 - 06:00";

    public static final String MORNING_TO_NOON = "06:00 - 12:00";

    public static final String NOON_TO_EVENING = "12:00 - 18:00";

    public static final String EVENING_TO_MIDNIGHT = "18:00 - 24:00";

    public static final Map<String, Integer> TIME_PERIOD_MAP =
            Map.of(MIDNIGHT_TO_MORNING, 1, MORNING_TO_NOON, 2, NOON_TO_EVENING, 3, EVENING_TO_MIDNIGHT, 4);

}
