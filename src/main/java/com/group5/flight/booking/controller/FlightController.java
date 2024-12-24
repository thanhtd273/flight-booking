package com.group5.flight.booking.controller;

import com.group5.flight.booking.core.APIResponse;
import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.ExceptionHandler;
import com.group5.flight.booking.dto.FlightInfo;
import com.group5.flight.booking.dto.FilterCriteria;
import com.group5.flight.booking.model.Flight;
import com.group5.flight.booking.service.FlightService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/flight-booking/flights")
@RequiredArgsConstructor
public class FlightController {

    private static final Logger logger = LoggerFactory.getLogger(FlightController.class);

    private final FlightService flightService;

    @GetMapping()
    public APIResponse getAllFlights(HttpServletResponse response) {
        long start = System.currentTimeMillis();

        try {
            List<Flight> flights = flightService.getAllFlights();
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, flights);
        } catch (Exception e) {
            logger.error("Call API GET /api/v1/flight-booking/flights failed, error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @GetMapping(value = "/{flightId}")
    public APIResponse findByFlightId(@PathVariable(value = "flightId") Long flightId, HttpServletResponse response) {
        long start = System.currentTimeMillis();

        try {
            Flight flight = flightService.findByFlightId(flightId);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, flight);
        } catch (Exception e) {
            logger.error("Call API GET /api/v1/flight-booking/flights/{} failed, error: {}", flightId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @PostMapping(value = "/create")
    public APIResponse createFlight(@RequestBody FlightInfo flightInfo, HttpServletResponse response) {
        long start = System.currentTimeMillis();

        try {
            Flight flight = flightService.create(flightInfo);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, flight);
        } catch (Exception e) {
            logger.error("Call API POST /api/v1/flight-booking/flights failed, error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @GetMapping(value = "/search")
    public APIResponse findByAirportsAndDepartureDate(@RequestParam(value = "from") Long fromAirportId,
                                                      @RequestParam(value = "to") Long toAirportId,
                                                      @RequestParam(value = "departureDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date departureDate,
                                                      HttpServletResponse response) {
        long start = System.currentTimeMillis();

        try {
            List<FlightInfo> flightInfoList = flightService.findFlight(fromAirportId, toAirportId, departureDate);
            logger.debug("GET /api/v1/flight-booking/flights/search?from={}&to={}&departureDate={} success, count = {}",
                    fromAirportId, toAirportId, departureDate, flightInfoList.size());
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, flightInfoList);
        } catch (Exception e) {
            logger.error("GET /api/v1/flight-booking/flights/search?from={}&to={}&departureDate={} failed, error: {}", fromAirportId, toAirportId, departureDate, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @GetMapping(value = "/filter")
    public APIResponse filterFlight(@RequestBody FilterCriteria filterCriteria, HttpServletResponse response) {
        long start = System.currentTimeMillis();

        try {
            List<FlightInfo> flightInfoList = flightService.filterFlights(filterCriteria);
            logger.debug("GET /api/v1/flight-booking/flights/filter success, count = {}", flightInfoList.size());
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, flightInfoList);
        } catch (Exception e) {
            logger.error("GET /api/v1/flight-booking/flights/filter failed, error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }


}
