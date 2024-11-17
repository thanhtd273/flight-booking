package com.group5.flight.booking.controller;

import com.group5.flight.booking.core.APIResponse;
import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.ExceptionHandler;
import com.group5.flight.booking.dto.NationInfo;
import com.group5.flight.booking.model.Nation;
import com.group5.flight.booking.service.NationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/flight-booking/nations")
@RequiredArgsConstructor
public class NationController {

    private static final Logger logger = LoggerFactory.getLogger(NationController.class);

    private final NationService nationService;

    @GetMapping()
    public APIResponse getAllNations(HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            List<Nation> nations = nationService.getAllNations();
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, nations);
        } catch (Exception e) {
            logger.error("Call API GET /api/v1/flight-booking/nations failed, error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @GetMapping(value = "/{nationId}")
    public APIResponse findByNationId(@PathVariable(value = "nationId") Long nationId, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            Nation nation = nationService.findByNationId(nationId);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, nation);
        } catch (Exception e) {
            logger.error("Call API GET /api/v1/flight-booking/nations/{} failed, error: {}", nationId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @PostMapping(value = "/create")
    public APIResponse createNation(@RequestBody NationInfo nationInfo, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            Nation nation = nationService.create(nationInfo);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, nation);
        } catch (Exception e) {
            logger.error("Call API POST /api/v1/flight-booking/nations/create failed, error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @PutMapping(value = "/{nationId}/update")
    public APIResponse updateNation(@PathVariable(value = "nationId") Long nationId, @RequestBody NationInfo nationInfo,
                                  HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            Nation nation = nationService.update(nationId, nationInfo);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, nation);
        } catch (Exception e) {
            logger.error("Call API PUT /api/v1/flight-booking/nations/{}/update failed, error: {}", nationId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @DeleteMapping(value = "/{nationId}/delete")
    public APIResponse deleteNation(@PathVariable(value = "nationId") Long nationId, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            ErrorCode errorCode = nationService.delete(nationId);
            return new APIResponse(errorCode, "", System.currentTimeMillis() - start, errorCode.getMessage());
        } catch (Exception e) {
            logger.error("Call API DELETE /api/v1/flight-booking/nations/{}/delete failed, error: {}", nationId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }
}
