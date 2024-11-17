package com.group5.flight.booking.controller;

import com.group5.flight.booking.core.APIResponse;
import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.ExceptionHandler;
import com.group5.flight.booking.dto.PlaneInfo;
import com.group5.flight.booking.model.Plane;
import com.group5.flight.booking.service.PlaneService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/flight-booking/planes")
@RequiredArgsConstructor
public class PlaneController {

    private static final Logger logger = LoggerFactory.getLogger(PlaneController.class);

    private final PlaneService planeService;

    @GetMapping()
    public APIResponse getAllPlanes(HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            List<Plane> planes = planeService.getAllPlanes();
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, planes);
        } catch (Exception e) {
            logger.error("Call API GET /api/v1/flight-booking/planes failed, error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @GetMapping(value = "/{planeId}")
    public APIResponse findByPlaneId(@PathVariable(value = "planeId") Long planeId, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            Plane plane = planeService.findByPlaneId(planeId);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, plane);
        } catch (Exception e) {
            logger.error("Call API GET /api/v1/flight-booking/planes/{} failed, error: {}", planeId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @PostMapping(value = "/create")
    public APIResponse createPlane(@RequestBody PlaneInfo planeInfo, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            Plane plane = planeService.create(planeInfo);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, plane);
        } catch (Exception e) {
            logger.error("Call API POST /api/v1/flight-booking/planes/create failed, error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @PutMapping(value = "/{planeId}/update")
    public APIResponse updatePlane(@PathVariable(value = "planeId") Long planeId, @RequestBody PlaneInfo planeInfo,
                                     HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            Plane plane = planeService.update(planeId, planeInfo);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, plane);
        } catch (Exception e) {
            logger.error("Call API PUT /api/v1/flight-booking/planes/{}/update failed, error: {}", planeId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @DeleteMapping(value = "/{planeId}/delete")
    public APIResponse deletePlane(@PathVariable(value = "planeId") Long planeId, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            ErrorCode errorCode = planeService.delete(planeId);
            return new APIResponse(errorCode, "", System.currentTimeMillis() - start, errorCode.getMessage());
        } catch (Exception e) {
            logger.error("Call API DELETE /api/v1/flight-booking/planes/{}/delete failed, error: {}", planeId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }
}
