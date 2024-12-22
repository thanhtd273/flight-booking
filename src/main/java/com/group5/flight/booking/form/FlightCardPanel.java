package com.group5.flight.booking.form;

import com.group5.flight.booking.core.Constants;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

@Getter
public class FlightCardPanel extends JPanel {

    private JPanel cardPanel;

    public FlightCardPanel(String airline, String departure, String arrival, String duration, int price, int originalPrice) {
        cardPanel = new JPanel(new GridLayout(2, 1));

        // General info: airline, time, price
        JPanel flightInfoPanel = new JPanel(new GridLayout(1, 3));
        JLabel airlineLabel = new JLabel(airline);
        airlineLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        flightInfoPanel.add(airlineLabel);

        JPanel centerPanel = new JPanel(new GridLayout(1, 3));
        JPanel departureTimeAndLocationPanel = getTimeAndLocationPanel("12:35", "SGN");
        centerPanel.add(departureTimeAndLocationPanel);
        JLabel betweenLabel = new JLabel("--->");
        centerPanel.add(betweenLabel);
        JPanel destinationTimeAndLocationPanel = getTimeAndLocationPanel("13:50", "DAD");
        centerPanel.add(destinationTimeAndLocationPanel);

        JLabel priceLabel = new JLabel("2548312 VND/customer");
        priceLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        flightInfoPanel.add(priceLabel);
        // Action: Detail, Select
        JPanel actionPanel = new JPanel(new BorderLayout());
        JLabel detailLabel = new JLabel("Detail");
        detailLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 12));
        actionPanel.add(detailLabel, BorderLayout.WEST);
        JButton selectButton = new JButton("Choose");
        selectButton.setBackground(new Color(0, 102, 204));
        selectButton.setForeground(Color.WHITE);
        selectButton.setFocusPainted(false);
        selectButton.setFont(new Font("Arial", Font.BOLD, 14));
        actionPanel.add(selectButton, BorderLayout.EAST);

    }

    private static JPanel getTimeAndLocationPanel(String time, String location) {
        JPanel destinationTimeAndLocationPanel = new JPanel(new GridLayout(2, 1));
        JLabel destinationTimeLabel = new JLabel(time);
        destinationTimeLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        destinationTimeAndLocationPanel.add(destinationTimeLabel);
        JLabel destinationLocationLabel = new JLabel(location);
        destinationLocationLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 12));
        destinationTimeAndLocationPanel.add(destinationLocationLabel);
        return destinationTimeAndLocationPanel;
    }
}
