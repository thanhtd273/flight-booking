package com.group5.flight.booking.controller;

import com.group5.flight.booking.core.APIResponse;
import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.ExceptionHandler;
import com.group5.flight.booking.dto.PassengerInfo;
import com.group5.flight.booking.model.Passenger;
import com.group5.flight.booking.service.PassengerService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/flight-booking/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private static final Logger logger = LoggerFactory.getLogger(PassengerController.class);

    private final PassengerService passengerService;

    @GetMapping()
    public APIResponse getAllPassengers(HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            List<Passenger> passengers = passengerService.getAllPassengers();
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, passengers);
        } catch (Exception e) {
            logger.error("Call API GET /api/v1/flight-booking/passengers failed, error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @GetMapping(value = "/{passengerId}")
    public APIResponse findByPassengerId(@PathVariable(value = "passengerId") Long passengerId, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            Passenger passenger = passengerService.findByPassengerId(passengerId);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, passenger);
        } catch (Exception e) {
            logger.error("Call API GET /api/v1/flight-booking/passengers/{} failed, error: {}", passengerId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @PostMapping(value = "/create")
    public APIResponse createPassenger(@RequestBody PassengerInfo passengerInfo, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            Passenger passenger = passengerService.create(passengerInfo);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, passenger);
        } catch (Exception e) {
            logger.error("Call API POST /api/v1/flight-booking/passengers/create failed, error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @PutMapping(value = "/{passengerId}/update")
    public APIResponse updatePassenger(@PathVariable(value = "passengerId") Long passengerId, @RequestBody PassengerInfo passengerInfo,
                                   HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            Passenger passenger = passengerService.update(passengerId, passengerInfo);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, passenger);
        } catch (Exception e) {
            logger.error("Call API PUT /api/v1/flight-booking/passengers/{}/update failed, error: {}", passengerId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @DeleteMapping(value = "/{passengerId}/delete")
    public APIResponse deletePassenger(@PathVariable(value = "passengerId") Long passengerId, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            ErrorCode errorCode = passengerService.delete(passengerId);
            return new APIResponse(errorCode, "", System.currentTimeMillis() - start, errorCode.getMessage());
        } catch (Exception e) {
            logger.error("Call API DELETE /api/v1/flight-booking/passengers/{}/delete failed, error: {}", passengerId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }
}
