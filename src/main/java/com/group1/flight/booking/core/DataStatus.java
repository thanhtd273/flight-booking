package com.group1.flight.booking.core;

public class DataStatus {
    public static final int DELETED = 0;
    public static final int ACTIVE = 1;
    public static final int PENDING = 2;

    public static final int UNPAID = 3;

    public static final int PAID = 1;

    private DataStatus() {
        throw new IllegalStateException("Utility class");
    }
}