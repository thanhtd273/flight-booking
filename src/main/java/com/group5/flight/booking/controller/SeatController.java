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

    @GetMapping()
    public APIResponse getAllSeats(HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            List<Seat> seats = seatService.getAllSeats();
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, seats);
        } catch (Exception e) {
            logger.error("Call API GET /api/v1/flight-booking/seats failed, error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @GetMapping(value = "/{seatId}")
    public APIResponse findBySeatId(@PathVariable(value = "seatId") Long seatId, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            Seat seat = seatService.findBySeatId(seatId);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, seat);
        } catch (Exception e) {
            logger.error("Call API GET /api/v1/flight-booking/seats/{} failed, error: {}", seatId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @PostMapping(value = "/create")
    public APIResponse createSeat(@RequestBody SeatInfo seatInfo, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            Seat seat = seatService.create(seatInfo);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, seat);
        } catch (Exception e) {
            logger.error("Call API POST /api/v1/flight-booking/seats/create failed, error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @PutMapping(value = "/{seatId}/update")
    public APIResponse updateSeat(@PathVariable(value = "seatId") Long seatId, @RequestBody SeatInfo seatInfo,
                                  HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            Seat seat = seatService.update(seatId, seatInfo);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, seat);
        } catch (Exception e) {
            logger.error("Call API PUT /api/v1/flight-booking/seats/{}/update failed, error: {}", seatId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @DeleteMapping(value = "/{seatId}/delete")
    public APIResponse deleteSeat(@PathVariable(value = "seatId") Long seatId, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            ErrorCode errorCode = seatService.delete(seatId);
            return new APIResponse(errorCode, "", System.currentTimeMillis() - start, errorCode.getMessage());
        } catch (Exception e) {
            logger.error("Call API DELETE /api/v1/flight-booking/seats/{}/delete failed, error: {}", seatId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @GetMapping(value = "/available/{flightId}")
    public APIResponse getAvailableSeatsByFlight(@PathVariable(value = "flightId") Long flightId, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            List<Seat> availableSeats = seatService.getAvailableSeatsByFlight(flightId);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, availableSeats);
        } catch (Exception e) {
            logger.error("Call API GET /api/v1/flight-booking/seats/available/{} failed, error: {}", flightId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @GetMapping(value = "/count-by-class")
    public APIResponse countAvailableSeatsByClass(HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            List<Object[]> seatCounts = seatService.countAvailableSeatsByClass();
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, seatCounts);
        } catch (Exception e) {
            logger.error("Call API GET /api/v1/flight-booking/seats/count-by-class failed, error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }
}