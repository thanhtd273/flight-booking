package com.group5.flight.booking.controller;

import com.group5.flight.booking.core.APIResponse;
import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.ExceptionHandler;
import com.group5.flight.booking.dto.AirportInfo;
import com.group5.flight.booking.model.Airport;
import com.group5.flight.booking.service.AirportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/flight-booking/airports")
@RequiredArgsConstructor
public class AirportController {

    private static final Logger logger = LoggerFactory.getLogger(AirportController.class);

    private final AirportService airportService;

    @GetMapping()
    public APIResponse getAllAirports(HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            List<Airport> airports = airportService.getAllAirports();
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, airports);
        } catch (Exception e) {
            logger.error("Call API GET /api/v1/flight-booking/airports failed, error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @GetMapping(value = "/{airportId}")
    public APIResponse findByAirportId(@PathVariable(value = "airportId") Long airportId, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            Airport airport = airportService.findByAirportId(airportId);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, airport);
        } catch (Exception e) {
            logger.error("Call API GET /api/v1/flight-booking/airports/{} failed, error: {}", airportId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @PostMapping(value = "/create")
    public APIResponse createAirport(@RequestBody AirportInfo airportInfo, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            Airport airport = airportService.create(airportInfo);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, airport);
        } catch (Exception e) {
            logger.error("Call API POST /api/v1/flight-booking/airports/create failed, error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @PutMapping(value = "/{airportId}/update")
    public APIResponse updateAirport(@PathVariable(value = "airportId") Long airportId, @RequestBody AirportInfo airportInfo,
                                     HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            Airport airport = airportService.update(airportId, airportInfo);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, airport);
        } catch (Exception e) {
            logger.error("Call API PUT /api/v1/flight-booking/airports/{}/update failed, error: {}", airportId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @DeleteMapping(value = "/{airportId}/delete")
    public APIResponse deleteAirport(@PathVariable(value = "airportId") Long airportId, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            ErrorCode errorCode = airportService.delete(airportId);
            return new APIResponse(errorCode, "", System.currentTimeMillis() - start, errorCode.getMessage());
        } catch (Exception e) {
            logger.error("Call API DELETE /api/v1/flight-booking/airports/{}/delete failed, error: {}", airportId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }
}
