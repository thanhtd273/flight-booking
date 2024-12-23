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
        filterPanel.setPreferredSize(new Dimension(300, 700)); // You can adjust this value as needed

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
        airlineFilterPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Align to the left

        // Set maximum width for the airline filter panel
        airlineFilterPanel.setMaximumSize(new Dimension(filterPanel.getPreferredSize().width, Integer.MAX_VALUE));


        // Airline 1
        JPanel airline1Panel = createAirlineCheckbox("Malaysia Airlines", "12.350.857 VND", "/Malaysia.png");
        airlineFilterPanel.add(airline1Panel);

        // Airline 2
        JPanel airline2Panel = createAirlineCheckbox("VietJet Air", "5.595.711 VND", "/VietJet.png");
        airlineFilterPanel.add(airline2Panel);

        // Airline 3
        JPanel airline3Panel = createAirlineCheckbox("Vietnam Airlines", "2.422.825 VND", "/Vietnam.png");
        airlineFilterPanel.add(airline3Panel);

        // Airline 4
        JPanel airline4Panel = createAirlineCheckbox("Vietravel Airlines", "2.335.563 VND", "/Vietravel.png");
        airlineFilterPanel.add(airline4Panel);

        // Add sections to the main panel
        filterPanel.add(headerPanel);
        filterPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        filterPanel.add(airlineFilterPanel);

        // Flight time filter section
        JPanel timeFilterPanel = new JPanel();
        timeFilterPanel.setLayout(new BoxLayout(timeFilterPanel, BoxLayout.Y_AXIS));
        timeFilterPanel.setBackground(Color.WHITE);
        timeFilterPanel.setBorder(BorderFactory.createTitledBorder("Thời gian bay"));
        timeFilterPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Align to the left

        // Set maximum width for the time filter panel
        timeFilterPanel.setMaximumSize(new Dimension(filterPanel.getPreferredSize().width, Integer.MAX_VALUE));

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
        cardPanel.setPreferredSize(new Dimension(600, 160)); // Tăng chiều cao để có thêm khoảng cách

        JPanel mainInfoPanel = new JPanel();
        mainInfoPanel.setLayout(new BoxLayout(mainInfoPanel, BoxLayout.X_AXIS));
        mainInfoPanel.setBackground(Color.WHITE);

        JPanel airlinePanel = new JPanel();
        airlinePanel.setLayout(new BoxLayout(airlinePanel, BoxLayout.Y_AXIS));
        airlinePanel.setBackground(Color.WHITE);
        JLabel airlineLabel = new JLabel(airline);
        airlineLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        airlinePanel.add(airlineLabel);

        JPanel flightInfoPanel = new JPanel();
        flightInfoPanel.setLayout(new BoxLayout(flightInfoPanel, BoxLayout.Y_AXIS));
        flightInfoPanel.setBackground(Color.WHITE);
        JLabel timeLabel = new JLabel("Thời gian: " + departure + " → " + arrival);
        timeLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JLabel airportLabel = new JLabel("Sân bay: " + from + " → " + to);
        airportLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        airportLabel.setForeground(new Color(100, 100, 100));
        flightInfoPanel.add(timeLabel);
        flightInfoPanel.add(airportLabel);

        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.Y_AXIS));
        pricePanel.setBackground(Color.WHITE);
        JLabel priceLabel = new JLabel(price);
        priceLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        priceLabel.setForeground(new Color(255, 69, 0));
        pricePanel.add(priceLabel);

        mainInfoPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        mainInfoPanel.add(airlinePanel);
        mainInfoPanel.add(Box.createHorizontalGlue());
        mainInfoPanel.add(flightInfoPanel);
        mainInfoPanel.add(Box.createHorizontalGlue());
        mainInfoPanel.add(pricePanel);
        mainInfoPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BorderLayout());
        actionPanel.setBackground(Color.WHITE);
        actionPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10)); // Thêm khoảng cách dưới và hai bên

        // Nút "Chi tiết"
        JPanel detailsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        detailsPanel.setBackground(Color.WHITE);
        JButton detailsButton = new JButton("Chi tiết");
        detailsButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
        detailsButton.setForeground(new Color(0, 123, 255));
        detailsButton.setContentAreaFilled(false);
        detailsButton.setBorder(null);
        detailsPanel.add(detailsButton);

        // Nút "Chọn"
        JPanel selectPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        selectPanel.setBackground(Color.WHITE);
        JButton selectButton = new JButton("Chọn") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                g2.setColor(new Color(0, 123, 255));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

                g2.dispose();

                super.paintComponent(g);
            }
        };

        selectButton.setPreferredSize(new Dimension(100, 30));
        selectButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
        selectButton.setBackground(new Color(0, 123, 255));
        selectButton.setForeground(Color.WHITE);
        selectButton.setContentAreaFilled(false);
        selectButton.setOpaque(false);
        selectButton.setFocusPainted(false);
        selectButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        selectPanel.add(selectButton);

        actionPanel.add(detailsPanel, BorderLayout.WEST); // Đặt nút "Chi tiết" ở lề trái
        actionPanel.add(selectPanel, BorderLayout.EAST);  // Đặt nút "Chọn" ở lề phải

        cardPanel.add(mainInfoPanel, BorderLayout.CENTER);
        cardPanel.add(actionPanel, BorderLayout.SOUTH);

        return cardPanel;
    }


    private ImageIcon resizeIcon(String resourcePath, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(resourcePath));
            return new ImageIcon(icon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));
        } catch (Exception e) {
            System.err.println("Failed to load image: " + resourcePath);
            return null;
        }
    }

    private JPanel createAirlineCheckbox(String airlineName, String price, String iconPath) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS)); // Sắp xếp theo chiều ngang
        panel.setBackground(Color.WHITE);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT); // Căn lề trái cho toàn bộ panel

        // Tạo checkbox
        JCheckBox airlineCheckbox = new JCheckBox();
        airlineCheckbox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        airlineCheckbox.setBackground(Color.WHITE);
        airlineCheckbox.setFocusPainted(false);
        airlineCheckbox.setAlignmentY(Component.CENTER_ALIGNMENT); // Giữ checkbox thẳng hàng theo chiều dọc

        // Tạo icon hãng hàng không
        JLabel airlineIcon = new JLabel();
        airlineIcon.setIcon(resizeIcon(iconPath, 20, 20)); // Icon kích thước 20x20
        airlineIcon.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); // Khoảng cách xung quanh icon
        airlineIcon.setAlignmentY(Component.CENTER_ALIGNMENT); // Giữ icon thẳng hàng theo chiều dọc

        // Panel chứa tên hãng và giá (theo chiều dọc)
        JPanel verticalPanel = new JPanel();
        verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS)); // Sắp xếp dọc
        verticalPanel.setBackground(Color.WHITE);
        verticalPanel.setAlignmentY(Component.CENTER_ALIGNMENT); // Giữ cả tên và giá thẳng hàng theo chiều dọc

        // Tạo nhãn tên hãng
        JLabel airlineNameLabel = new JLabel(airlineName);
        airlineNameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        airlineNameLabel.setForeground(Color.BLACK);
        airlineNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Căn trái

        // Tạo nhãn giá
        JLabel priceLabel = new JLabel(price);
        priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        priceLabel.setForeground(new Color(255, 69, 0)); // Màu cam cho giá
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Căn trái

        // Thêm tên hãng và giá vào verticalPanel
        verticalPanel.add(airlineNameLabel);
        verticalPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Khoảng cách giữa tên và giá
        verticalPanel.add(priceLabel);

        // Thêm checkbox, icon, và verticalPanel vào panel chính
        panel.add(airlineCheckbox);
        panel.add(airlineIcon);
        panel.add(Box.createRigidArea(new Dimension(10, 0))); // Khoảng cách giữa icon và tên
        panel.add(verticalPanel);

        return panel;
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
        checkBox.setAlignmentX(Component.LEFT_ALIGNMENT); // Đảm bảo checkbox căn trái
    }

}
