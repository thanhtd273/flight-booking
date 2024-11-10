package com.group5.flight.booking.core.exception;

import com.group5.flight.booking.core.APIResponse;
import jakarta.servlet.http.HttpServletResponse;

public class ExceptionHandler {
    private ExceptionHandler() {
        throw new IllegalStateException("Static class");
    }

    public static APIResponse handleException(HttpServletResponse response, Exception e, long start) {
        if (e.getClass() == LogicException.class) {
            LogicException specEx = (LogicException)e;
            response.setStatus(specEx.getErrorCode().getValue());
            return new APIResponse(specEx.getErrorCode(), specEx.getMessage(), System.currentTimeMillis() - start, (Object)null);
        } else {
            response.setStatus(500);
            return new APIResponse(500, "error", e.getMessage(), System.currentTimeMillis() - start, (Long)null);
        }
    }
}