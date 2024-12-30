package com.group1.flight.booking.core.exception;

public class GlobalException extends Exception {
    public GlobalException() {
    }

    public GlobalException(String message) {
        super(message);
    }

    public GlobalException(String message, Throwable cause) {
        super(message, cause);
    }

    public GlobalException(Throwable cause) {
        super(cause);
    }
}