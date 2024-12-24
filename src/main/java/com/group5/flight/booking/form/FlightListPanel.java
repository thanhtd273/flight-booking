package com.group5.flight.booking.form;

import com.group5.flight.booking.core.AppUtils;
import com.group5.flight.booking.core.Constants;
import com.group5.flight.booking.dto.FlightInfo;
import com.group5.flight.booking.dto.FilterCriteria;
import com.group5.flight.booking.model.Airline;
import com.group5.flight.booking.service.AirlineService;
import com.group5.flight.booking.service.FlightService;
import com.group5.flight.booking.service.PlaneService;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class FlightListPanel extends JPanel {

    private static final Logger logger = LoggerFactory.getLogger(FlightListPanel.class);

    private final Long fromAirportId;

    private final Long toAirportId;

    private final Date departureDate;

    private final JPanel mainPanel;

    private final CardLayout cardLayout;

    @Setter
    private List<FlightInfo> flightInfoList;

    private FilterCriteria filterCriteria;

    private final AirlineService airlineService;

    private final FlightService flightService;

    private final PlaneService planeService;


    public FlightListPanel(JPanel mainPanel, CardLayout cardLayout, Long fromAirportId,
                           Long toAirportId, Date departureDate, List<FlightInfo> flightInfoList,
                           AirlineService airlineService, FlightService flightService, PlaneService planeService) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.airlineService = airlineService;
        this.flightService = flightService;
        this.planeService = planeService;
        setBaseFilterCriteria(fromAirportId, toAirportId, departureDate);
        this.fromAirportId = fromAirportId;
        this.toAirportId = toAirportId;
        this.departureDate = departureDate;
        this.flightInfoList = flightInfoList;

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

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setBackground(Color.WHITE);

        JLabel headerLabel = new JLabel("Filter");
        headerLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 20));
        headerLabel.setForeground(new Color(34, 139, 34)); // Dark green color

        JButton resetButton = new JButton("Reset");
        resetButton.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 14));
        resetButton.setForeground(new Color(0, 123, 255)); // Blue color for the button
        resetButton.setContentAreaFilled(false);
        resetButton.addActionListener(e -> {
            setBaseFilterCriteria(fromAirportId, toAirportId, departureDate);
            updateFilterResult();
        });
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


        List<Airline> airlineList = airlineService.getAllAirlines();
        logger.debug("airlineList: {}", airlineList);
        airlineFilterPanel.add(Box.createVerticalGlue());
        for (Airline airline: airlineList) {
            JCheckBox airlineCheckbox = new JCheckBox(airline.getName());
            styleCheckbox(airlineCheckbox);
            airlineCheckbox.addActionListener(e -> {
                List<Long> currentIds = filterCriteria.getAirlineIds();
                if (currentIds.contains(airline.getAirlineId())) {
                    currentIds.remove(airline.getAirlineId());
                } else {
                    currentIds.add(airline.getAirlineId());
                }
                filterCriteria.setAirlineIds(currentIds);
                updateFilterResult();
            });
            airlineFilterPanel.add(createAirlineCheckboxWithIcon(airlineCheckbox, airline));
            airlineFilterPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        airlineFilterPanel.add(Box.createVerticalGlue());

        // Flight time filter section
        JPanel timeFilterPanel = new JPanel();
        timeFilterPanel.setLayout(new BoxLayout(timeFilterPanel, BoxLayout.Y_AXIS));
        timeFilterPanel.setBackground(Color.WHITE);
        timeFilterPanel.setBorder(BorderFactory.createTitledBorder("Thời gian bay"));
        timeFilterPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Align to the left

        // Set maximum width for the time filter panel
        timeFilterPanel.setMaximumSize(new Dimension(filterPanel.getPreferredSize().width, Integer.MAX_VALUE));

        JLabel departureTimeLabel = new JLabel("Departure Time");
        departureTimeLabel.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 14));
        departureTimeLabel.setForeground(new Color(34, 139, 34));
        departureTimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel departureTimePanel = new JPanel(new GridLayout(2, 2, 10, 10));
        departureTimePanel.setBackground(Color.WHITE);

        for (String timePeriod: Constants.TIME_PERIOD_MAP.keySet()) {
            JButton departureTimeBtn = new JButton(timePeriod);
            styleTimeButton(departureTimeBtn);
            departureTimePanel.add(departureTimeBtn);
        }

        JLabel arrivalTimeLabel = new JLabel("Arrival Time");
        arrivalTimeLabel.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 14));
        arrivalTimeLabel.setForeground(new Color(34, 139, 34));
        arrivalTimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel arrivalTimePanel = new JPanel(new GridLayout(2, 2, 10, 10));
        arrivalTimePanel.setBackground(Color.WHITE);

        for (String timePeriod: Constants.TIME_PERIOD_MAP.keySet()) {
            JButton arrivalTimeBtn = new JButton(timePeriod);
            styleTimeButton(arrivalTimeBtn);
            arrivalTimePanel.add(arrivalTimeBtn);
        }

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

        for (FlightInfo flightInfo: flightInfoList) {
            listPanel.add(createFlightCard(flightInfo));
            listPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        return scrollPane;
    }


    private JPanel createFlightCard(FlightInfo flightInfo) {
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
        JLabel airlineLabel = new JLabel(flightInfo.getAirline().getName());
        airlineLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 14));
        airlinePanel.add(airlineLabel);

        JPanel flightInfoPanel = new JPanel();
        flightInfoPanel.setLayout(new BoxLayout(flightInfoPanel, BoxLayout.Y_AXIS));
        flightInfoPanel.setBackground(Color.WHITE);
        JLabel timeLabel = new JLabel(AppUtils.formatTime(flightInfo.getDepatureDate()) + " → " + AppUtils.formatTime(flightInfo.getReturnDate()));
        timeLabel.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 14));
        JLabel airportLabel = new JLabel(flightInfo.getFromAirport().getAirportCode() + " → " + flightInfo.getToAirport().getAirportCode());
        airportLabel.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 14));
        airportLabel.setForeground(new Color(100, 100, 100));
        flightInfoPanel.add(timeLabel);
        flightInfoPanel.add(airportLabel);

        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.Y_AXIS));
        pricePanel.setBackground(Color.WHITE);
        JLabel priceLabel = new JLabel(String.valueOf(flightInfo.getBasePrice()));
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
        JButton selectButton = getSelectButton();
        selectButton.addActionListener(e -> {
            FlightDetailPanel detailPanel = new FlightDetailPanel(mainPanel, cardLayout, flightInfo,
                    planeService, flightService);
            mainPanel.add(detailPanel, Constants.FLIGHT_DETAIL_SCREEN);
            cardLayout.show(mainPanel, Constants.FLIGHT_DETAIL_SCREEN);
        });
        selectPanel.add(selectButton);

        actionPanel.add(detailsPanel, BorderLayout.WEST); // Đặt nút "Chi tiết" ở lề trái
        actionPanel.add(selectPanel, BorderLayout.EAST);  // Đặt nút "Chọn" ở lề phải

        cardPanel.add(mainInfoPanel, BorderLayout.CENTER);
        cardPanel.add(actionPanel, BorderLayout.SOUTH);

        return cardPanel;
    }

    private JButton getSelectButton() {
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
        return selectButton;
    }

    private void updateFilterResult() {
        try {
            flightInfoList = flightService.filter(filterCriteria);
        } catch (Exception e) {
            logger.error("Filter flight failed, error: {}", e.getMessage());
            AppUtils.showErrorDialog(e.getMessage());
        }

    }

    private void setBaseFilterCriteria(Long fromAirportId, Long toAirportId, Date departureDate) {
        this.filterCriteria = new FilterCriteria();
        this.filterCriteria.setFromAirportId(fromAirportId);
        this.filterCriteria.setToAirportId(toAirportId);
        this.filterCriteria.setDepartureDate(departureDate);
    }

    private JPanel createAirlineCheckboxWithIcon(JCheckBox airlineCheckbox, Airline airline) {
        // Tạo đường dẫn đến biểu tượng của hãng hàng không
        String iconPath = "";
        switch (airline.getName()) {
            case "VietJet Air":
                iconPath = "/VietJet.png";
                break;
            case "Vietnam Airlines":
                iconPath = "/Vietnam.png";
                break;
            case "Bamboo Airways":
                iconPath = "/Bambo.png";
                break;
            case "Pacific Airlines":
                iconPath = "/pacific.png";
                break;
            case "VASCO":
                iconPath = "/vasco.png";
                break;
        }

        // Tải và điều chỉnh kích thước của biểu tượng
        ImageIcon icon = resizeIcon(iconPath, 15, 15);

        // Tạo một JPanel để chứa Checkbox, Biểu tượng và Tên hãng
        JPanel airlinePanel = new JPanel();
        airlinePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5)); // Căn lề bên trái
        airlinePanel.setBackground(Color.WHITE);

        // Đảm bảo biểu tượng chỉ xuất hiện khi có đường dẫn hình ảnh hợp lệ
        if (icon != null) {
            JLabel airlineIconLabel = new JLabel(icon);  // Đặt biểu tượng
            airlinePanel.add(airlineCheckbox);          // Thêm JCheckBox
            airlinePanel.add(airlineIconLabel);         // Thêm biểu tượng
        } else {
            airlinePanel.add(airlineCheckbox);          // Thêm JCheckBox
        }

        // Thêm tên hãng hàng không vào JLabel riêng
        JLabel airlineNameLabel = new JLabel(airline.getName());
        airlineNameLabel.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 12));
        airlineNameLabel.setForeground(Color.BLACK);
        airlinePanel.add(airlineNameLabel);            // Thêm tên hãng

        return airlinePanel; // Trả về JPanel đã cấu hình
    }

    private ImageIcon resizeIcon(String resourcePath, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(resourcePath));
            return new ImageIcon(icon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));
        } catch (Exception e) {
            logger.error("Failed to load image: {}", resourcePath);
            return null;
        }
    }

    private void styleActionButton(JButton button) {
        button.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 12));
        button.setBackground(new Color(0, 123, 255)); // Màu xanh cho nút
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        button.setFocusPainted(false);
    }


    private void styleTimeButton(JButton button) {
        button.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 14));
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        button.setFocusPainted(false);
    }

    private void styleCheckbox(JCheckBox checkBox) {
        checkBox.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 20));
        checkBox.setBackground(Color.WHITE);
        checkBox.setFocusPainted(false);
        checkBox.setAlignmentX(Component.LEFT_ALIGNMENT); // Đảm bảo checkbox căn trái
    }

}
