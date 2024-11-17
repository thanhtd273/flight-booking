package com.group5.flight.booking.controller;

import com.group5.flight.booking.core.APIResponse;
import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.ExceptionHandler;
import com.group5.flight.booking.dto.CityInfo;
import com.group5.flight.booking.model.City;
import com.group5.flight.booking.service.CityService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/flight-booking/cities")
@RequiredArgsConstructor
public class CityController {

    private static final Logger logger = LoggerFactory.getLogger(CityController.class);

    private final CityService cityService;

    @GetMapping()
    public APIResponse getAllCities(HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            List<City> cities = cityService.getAllCities();
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, cities);
        } catch (Exception e) {
            logger.error("Call API GET /api/v1/flight-booking/cities failed, error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @GetMapping(value = "/{cityId}")
    public APIResponse findByCityId(@PathVariable(value = "cityId") Long cityId, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            City city = cityService.findByCityId(cityId);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, city);
        } catch (Exception e) {
            logger.error("Call API GET /api/v1/flight-booking/cities/{} failed, error: {}", cityId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @PostMapping(value = "/create")
    public APIResponse createCity(@RequestBody CityInfo cityInfo, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            City city = cityService.create(cityInfo);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, city);
        } catch (Exception e) {
            logger.error("Call API POST /api/v1/flight-booking/cities/create failed, error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @PutMapping(value = "/{cityId}/update")
    public APIResponse updateCity(@PathVariable(value = "cityId") Long cityId, @RequestBody CityInfo cityInfo,
                                  HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            City city = cityService.update(cityId, cityInfo);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, city);
        } catch (Exception e) {
            logger.error("Call API PUT /api/v1/flight-booking/cities/{}/update failed, error: {}", cityId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @DeleteMapping(value = "/{cityId}/delete")
    public APIResponse deleteCity(@PathVariable(value = "cityId") Long cityId, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            ErrorCode errorCode = cityService.delete(cityId);
            return new APIResponse(errorCode, "", System.currentTimeMillis() - start, errorCode.getMessage());
        } catch (Exception e) {
            logger.error("Call API DELETE /api/v1/flight-booking/cities/{}/delete failed, error: {}", cityId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }
}
