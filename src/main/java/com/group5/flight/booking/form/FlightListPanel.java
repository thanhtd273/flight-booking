package com.group5.flight.booking.form;

import com.group5.flight.booking.core.AppUtils;
import com.group5.flight.booking.core.Constants;
import com.group5.flight.booking.dto.FlightInfo;
import com.group5.flight.booking.dto.FilterCriteria;
import com.group5.flight.booking.model.Airline;
import com.group5.flight.booking.service.AirlineService;
import com.group5.flight.booking.service.FlightService;
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

    @Setter
    private List<FlightInfo> flightInfoList;

    private FilterCriteria filterCriteria;

    private final AirlineService airlineService;

    private final FlightService flightService;

    public FlightListPanel(Long fromAirportId, Long toAirportId, Date departureDate, AirlineService airlineService, FlightService flightService) {
        this.airlineService = airlineService;
        this.flightService = flightService;
        setBaseFilterCriteria(fromAirportId, toAirportId, departureDate);
        this.fromAirportId = fromAirportId;
        this.toAirportId = toAirportId;
        this.departureDate = departureDate;

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
        airlineFilterPanel.setBorder(BorderFactory.createTitledBorder("Airlines"));
        Dimension fixedSize = new Dimension(300, 300);
        airlineFilterPanel.setPreferredSize(fixedSize);
        airlineFilterPanel.setMinimumSize(fixedSize);
        airlineFilterPanel.setMaximumSize(fixedSize);
        airlineFilterPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

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
            airlineFilterPanel.add(airlineCheckbox);
            airlineFilterPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        airlineFilterPanel.add(Box.createVerticalGlue());

        // Flight time filter section
        JPanel timeFilterPanel = new JPanel();
        timeFilterPanel.setLayout(new BoxLayout(timeFilterPanel, BoxLayout.Y_AXIS));
        timeFilterPanel.setBackground(Color.WHITE);
        timeFilterPanel.setBorder(BorderFactory.createTitledBorder("Time"));
        timeFilterPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

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
            listPanel.add(createFlightCard(
                    flightInfo.getAirline().getName(),
                    AppUtils.formatTime(flightInfo.getDepatureDate()),
                    AppUtils.formatTime(flightInfo.getReturnDate()),
                    flightInfo.getFromAirport().getAirportCode(),
                    flightInfo.getToAirport().getAirportCode(),
                    String.valueOf(flightInfo.getBasePrice())
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
        cardPanel.setPreferredSize(new Dimension(600, 140));

        // Panel chứa thông tin chính (hàng ngang)
        JPanel mainInfoPanel = new JPanel();
        mainInfoPanel.setLayout(new BoxLayout(mainInfoPanel, BoxLayout.X_AXIS));
        mainInfoPanel.setBackground(Color.WHITE);

        // Hãng bay
        JPanel airlinePanel = new JPanel();
        airlinePanel.setLayout(new BoxLayout(airlinePanel, BoxLayout.Y_AXIS));
        airlinePanel.setBackground(Color.WHITE);
        JLabel airlineLabel = new JLabel(airline);
        airlineLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 14));
        airlinePanel.add(airlineLabel);

        JPanel flightInfoPanel = new JPanel();
        flightInfoPanel.setLayout(new BoxLayout(flightInfoPanel, BoxLayout.Y_AXIS));
        flightInfoPanel.setBackground(Color.WHITE);
        JLabel timeLabel = new JLabel(departure + " → " + arrival);
        timeLabel.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 14));
        JLabel airportLabel = new JLabel(from + " → " + to);
        airportLabel.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 14));
        airportLabel.setForeground(new Color(100, 100, 100));
        flightInfoPanel.add(timeLabel);
        flightInfoPanel.add(airportLabel);

        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.Y_AXIS));
        pricePanel.setBackground(Color.WHITE);
        JLabel priceLabel = new JLabel(price);
        priceLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        priceLabel.setForeground(new Color(255, 69, 0)); // Màu giá vé đỏ
        pricePanel.add(priceLabel);

        mainInfoPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        mainInfoPanel.add(airlinePanel);
        mainInfoPanel.add(Box.createHorizontalGlue());
        mainInfoPanel.add(flightInfoPanel);
        mainInfoPanel.add(Box.createHorizontalGlue());
        mainInfoPanel.add(pricePanel);
        mainInfoPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BorderLayout()); // Sử dụng BorderLayout để căn hai nút
        actionPanel.setBackground(Color.WHITE);

        JButton detailsButton = new JButton("Detail");
        JButton selectButton = new JButton("Select");

        styleActionButton(detailsButton);
        styleActionButton(selectButton);

        actionPanel.add(detailsButton, BorderLayout.WEST);
        actionPanel.add(selectButton, BorderLayout.EAST);

        cardPanel.add(mainInfoPanel, BorderLayout.CENTER);
        cardPanel.add(actionPanel, BorderLayout.SOUTH);

        return cardPanel;
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
    }
}
