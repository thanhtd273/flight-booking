package com.group1.flight.booking.controller;

import com.group1.flight.booking.core.APIResponse;
import com.group1.flight.booking.core.ErrorCode;
import com.group1.flight.booking.core.exception.ExceptionHandler;
import com.group1.flight.booking.dto.BookingDetail;
import com.group1.flight.booking.dto.BookingInfo;
import com.group1.flight.booking.model.Booking;
import com.group1.flight.booking.service.BookingService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/flight-booking/bookings")
@RequiredArgsConstructor
public class BookingController {

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    private final BookingService bookingService;

    @GetMapping()
    public APIResponse getAllBookings(HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            List<Booking> bookings = bookingService.getAllBookings();
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, bookings);
        } catch (Exception e) {
            logger.error("Call API GET /api/v1/flight-booking/bookings failed, error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @GetMapping(value = "/{bookingId}")
    public APIResponse findByBookingId(@PathVariable(value = "bookingId") Long bookingId, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            Booking booking = bookingService.findByBookingId(bookingId);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, booking);
        } catch (Exception e) {
            logger.error("Call API GET /api/v1/flight-booking/bookings/{} failed, error: {}", bookingId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @PostMapping(value = "/create")
    public APIResponse createBooking(@RequestBody BookingInfo bookingInfo, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            Booking booking = bookingService.create(bookingInfo);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, booking);
        } catch (Exception e) {
            logger.error("Call API POST /api/v1/flight-booking/bookings/create failed, error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @DeleteMapping(value = "/{bookingId}/delete")
    public APIResponse deleteBooking(@PathVariable(value = "bookingId") Long bookingId, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            ErrorCode errorCode = bookingService.delete(bookingId);
            return new APIResponse(errorCode, "", System.currentTimeMillis() - start, errorCode.getMessage());
        } catch (Exception e) {
            logger.error("Call API DELETE /api/v1/flight-booking/bookings/{}/delete failed, error: {}", bookingId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

}
