package com.group5.flight.booking.controller;

import com.group5.flight.booking.core.APIResponse;
import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.ExceptionHandler;
import com.group5.flight.booking.dto.OTPInfo;
import com.group5.flight.booking.dto.UpdatePasswordInfo;
import com.group5.flight.booking.dto.UserInfo;
import com.group5.flight.booking.model.User;
import com.group5.flight.booking.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/flight-booking")
@RequiredArgsConstructor
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @GetMapping("/users/{userId}")
    public APIResponse findByUserId(HttpServletResponse response, @PathVariable("userId") Long userId) {
        long start = System.currentTimeMillis();
        try {
            User user = userService.findByUserId(userId);
            logger.debug("Call API GET /api/v1/flight-booking/users/{} success", userId);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, user);
        } catch (Exception e) {
            logger.error("Call API GET /api/v1/flight-booking/users/{} failed, error: {}", userId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @GetMapping("/users/me")
    public APIResponse getCurrentUser(HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            UserInfo userInfo = userService.getCurrentUser();
            logger.debug("Call API /api/v1/flight-booking/users/me success, took: {}", System.currentTimeMillis() - start);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, userInfo);
        } catch (Exception e) {
            logger.error("Call API GET /api/v1/flight-booking/users/me failed, error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @PutMapping("/users/{userId}")
    public APIResponse updateUser(HttpServletResponse response, @PathVariable("userId") Long userId, @RequestBody UserInfo userInfo) {
        long start = System.currentTimeMillis();
        try {
            User user = userService.updateUser(userId, userInfo);
            logger.debug("Call API PUT /api/v1/flight-booking/users/{} success", userId);
            return new APIResponse(ErrorCode.SUCCESS, "", System.currentTimeMillis() - start, user);
        } catch (Exception e) {
            logger.error("Call API PUT /api/v1/flight-booking/users/{} failed, request body: {}, error: {}", userId, userInfo, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @DeleteMapping("/users/{userId}")
    public APIResponse deactivateUser(HttpServletResponse response, @PathVariable("userId") Long userId) {
        long start = System.currentTimeMillis();
        try {
            ErrorCode errorCode = userService.deactivateUser(userId);
            logger.debug("Call API DELETE /api/v1/flight-booking/users/{} success", userId);
            response.setStatus(errorCode.getValue());
            return new APIResponse(errorCode, "", System.currentTimeMillis() - start, errorCode.getMessage());
        } catch (Exception e) {
            logger.error("Call API DELETE /api/v1/flight-booking/users/{} failed, error: {}", userId, e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @PostMapping("/users/change-password")
    public APIResponse changePassword(HttpServletResponse response, @RequestBody UpdatePasswordInfo updatePasswordInfo) {
        long start = System.currentTimeMillis();
        try {
            ErrorCode errorCode = userService.changePassword(updatePasswordInfo);
            if (errorCode == ErrorCode.SUCCESS) {
                logger.info("Call API POST /api/v1/flight-booking/users/change-password success, user email: {}", updatePasswordInfo.getEmail());
            } else {
                logger.error("Call API POST /api/v1/flight-booking/users/change-password failed, user email: {}, error: {}",
                        updatePasswordInfo.getEmail(), errorCode.getMessage());
            }

            response.setStatus(errorCode.getValue());
            return new APIResponse(errorCode, "", System.currentTimeMillis() - start, errorCode.getMessage());
        } catch (Exception e) {
            logger.error("Call API POST /api/v1/flight-booking/users/change-password failed, error: {}", e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }

    @PostMapping("/users/forgot-password")
    public APIResponse forgotPassword(HttpServletResponse response, @RequestBody UserInfo userInfo) {
        long start = System.currentTimeMillis();
        try {
            ErrorCode errorCode = userService.forgotPassword(userInfo);
            if (errorCode == ErrorCode.SUCCESS) {
                logger.info("Call API POST /api/v1/flight-booking/users/forgot-password success, user email: {}", userInfo.getEmail());
            } else {
                logger.error("Call API POST /api/v1/flight-booking/users/forgot-password failed, user email: {}, error: ", userInfo.getEmail());
            }

            response.setStatus(errorCode.getValue());
            return new APIResponse(errorCode, "", System.currentTimeMillis() - start, errorCode.getMessage());
        } catch (Exception e) {
            logger.error("Call API POST /api/v1/flight-booking/users/forgot-password failed, user email: {}, error: {}",
                    userInfo.getEmail(), e.getMessage());
            return ExceptionHandler.handleException(response, e, start);
        }
    }


}
