package com.group1.flight.booking.form;

import com.group1.flight.booking.core.AppUtils;
import com.group1.flight.booking.core.Constants;
import com.group1.flight.booking.dto.FlightInfo;
import com.group1.flight.booking.dto.FilterCriteria;
import com.group1.flight.booking.dto.BookingInfo;
import com.group1.flight.booking.model.Airline;
import com.group1.flight.booking.service.AirlineService;
import com.group1.flight.booking.service.BookingService;
import com.group1.flight.booking.service.FlightService;
import com.group1.flight.booking.service.NationService;
import lombok.Setter;
import org.apache.commons.lang3.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FlightListPanel extends JPanel {

    private static final Logger logger = LoggerFactory.getLogger(FlightListPanel.class);

    private static final String MIDNIGHT_TO_MORNING = "00:00 - 06:00";

    private static final String MORNING_TO_NOON = "06:00 - 12:00";

    private static final String NOON_TO_EVENING = "12:00 - 18:00";

    private static final String EVENING_TO_MIDNIGHT = "18:00 - 24:00";

    private static final Map<String, Range<LocalTime>> TIME_PERIOD_MAP =
            Map.of(MIDNIGHT_TO_MORNING, Range.of(LocalTime.of(0, 0), LocalTime.of(6, 0)),
                    MORNING_TO_NOON, Range.of(LocalTime.of(6, 0), LocalTime.of(12, 0)),
                    NOON_TO_EVENING, Range.of(LocalTime.of(12, 0), LocalTime.of(18, 0)),
                    EVENING_TO_MIDNIGHT, Range.of(LocalTime.of(18, 0), LocalTime.of(0, 0)));

    private final JPanel mainPanel;

    private final CardLayout cardLayout;

    private final BookingInfo bookingInfo;

    @Setter
    private List<FlightInfo> flightInfoList;

    private FilterCriteria filterCriteria;

    private final AirlineService airlineService;

    private final FlightService flightService;

    private final BookingService bookingService;

    private final NationService nationService;

    public FlightListPanel(JPanel mainPanel, CardLayout cardLayout, BookingInfo bookingInfo, List<FlightInfo> flightInfoList,
                           AirlineService airlineService, FlightService flightService, BookingService bookingService, NationService nationService) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.airlineService = airlineService;
        this.flightService = flightService;
        this.bookingService = bookingService;
        this.nationService = nationService;

        this.bookingInfo = bookingInfo;
        setBaseFilterCriteria();
        this.flightInfoList = flightInfoList;

        initComponent();
    }

    private void initComponent() {
        setLayout(new BorderLayout());
        add(createFilterSidebar(filterCriteria), BorderLayout.WEST);
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, Constants.FLIGHT_SEARCHER_SCREEN));
        add(backButton, BorderLayout.NORTH);
        add(createFlightListPanel(flightInfoList), BorderLayout.CENTER);
    }

    private JPanel createFilterSidebar(FilterCriteria filterCriteria) {
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
            setBaseFilterCriteria();
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
        airlineFilterPanel.setBorder(BorderFactory.createTitledBorder("Airline"));
        airlineFilterPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Set maximum width for the airline filter panel
        airlineFilterPanel.setMaximumSize(new Dimension(filterPanel.getPreferredSize().width, Integer.MAX_VALUE));


        List<Airline> airlineList = airlineService.getAllAirlines();
        airlineFilterPanel.add(Box.createVerticalGlue());
        for (Airline airline: airlineList) {
            JCheckBox airlineCheckbox = new JCheckBox(airline.getName());
            styleCheckbox(airlineCheckbox);

            boolean isSelected = filterCriteria.getAirlineIds() != null && filterCriteria.getAirlineIds().contains(airline.getAirlineId());
            airlineCheckbox.setSelected(isSelected);

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
        timeFilterPanel.setBorder(BorderFactory.createTitledBorder("Time"));
        timeFilterPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Align to the left

        // Set maximum width for the time filter panel
        timeFilterPanel.setMaximumSize(new Dimension(filterPanel.getPreferredSize().width, Integer.MAX_VALUE));

        JLabel departureTimeLabel = new JLabel("Departure Time");
        departureTimeLabel.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 14));
        departureTimeLabel.setForeground(new Color(34, 139, 34));
        departureTimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel departureTimePanel = new JPanel(new GridLayout(2, 2, 10, 10));
        departureTimePanel.setBackground(Color.WHITE);

        for (String timePeriod: TIME_PERIOD_MAP.keySet()) {
            Range<LocalTime> timeRange = TIME_PERIOD_MAP.get(timePeriod);

            JButton departureTimeBtn = new JButton(timePeriod);
            styleTimeButton(departureTimeBtn);
            List<Range<LocalTime>> departureTimeRange = filterCriteria.getDepartureTimes();
            boolean isSelected = departureTimeRange.contains(timeRange);
            departureTimeBtn.setBackground(isSelected ? Color.LIGHT_GRAY : Color.WHITE);

            departureTimeBtn.addActionListener(e -> {
                departureTimeBtn.setBackground(isSelected ? Color.WHITE : Color.GRAY);
                if (isSelected) {
                    departureTimeRange.remove(timeRange);
                } else {
                    departureTimeRange.add(timeRange);
                }

                filterCriteria.setDepartureTimes(departureTimeRange);
                updateFilterResult();
            });
            departureTimePanel.add(departureTimeBtn);
        }

        JLabel arrivalTimeLabel = new JLabel("Arrival Time");
        arrivalTimeLabel.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 14));
        arrivalTimeLabel.setForeground(new Color(34, 139, 34));
        arrivalTimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel arrivalTimePanel = new JPanel(new GridLayout(2, 2, 10, 10));
        arrivalTimePanel.setBackground(Color.WHITE);

        for (String timePeriod: TIME_PERIOD_MAP.keySet()) {
            Range<LocalTime> timeRange = TIME_PERIOD_MAP.get(timePeriod);
            JButton arrivalTimeBtn = new JButton(timePeriod);
            styleTimeButton(arrivalTimeBtn);
            List<Range<LocalTime>> arrivalTimeRange = filterCriteria.getArrivalTimes();
            boolean isSelected = arrivalTimeRange.contains(timeRange);
            arrivalTimeBtn.setBackground(isSelected ? Color.LIGHT_GRAY : Color.WHITE);

            arrivalTimeBtn.addActionListener(e -> {
                arrivalTimeBtn.setBackground(isSelected ? Color.WHITE : Color.GRAY);
                if (isSelected) {
                    arrivalTimeRange.remove(timeRange);
                } else {
                    arrivalTimeRange.add(timeRange);
                }

                filterCriteria.setArrivalTimes(arrivalTimeRange);
                updateFilterResult();
            });
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

    private JScrollPane createFlightListPanel(List<FlightInfo> flightInfoList) {
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
        cardPanel.setPreferredSize(new Dimension(600, 160));

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
        JLabel timeLabel = new JLabel(AppUtils.formatTime(flightInfo.getDepartureDate()) + " → " + AppUtils.formatTime(flightInfo.getReturnDate()));
        timeLabel.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 14));
        JLabel airportLabel = new JLabel(flightInfo.getDepartureAirportInfo().getAirportCode() + " → " + flightInfo.getDestinationAirportInfo().getAirportCode());
        airportLabel.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 14));
        airportLabel.setForeground(new Color(100, 100, 100));
        flightInfoPanel.add(timeLabel);
        flightInfoPanel.add(airportLabel);

        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.Y_AXIS));
        pricePanel.setBackground(Color.WHITE);
        JLabel priceLabel = new JLabel(String.valueOf(flightInfo.getBasePrice()));
        priceLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
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
        actionPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        JPanel detailsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        detailsPanel.setBackground(Color.WHITE);
        JButton detailsButton = new JButton("Detail");
        detailsButton.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 12));
        detailsButton.setForeground(new Color(0, 123, 255));
        detailsButton.setContentAreaFilled(false);
        detailsButton.setBorder(null);
        detailsPanel.add(detailsButton);

        JPanel selectPanel = createChoosePanel(flightInfo);

        actionPanel.add(detailsPanel, BorderLayout.WEST);
        actionPanel.add(selectPanel, BorderLayout.EAST);

        cardPanel.add(mainInfoPanel, BorderLayout.CENTER);
        cardPanel.add(actionPanel, BorderLayout.SOUTH);

        return cardPanel;
    }

    private JPanel createChoosePanel(FlightInfo flightInfo) {
        JPanel selectPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        selectPanel.setBackground(Color.WHITE);
        JButton chooseButton = createChooseButton();
        chooseButton.addActionListener(e -> {
            bookingInfo.setFlightId(flightInfo.getFlightId());
            bookingInfo.setDepartureDate(flightInfo.getDepartureDate());
            bookingInfo.setFlightId(flightInfo.getFlightId());
            bookingInfo.setFlightInfo(flightInfo);
            ContactFormPanel contactFormPanel = new ContactFormPanel(mainPanel, cardLayout, bookingInfo,
                    flightService, bookingService, nationService);
            mainPanel.add(contactFormPanel, Constants.CONTACT_FORM);
            cardLayout.show(mainPanel, Constants.CONTACT_FORM);

        });
        selectPanel.add(chooseButton);
        return selectPanel;
    }

    private JButton createChooseButton() {
        JButton button = new JButton("Choose") {
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

        button.setPreferredSize(new Dimension(100, 30));
        button.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 12));
        button.setBackground(new Color(0, 123, 255));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return button;
    }

    private void updateFilterResult() {
        try {
            logger.debug("Filter criteria: {}", filterCriteria);
            List<FlightInfo> updatedFlightList = flightService.filterFlights(filterCriteria);
            logger.debug("Flight List: {}", updatedFlightList);

            removeAll();
            setLayout(new BorderLayout());
            add(createFilterSidebar(filterCriteria), BorderLayout.WEST);
            JButton backButton = new JButton("Back");
            backButton.addActionListener(e -> cardLayout.show(mainPanel, Constants.FLIGHT_SEARCHER_SCREEN));
            add(backButton, BorderLayout.NORTH);
            add(createFlightListPanel(updatedFlightList), BorderLayout.CENTER);

            revalidate();
            repaint();
        } catch (Exception e) {
            logger.error("Filter flight failed, error: {}", e.getMessage());
            AppUtils.showErrorDialog(e.getMessage());
        }
    }

    private void setBaseFilterCriteria() {
        this.filterCriteria = new FilterCriteria();
        this.filterCriteria.setDepartureAirportId(bookingInfo.getDepartureAirportId());
        this.filterCriteria.setDestinationAirportId(bookingInfo.getDestinationAirportId());
        this.filterCriteria.setDepartureDate(bookingInfo.getDepartureDate());
    }

    private JPanel createAirlineCheckboxWithIcon(JCheckBox airlineCheckbox, Airline airline) {
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
            default:
                break;
        }

        ImageIcon icon = resizeIcon(iconPath);

        JPanel airlinePanel = new JPanel();
        airlinePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        airlinePanel.setBackground(Color.WHITE);

        if (icon != null) {
            JLabel airlineIconLabel = new JLabel(icon);
            airlinePanel.add(airlineCheckbox);
            airlinePanel.add(airlineIconLabel);
        } else {
            airlinePanel.add(airlineCheckbox);
        }

        JLabel airlineNameLabel = new JLabel(airline.getName());
        airlineNameLabel.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 12));
        airlineNameLabel.setForeground(Color.BLACK);
        airlinePanel.add(airlineNameLabel);

        return airlinePanel;
    }

    private ImageIcon resizeIcon(String resourcePath) {
        try {
            ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(resourcePath)));
            return new ImageIcon(icon.getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH));
        } catch (Exception e) {
            logger.error("Failed to load image: {}", resourcePath);
            return null;
        }
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
        checkBox.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

}
