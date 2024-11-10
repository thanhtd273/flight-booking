package com.group5.flight.booking.controller;

import com.group5.flight.booking.core.APIResponse;
import com.group5.flight.booking.core.ErrorCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/task-service")
public class MonitorController {

    @GetMapping(value = "/health")
    public APIResponse echo() {
        return new APIResponse(ErrorCode.SUCCESS, "Application is running", 0L, "health");
    }
}
