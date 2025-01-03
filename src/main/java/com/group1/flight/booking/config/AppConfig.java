package com.group1.flight.booking.config;

import com.group1.flight.booking.form.MainFrame;

import com.group1.flight.booking.service.AirlineService;
import com.group1.flight.booking.service.AirportService;
import com.group1.flight.booking.service.AuthService;
import com.group1.flight.booking.service.BookingService;
import com.group1.flight.booking.service.FlightService;
import com.group1.flight.booking.service.NationService;
import com.group1.flight.booking.service.UserService;
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

    private final AirlineService airlineService;

    private final BookingService bookingService;

    private final NationService nationService;

    @Bean
    public MainFrame jFrameLogin() {
        return new MainFrame(authService, airportService, flightService, userService, airlineService, bookingService, nationService);
    }
}
