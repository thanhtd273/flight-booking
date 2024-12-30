package com.group1.flight.booking.form.component;

import javax.swing.*;
import java.awt.*;

public class FlightCard extends JPanel {

    public FlightCard() {
        initComponent();
    }

    private void initComponent() {
        setLayout(new BorderLayout());
        setSize(400, 200);
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        setBackground(Color.WHITE);

        // Airline and details
        JPanel airlinePanel = new JPanel(new BorderLayout());
        airlinePanel.setBackground(Color.WHITE);

        JLabel airlineLabel = new JLabel("Vietravel Airlines", SwingConstants.LEFT);
        airlineLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        airlineLabel.setIcon(new ImageIcon("path/to/airline_icon.png")); // Add airline logo

        airlinePanel.add(airlineLabel, BorderLayout.WEST);

        // Time and route panel
        JPanel timeRoutePanel = new JPanel();
        timeRoutePanel.setLayout(new GridLayout(2, 3, 10, 5));
        timeRoutePanel.setBackground(Color.WHITE);

        JLabel departureTimeLabel = new JLabel("23:59", JLabel.CENTER);
        departureTimeLabel.setFont(new Font("SansSerif", Font.BOLD, 22));

        JLabel flightDurationLabel = new JLabel("1h 26m", JLabel.CENTER);
        flightDurationLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel arrivalTimeLabel = new JLabel("01:25+1 ngày", JLabel.CENTER);
        arrivalTimeLabel.setFont(new Font("SansSerif", Font.BOLD, 22));

        JLabel departureAirportLabel = new JLabel("SGN", JLabel.CENTER);
        departureAirportLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));

        JLabel flightTypeLabel = new JLabel("Bay thẳng", JLabel.CENTER);
        flightTypeLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel arrivalAirportLabel = new JLabel("DAD", JLabel.CENTER);
        arrivalAirportLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));

        timeRoutePanel.add(departureTimeLabel);
        timeRoutePanel.add(flightDurationLabel);
        timeRoutePanel.add(arrivalTimeLabel);
        timeRoutePanel.add(departureAirportLabel);
        timeRoutePanel.add(flightTypeLabel);
        timeRoutePanel.add(arrivalAirportLabel);

        // Price and button panel
        JPanel priceButtonPanel = new JPanel(new BorderLayout());
        priceButtonPanel.setBackground(Color.WHITE);

        JLabel priceLabel = new JLabel("2,335,563 VND/khách", JLabel.RIGHT);
        priceLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        priceLabel.setForeground(new Color(255, 69, 0));

        JButton selectButton = new JButton("Chọn");
        selectButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        selectButton.setBackground(new Color(0, 123, 255));
        selectButton.setForeground(Color.WHITE);

        priceButtonPanel.add(priceLabel, BorderLayout.CENTER);
        priceButtonPanel.add(selectButton, BorderLayout.EAST);

        // Add components to main panel
        add(airlinePanel, BorderLayout.NORTH);
        add(timeRoutePanel, BorderLayout.CENTER);
        add(priceButtonPanel, BorderLayout.SOUTH);
    }
}
