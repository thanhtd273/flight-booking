package com.group5.flight.booking.form;

import com.group5.flight.booking.form.component.FlightSearchPanel;
import com.group5.flight.booking.service.AirportService;
import com.group5.flight.booking.service.AuthService;
import com.group5.flight.booking.service.FlightService;
import com.group5.flight.booking.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import static com.group5.flight.booking.core.Constants.*;

public class MainFrame extends JFrame {

    private static final Logger logger = LoggerFactory.getLogger(MainFrame.class);

    private final AuthService authService;

    private final AirportService airportService;

    private final FlightService flightService;

    private final UserService userService;

    private final CardLayout cardLayout;

    private final JPanel mainPanel;

    public MainFrame(AuthService authService, AirportService airportService, FlightService flightService, UserService userService) {
        this.authService = authService;
        this.airportService = airportService;
        this.flightService = flightService;
        this.userService = userService;

        // Initialize CardLayout and main panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        initComponent();
    }

    private void initComponent() {
        // Set the frame properties
        setTitle("Flight Booking Application");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);




        // Add the sign-up panel, login panel, and flight searcher panel
        CodeVerifierPanel codeVerifierPanel = new CodeVerifierPanel(mainPanel, cardLayout, userService);
        SignUpPanel signUpPanel = new SignUpPanel(mainPanel, cardLayout, codeVerifierPanel, authService);
        LoginPanel loginPanel = new LoginPanel(mainPanel, cardLayout, authService);
        FlightSearchPanel flightSearchPanel = new FlightSearchPanel(airportService, flightService);
        FlightListPanel flightListPanel = new FlightListPanel();

        mainPanel.add(signUpPanel, SIGNUP_SCREEN);
        mainPanel.add(loginPanel, LOGIN_SCREEN);
        mainPanel.add(flightSearchPanel, FLIGHT_SEARCHER_SCREEN);
        mainPanel.add(flightListPanel, FLIGHT_LIST_SCREEN);

        FlightSeatPanel flightSeatPanel = new FlightSeatPanel();
        mainPanel.add(flightSeatPanel, FLIGHT_SEAT_SCREEN);
        mainPanel.add(codeVerifierPanel, CODE_VERIFIER);
        // Add the main panel to the frame
        add(mainPanel);

        // Show the sign-up panel initially
        cardLayout.show(mainPanel, FLIGHT_LIST_SCREEN);
    }
}
