package com.group1.flight.booking.controller;

import com.group1.flight.booking.core.APIResponse;
import com.group1.flight.booking.core.ErrorCode;
import com.group1.flight.booking.core.exception.ExceptionHandler;
import com.group1.flight.booking.dto.UserInfo;
import com.group1.flight.booking.model.User;
import com.group1.flight.booking.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/v1/flight-booking")
@RequiredArgsConstructor
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    @GetMapping(value = "/verify-token")
    public APIResponse verifyToken(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {

            logger.info("Call API GET /api/v1/flight-booking/verify-token success");
            UserInfo userInfo = authService.verifyToken(token);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, userInfo);
        } catch (Exception e) {
            logger.error("Call API GET /api/v1/flight-booking/verify-token fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @PostMapping("/sign-up")
    public APIResponse createUser(HttpServletResponse response, @RequestBody UserInfo userInfo) {
        long start = System.currentTimeMillis();
        try {
            User user = authService.signUp(userInfo);
            logger.debug("Call API POST /api/v1/flight-booking/sign-up success, userId = {}", user.getUserId());
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, user);
        } catch (Exception e) {
            logger.error("Call API POST /api/v1/flight-booking/sign-up failed, request body: {}, error: {}", userInfo, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }
}
