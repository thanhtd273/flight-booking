package com.group1.flight.booking.controller;

import com.group1.flight.booking.core.APIResponse;
import com.group1.flight.booking.core.ErrorCode;
import com.group1.flight.booking.core.exception.ExceptionHandler;
import com.group1.flight.booking.dto.AirlineInfo;
import com.group1.flight.booking.model.Airline;
import com.group1.flight.booking.service.AirlineService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/flight-booking/airlines")
@RequiredArgsConstructor
public class AirlineController {

    private static final Logger logger = LoggerFactory.getLogger(AirlineController.class);

    private final AirlineService airlineService;

    @GetMapping()
    public APIResponse getAllAirlines(HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            List<Airline> airlines = airlineService.getAllAirlines();
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, airlines);
        } catch (Exception e) {
            logger.error("Call API GET /api/v1/flight-booking/airlines failed, error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @GetMapping(value = "/{airlineId}")
    public APIResponse findByAirlineId(@PathVariable(value = "airlineId") Long airlineId, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            Airline airline = airlineService.findByAirlineId(airlineId);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, airline);
        } catch (Exception e) {
            logger.error("Call API GET /api/v1/flight-booking/airlines/{} failed, error: {}", airlineId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @PostMapping(value = "/create")
    public APIResponse createAirline(@RequestBody AirlineInfo airlineInfo, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            Airline airline = airlineService.create(airlineInfo);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, airline);
        } catch (Exception e) {
            logger.error("Call API POST /api/v1/flight-booking/airlines/create failed, error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @PutMapping(value = "/{airlineId}/update")
    public APIResponse updateAirline(@PathVariable(value = "airlineId") Long airlineId, @RequestBody AirlineInfo airlineInfo,
                                    HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            Airline airline = airlineService.update(airlineId, airlineInfo);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, airline);
        } catch (Exception e) {
            logger.error("Call API PUT /api/v1/flight-booking/airlines/{}/update failed, error: {}", airlineId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @DeleteMapping(value = "/{airlineId}/delete")
    public APIResponse deleteAirline(@PathVariable(value = "airlineId") Long airlineId, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            ErrorCode errorCode = airlineService.delete(airlineId);
            return new APIResponse(errorCode, "", System.currentTimeMillis() - start, errorCode.getMessage());
        } catch (Exception e) {
            logger.error("Call API DELETE /api/v1/flight-booking/airlines/{}/delete failed, error: {}", airlineId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }
}
