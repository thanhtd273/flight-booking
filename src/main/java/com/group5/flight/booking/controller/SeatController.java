package com.group5.flight.booking.controller;

import com.group5.flight.booking.core.APIResponse;
import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.ExceptionHandler;
import com.group5.flight.booking.dto.SeatInfo;
import com.group5.flight.booking.model.Seat;
import com.group5.flight.booking.service.SeatService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/flight-booking/seats")
@RequiredArgsConstructor
public class SeatController {

    private static final Logger logger = LoggerFactory.getLogger(SeatController.class);
    private final SeatService seatService;

    @GetMapping
    public APIResponse getAllSeats(HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            List<Seat> seats = seatService.getAllSeats();
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, seats);
        } catch (Exception e) {
            logger.error("Call API GET /seats failed, error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @GetMapping("/{id}")
    public APIResponse getSeatById(@PathVariable Long id, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            Seat seat = seatService.findBySeatId(id);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, seat);
        } catch (Exception e) {
            logger.error("Call API GET /seats/{} failed, error: {}", id, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @PostMapping
    public APIResponse createSeat(@RequestBody SeatInfo seatInfo, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            Seat seat = seatService.create(seatInfo);
            return new APIResponse(ErrorCode.SUCCESS, "Seat created successfully", 
                System.currentTimeMillis() - start, seat);
        } catch (Exception e) {
            logger.error("Call API POST /seats failed, error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @DeleteMapping("/{id}")
    public APIResponse deleteSeat(@PathVariable Long id, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            ErrorCode result = seatService.delete(id);
            return new APIResponse(result, "Seat deleted successfully", 
                System.currentTimeMillis() - start, null);
        } catch (Exception e) {
            logger.error("Call API DELETE /seats/{} failed, error: {}", id, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @GetMapping("/flights/{flightId}/available")
    public APIResponse getAvailableSeatsByFlight(@PathVariable Long flightId, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            List<Seat> availableSeats = seatService.getAvailableSeatsByFlight(flightId);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, availableSeats);
        } catch (Exception e) {
            logger.error("Call API GET /seats/flights/{}/available failed, error: {}", flightId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

}
