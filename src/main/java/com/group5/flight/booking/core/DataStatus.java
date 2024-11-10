package com.group5.flight.booking.core;

public class DataStatus {
    public static final int DELETED = 0;
    public static final int ACTIVE = 1;
    public static final int PENDING = 2;

    private DataStatus() {
        throw new IllegalStateException("Utility class");
    }
}