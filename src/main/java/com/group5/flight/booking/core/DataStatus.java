package com.group5.flight.booking.core;

public class DataStatus {
    public static final int DELETED = 0;
    public static final int ACTIVE = 1;
    public static final int PENDING = 2;

    public static final int FLIGHT_CHOSEN = 2;

    public static final int FILL_OUT_INFO = 3;

    public static final int PAYING_IN_PROGRESS = 4;

    public static final int PAYING_FAIL = 5;

    public static final int PAYING_SUCCESS = 6;

    private DataStatus() {
        throw new IllegalStateException("Utility class");
    }
}