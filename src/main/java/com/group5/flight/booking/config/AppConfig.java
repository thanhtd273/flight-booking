package com.group5.flight.booking.config;

import com.group5.flight.booking.form.MainFrame;
import com.group5.flight.booking.service.*;
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
