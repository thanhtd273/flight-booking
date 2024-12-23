package com.group5.flight.booking.form;

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
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setPreferredSize(new Dimension(300, 700));

        // Header panel with "Bộ lọc" on the top left and "Đặt lại" on the top right
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setBackground(Color.WHITE);

        JLabel headerLabel = new JLabel("Bộ lọc");
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        headerLabel.setForeground(new Color(34, 139, 34)); // Dark green color

        JButton resetButton = new JButton("Đặt lại");
        resetButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        resetButton.setForeground(new Color(0, 123, 255)); // Blue color for the button
        resetButton.setContentAreaFilled(false);
        resetButton.setBorder(null);

        headerPanel.add(headerLabel);
        headerPanel.add(Box.createHorizontalGlue());
        headerPanel.add(resetButton);

        // Airline filter section
        JPanel airlineFilterPanel = new JPanel();
        airlineFilterPanel.setLayout(new BoxLayout(airlineFilterPanel, BoxLayout.Y_AXIS));
        airlineFilterPanel.setBackground(Color.WHITE);
        airlineFilterPanel.setBorder(BorderFactory.createTitledBorder("Hãng hàng không"));

        airlineFilterPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment

        JCheckBox airline1 = new JCheckBox("Malaysia Airlines - 12.350.857 VND");
        JCheckBox airline2 = new JCheckBox("VietJet Air - 5.595.711 VND");
        JCheckBox airline3 = new JCheckBox("Vietnam Airlines - 2.422.825 VND");
        JCheckBox airline4 = new JCheckBox("Vietravel Airlines - 2.335.563 VND");

        styleCheckbox(airline1);
        styleCheckbox(airline2);
        styleCheckbox(airline3);
        styleCheckbox(airline4);

        airlineFilterPanel.add(Box.createVerticalGlue());
        airlineFilterPanel.add(airline1);
        airlineFilterPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        airlineFilterPanel.add(airline2);
        airlineFilterPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        airlineFilterPanel.add(airline3);
        airlineFilterPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        airlineFilterPanel.add(airline4);
        airlineFilterPanel.add(Box.createVerticalGlue());

        // Flight time filter section
        JPanel timeFilterPanel = new JPanel();
        timeFilterPanel.setLayout(new BoxLayout(timeFilterPanel, BoxLayout.Y_AXIS));
        timeFilterPanel.setBackground(Color.WHITE);
        timeFilterPanel.setBorder(BorderFactory.createTitledBorder("Thời gian bay"));
        timeFilterPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel departureTimeLabel = new JLabel("Giờ cất cánh");
        departureTimeLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        departureTimeLabel.setForeground(new Color(34, 139, 34));
        departureTimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel departureTimePanel = new JPanel(new GridLayout(2, 2, 10, 10));
        departureTimePanel.setBackground(Color.WHITE);

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
        arrivalTimeLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        arrivalTimeLabel.setForeground(new Color(34, 139, 34));
        arrivalTimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel arrivalTimePanel = new JPanel(new GridLayout(2, 2, 10, 10));
        arrivalTimePanel.setBackground(Color.WHITE);

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
        timeFilterPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        timeFilterPanel.add(departureTimePanel);
        timeFilterPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        timeFilterPanel.add(arrivalTimeLabel);
        timeFilterPanel.add(Box.createRigidArea(new Dimension(0, 5)));
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
            listPanel.add(createFlightCard(
                    "Vietravel Airlines",
                    "23:59",
                    "01:25+1",
                    "SGN",
                    "DAD",
                    "2,335,563 VND"
            ));
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
        cardPanel.setPreferredSize(new Dimension(600, 140)); // Tăng chiều cao để chứa thêm thông tin

        // Panel chứa thông tin chính (hàng ngang)
        JPanel mainInfoPanel = new JPanel();
        mainInfoPanel.setLayout(new BoxLayout(mainInfoPanel, BoxLayout.X_AXIS));
        mainInfoPanel.setBackground(Color.WHITE);

        // Hãng bay
        JPanel airlinePanel = new JPanel();
        airlinePanel.setLayout(new BoxLayout(airlinePanel, BoxLayout.Y_AXIS));
        airlinePanel.setBackground(Color.WHITE);
        JLabel airlineLabel = new JLabel(airline);
        airlineLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        airlinePanel.add(airlineLabel);

        // Thời gian và sân bay
        JPanel flightInfoPanel = new JPanel();
        flightInfoPanel.setLayout(new BoxLayout(flightInfoPanel, BoxLayout.Y_AXIS));
        flightInfoPanel.setBackground(Color.WHITE);
        JLabel timeLabel = new JLabel("Thời gian: " + departure + " → " + arrival);
        timeLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JLabel airportLabel = new JLabel("Sân bay: " + from + " → " + to);
        airportLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        airportLabel.setForeground(new Color(100, 100, 100)); // Màu xám cho thông tin phụ
        flightInfoPanel.add(timeLabel);
        flightInfoPanel.add(airportLabel);

        // Giá vé
        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.Y_AXIS));
        pricePanel.setBackground(Color.WHITE);
        JLabel priceLabel = new JLabel(price);
        priceLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        priceLabel.setForeground(new Color(255, 69, 0)); // Màu giá vé đỏ
        pricePanel.add(priceLabel);

        // Thêm các phần tử vào hàng ngang
        mainInfoPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Khoảng cách trái
        mainInfoPanel.add(airlinePanel);
        mainInfoPanel.add(Box.createHorizontalGlue());
        mainInfoPanel.add(flightInfoPanel);
        mainInfoPanel.add(Box.createHorizontalGlue());
        mainInfoPanel.add(pricePanel);
        mainInfoPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Khoảng cách phải

        // Panel chứa các nút "Chi tiết" và "Chọn"
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BorderLayout()); // Sử dụng BorderLayout để căn hai nút
        actionPanel.setBackground(Color.WHITE);

        JButton detailsButton = new JButton("Chi tiết");
        JButton selectButton = new JButton("Chọn");

        styleActionButton(detailsButton);
        styleActionButton(selectButton);

        actionPanel.add(detailsButton, BorderLayout.WEST); // Nút "Chi tiết" bên trái
        actionPanel.add(selectButton, BorderLayout.EAST); // Nút "Chọn" bên phải

        // Thêm các phần tử vào cardPanel
        cardPanel.add(mainInfoPanel, BorderLayout.CENTER);  // Thông tin chính ở giữa
        cardPanel.add(actionPanel, BorderLayout.SOUTH);     // Các nút ở dưới

        return cardPanel;
    }

    private void styleActionButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.PLAIN, 12));
        button.setBackground(new Color(0, 123, 255)); // Màu xanh cho nút
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        button.setFocusPainted(false);
    }


    private void styleTimeButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        button.setFocusPainted(false);
    }

    private void styleCheckbox(JCheckBox checkBox) {
        checkBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        checkBox.setBackground(Color.WHITE);
        checkBox.setFocusPainted(false);
    }
}
