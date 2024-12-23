package com.group5.flight.booking.config;

import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.form.MainFrame;
import com.group5.flight.booking.service.AirportService;
import com.group5.flight.booking.service.AuthService;
import com.group5.flight.booking.service.FlightService;
import com.group5.flight.booking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final AuthService authService;

    private final AirportService airportService;

    private final FlightService flightService;

    private final UserService userService;

    @Bean
    public MainFrame jFrameLogin() throws LogicException {
        return new MainFrame(authService, airportService, flightService, userService);
    }
}
