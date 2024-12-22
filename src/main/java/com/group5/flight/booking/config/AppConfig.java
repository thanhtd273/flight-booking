package com.group5.flight.booking.config;

import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.form.JFrameLogin;
import com.group5.flight.booking.service.AirportService;
import com.group5.flight.booking.service.AuthService;
import com.group5.flight.booking.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final AuthService authService;

    private final AirportService airportService;

    private final FlightService flightService;

    @Bean
    public JFrameLogin jFrameLogin() throws LogicException {
        return new JFrameLogin(authService, airportService, flightService);
    }
}
