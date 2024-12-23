package com.group5.flight.booking.form;

import com.group5.flight.booking.core.Constants;

import javax.swing.*;
import java.awt.*;

public class FlightListPanel extends JPanel {

    public FlightListPanel() {
        initComponent();
    }

    private void initComponent() {
        setLayout(new BorderLayout());
        add(createFilterSidebar(), BorderLayout.WEST);
        add(createFlightListPanel(), BorderLayout.CENTER);
    }

    private JPanel createFilterSidebar() {
        // Main panel setup
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
        filterPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        filterPanel.setBackground(new Color(245, 245, 245));
        filterPanel.setPreferredSize(new Dimension(300, 700));

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(245, 245, 245));

        JLabel headerLabel = new JLabel("Bộ lọc");
        headerLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 20));

        JButton resetButton = new JButton("Đặt lại");
        resetButton.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 14));
        resetButton.setForeground(new Color(0, 123, 255));
        resetButton.setContentAreaFilled(false);
        resetButton.setBorder(null);

        headerPanel.add(headerLabel, BorderLayout.WEST);
        headerPanel.add(resetButton, BorderLayout.EAST);

        // Airline filter section
        JPanel airlineFilterPanel = new JPanel();
        airlineFilterPanel.setLayout(new BoxLayout(airlineFilterPanel, BoxLayout.Y_AXIS));
        airlineFilterPanel.setBackground(new Color(245, 245, 245));
        airlineFilterPanel.setBorder(BorderFactory.createTitledBorder(null, "Hãng hàng không",
                0, 0, new Font(Constants.FB_FONT, Font.BOLD, 16)));

        JCheckBox airline1 = new JCheckBox("Malaysia Airlines - 12.350.857 VND");
        JCheckBox airline2 = new JCheckBox("VietJet Air - 5.595.711 VND");
        JCheckBox airline3 = new JCheckBox("Vietnam Airlines - 2.422.825 VND");
        JCheckBox airline4 = new JCheckBox("Vietravel Airlines - 2.335.563 VND");

        styleCheckbox(airline1);
        styleCheckbox(airline2);
        styleCheckbox(airline3);
        styleCheckbox(airline4);

        airlineFilterPanel.add(airline1);
        airlineFilterPanel.add(airline2);
        airlineFilterPanel.add(airline3);
        airlineFilterPanel.add(airline4);

        // Flight time filter section
        JPanel timeFilterPanel = new JPanel();
        timeFilterPanel.setLayout(new BoxLayout(timeFilterPanel, BoxLayout.Y_AXIS));
        timeFilterPanel.setBackground(new Color(245, 245, 245));
        timeFilterPanel.setBorder(BorderFactory.createTitledBorder(null, "Thời gian bay",
                0, 0, new Font(Constants.FB_FONT, Font.BOLD, 16)));

        JLabel departureTimeLabel = new JLabel("Giờ cất cánh");
        departureTimeLabel.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 14));

        JPanel departureTimePanel = new JPanel(new GridLayout(2, 2, 10, 10));
        departureTimePanel.setBackground(new Color(245, 245, 245));

        JButton btnMidnightToMorning = new JButton("00:00 - 06:00");
        JButton btnMorningToNoon = new JButton("06:00 - 12:00");
        JButton btnNoonToEvening = new JButton("12:00 - 18:00");
        JButton btnEveningToMidnight = new JButton("18:00 - 24:00");

        styleTimeButton(btnMidnightToMorning);
        styleTimeButton(btnMorningToNoon);
        styleTimeButton(btnNoonToEvening);
        styleTimeButton(btnEveningToMidnight);

        departureTimePanel.add(btnMidnightToMorning);
        departureTimePanel.add(btnMorningToNoon);
        departureTimePanel.add(btnNoonToEvening);
        departureTimePanel.add(btnEveningToMidnight);

        JLabel arrivalTimeLabel = new JLabel("Giờ hạ cánh");
        arrivalTimeLabel.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 14));

        JPanel arrivalTimePanel = new JPanel(new GridLayout(2, 2, 10, 10));
        arrivalTimePanel.setBackground(new Color(245, 245, 245));

        JButton btnArrivalMidnightToMorning = new JButton("00:00 - 06:00");
        JButton btnArrivalMorningToNoon = new JButton("06:00 - 12:00");
        JButton btnArrivalNoonToEvening = new JButton("12:00 - 18:00");
        JButton btnArrivalEveningToMidnight = new JButton("18:00 - 24:00");

        styleTimeButton(btnArrivalMidnightToMorning);
        styleTimeButton(btnArrivalMorningToNoon);
        styleTimeButton(btnArrivalNoonToEvening);
        styleTimeButton(btnArrivalEveningToMidnight);

        arrivalTimePanel.add(btnArrivalMidnightToMorning);
        arrivalTimePanel.add(btnArrivalMorningToNoon);
        arrivalTimePanel.add(btnArrivalNoonToEvening);
        arrivalTimePanel.add(btnArrivalEveningToMidnight);

        timeFilterPanel.add(departureTimeLabel);
        timeFilterPanel.add(departureTimePanel);
        timeFilterPanel.add(arrivalTimeLabel);
        timeFilterPanel.add(arrivalTimePanel);

        // Add sections to the main panel
        filterPanel.add(headerPanel);
        filterPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        filterPanel.add(airlineFilterPanel);
        filterPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        filterPanel.add(timeFilterPanel);

        return filterPanel;
    }

    private JScrollPane createFlightListPanel() {
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);

        for (int i = 0; i < 10; i++) {
            listPanel.add(createFlightCard("Vietravel Airlines", "23:59", "01:25+1", "SGN", "DAD", "2,335,563 VND"));
            listPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        return scrollPane;
    }

    private JPanel createFlightCard(String airline, String departure, String arrival, String from, String to, String price) {
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BorderLayout());
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setPreferredSize(new Dimension(500, 100));

        JLabel airlineLabel = new JLabel(airline);
        airlineLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 14));

        JLabel timeLabel = new JLabel(departure + " - " + arrival);
        timeLabel.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 14));

        JLabel routeLabel = new JLabel(from + " → " + to);
        routeLabel.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 14));

        JLabel priceLabel = new JLabel(price);
        priceLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        priceLabel.setForeground(new Color(255, 69, 0));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);

        textPanel.add(airlineLabel);
        textPanel.add(timeLabel);
        textPanel.add(routeLabel);

        cardPanel.add(textPanel, BorderLayout.CENTER);
        cardPanel.add(priceLabel, BorderLayout.EAST);

        return cardPanel;
    }

    private void styleTimeButton(JButton button) {
        button.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 14));
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        button.setFocusPainted(false);
    }

    private void styleCheckbox(JCheckBox checkBox) {
        checkBox.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 14));
        checkBox.setBackground(new Color(245, 245, 245));
        checkBox.setFocusPainted(false);
    }
}
