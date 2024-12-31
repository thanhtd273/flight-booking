package com.group1.flight.booking.form;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

import com.group1.flight.booking.core.AppUtils;
import com.group1.flight.booking.core.Constants;
import com.group1.flight.booking.core.exception.LogicException;
import com.group1.flight.booking.dto.AirportInfo;
import com.group1.flight.booking.dto.BookingInfo;
import com.group1.flight.booking.dto.FlightInfo;
import com.group1.flight.booking.dto.NationInfo;
import com.group1.flight.booking.service.*;
import com.group1.flight.booking.form.component.FbButton;
import com.toedter.calendar.JDateChooser;
import net.miginfocom.swing.MigLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import static com.group1.flight.booking.core.Constants.FLIGHT_LIST_SCREEN;

public class FlightSearchPanel extends JPanel {

    private static final Logger logger = LoggerFactory.getLogger(FlightSearchPanel.class);

    private static final String STYLE_CONSTRAINT = "width 400!, height 50!, wrap";

    private final JPanel mainPanel;

    private final CardLayout cardLayout;

    private final BookingInfo bookingInfo;

    private final AirportService airportService;

    private final FlightService flightService;

    private final AirlineService airlineService;

    private final BookingService bookingService;

    private final NationService nationService;

    final boolean[] needsReset = {false};

    public FlightSearchPanel(JPanel mainPanel, CardLayout cardLayout, AirportService airportService,
                             FlightService flightService, AirlineService airlineService, BookingService bookingService,
                             NationService nationService) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.airportService = airportService;
        this.flightService = flightService;
        this.airlineService = airlineService;
        this.bookingService = bookingService;
        this.nationService = nationService;

        this.bookingInfo = new BookingInfo();
        this.bookingInfo.setNumOfPassengers(1);

        initComponents();
        setOpaque(false);
    }

    private JPanel createAirlineCheckbox(String airlineName, String price, String iconPath) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(new Color(238, 238, 238));

        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(iconPath)));
        Image img = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel airlineIcon = new JLabel(new ImageIcon(img));
        airlineIcon.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        JPanel verticalPanel = new JPanel();
        verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));
        verticalPanel.setBackground(new Color(238, 238, 238));

        JLabel airlineNameLabel = new JLabel(airlineName);
        airlineNameLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 14));

        JLabel priceLabel = new JLabel(price);
        priceLabel.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 12));
        priceLabel.setForeground(new Color(100, 100, 100));

        verticalPanel.add(airlineNameLabel);
        verticalPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        verticalPanel.add(priceLabel);

        panel.add(airlineIcon);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(verticalPanel);

        return panel;
    }

    private JDateChooser createCustomDateChooser() {
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setBorder(BorderFactory.createTitledBorder("Departure Date"));
        dateChooser.setBounds(300, 440, 400, 80);

        dateChooser.addPropertyChangeListener("date", evt -> bookingInfo.setDepartureDate((Date) evt.getNewValue()));

        return dateChooser;
    }

    private boolean validateAndSetReset(JTextField lblPassengerCount, int[] passengerCount, boolean[] needsReset) {
        try {
            int value = Integer.parseInt(lblPassengerCount.getText());
            if (value < 1) {
                passengerCount[0] = 1;
                needsReset[0] = true;
            } else if (value > 9) {
                passengerCount[0] = 9;
                needsReset[0] = true;
            } else {
                passengerCount[0] = value;
                if (needsReset[0]) {
                    needsReset[0] = false; // Đã reset xong, không cần reset nữa
                    return true; // Chỉ reset, không tăng/giảm
                }
            }
        } catch (NumberFormatException ex) {
            passengerCount[0] = 1; // Giá trị mặc định nếu nhập sai
            needsReset[0] = true;
        }
        lblPassengerCount.setText(String.valueOf(passengerCount[0])); // Cập nhật giao diện
        return needsReset[0]; // Trả về true nếu vừa reset
    }

    private void initComponents() {
        JComboBox<String> cbDestination;
        JComboBox<String> cbDeparture;
        final List<FlightInfo> flightInfoList = new ArrayList<>();

        setLayout(new MigLayout("wrap, fill", "[center]", "[top][grow]"));

        JPanel flightSearcherPanel = new JPanel(new MigLayout("wrap 2", "[grow,fill][grow,fill]", "[]10[]10[]"));
        flightSearcherPanel.setOpaque(false);

        JLabel lblTitle = new JLabel("Search Flights");
        lblTitle.setFont(new Font("sanserif", Font.BOLD, 30));
        lblTitle.setForeground(new Color(7, 164, 121));
        add(lblTitle, "wrap, align center");

        List<AirportInfo> airportInfoList = airportService.getAllAirportInfos();

        Map<String, Long> airportLocations = generateMenuData(airportInfoList);
        String[] locations = AppUtils.list2Array(airportLocations.keySet().stream().toList());

        cbDeparture = new JComboBox<>(locations);
        cbDeparture.setBorder(BorderFactory.createTitledBorder("Departure Location"));
        cbDeparture.setBounds(300, 100, 400, 40);
        cbDeparture.addActionListener(e -> {
            String location = (String) cbDeparture.getSelectedItem();
            if (!ObjectUtils.isEmpty(location)) {
                bookingInfo.setDepartureAirportId(airportLocations.get(location));
            }
        });
        flightSearcherPanel.add(cbDeparture, STYLE_CONSTRAINT);

        cbDestination = new JComboBox<>(locations);
        cbDestination.setBorder(BorderFactory.createTitledBorder("Destination Location"));
        cbDestination.setBounds(300, 170, 400, 50);
        cbDestination.addActionListener(e -> {
            String location = (String) cbDestination.getSelectedItem();
            if (!ObjectUtils.isEmpty(location)) {
                bookingInfo.setDestinationAirportId(airportLocations.get(location));
            }
            logger.debug("Chose destination location: {}", location);
        });
        flightSearcherPanel.add(cbDestination, STYLE_CONSTRAINT);

        JDateChooser dateChooser = createCustomDateChooser();
        flightSearcherPanel.add(dateChooser, STYLE_CONSTRAINT);

        add(flightSearcherPanel);


        JPanel bottomPanel = new JPanel(new MigLayout("align center"));
        bottomPanel.setOpaque(false);
        FbButton btnSearch = new FbButton();
        btnSearch.setText("Search");
        btnSearch.setBackground(new Color(7, 164, 121));
        btnSearch.setForeground(new Color(255, 255, 255));
        btnSearch.setPreferredSize(new Dimension(200, 40));
        btnSearch.addActionListener(e -> {
            logger.debug("Find flight by departureId = {}, destinationId =  {}, departureDate = {}",
                    bookingInfo.getDepartureAirportId(), bookingInfo.getDestinationAirportId(), bookingInfo.getDepartureDate());
            if (ObjectUtils.isEmpty(bookingInfo.getDepartureAirportId()) ||
                    ObjectUtils.isEmpty(bookingInfo.getDestinationAirportId()) ||
                    ObjectUtils.isEmpty(bookingInfo.getDepartureDate())) {
                JOptionPane.showMessageDialog(null, "Please choose full of information", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    logger.debug("Travel info: {}", bookingInfo);
                    flightInfoList.addAll(flightService.findFlight(bookingInfo.getDepartureAirportId(), bookingInfo.getDestinationAirportId(), bookingInfo.getDepartureDate())) ;
                    logger.debug("Flight list: {}", flightInfoList);
                    FlightListPanel flightListPanel = new FlightListPanel(mainPanel, cardLayout, bookingInfo,
                            flightInfoList, airlineService, flightService, bookingService, nationService);
                    mainPanel.add(flightListPanel, FLIGHT_LIST_SCREEN);
                    cardLayout.show(mainPanel, FLIGHT_LIST_SCREEN);
                } catch (LogicException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        bottomPanel.add(btnSearch);

        JPanel passengerPanel = new JPanel(new GridBagLayout());
        passengerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        final int[] passengerCount = {1};
        JTextField lblPassengerCount = new JTextField(String.valueOf(passengerCount[0]), SwingConstants.CENTER);
        lblPassengerCount.setFont(new Font(Constants.FB_FONT, Font.BOLD, 14));
        lblPassengerCount.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        lblPassengerCount.setPreferredSize(new Dimension(50, 30));
        lblPassengerCount.setHorizontalAlignment(JTextField.CENTER);
        lblPassengerCount.addActionListener(e -> validateAndSetReset(lblPassengerCount, passengerCount, needsReset));
        lblPassengerCount.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                validateAndSetReset(lblPassengerCount, passengerCount, needsReset);
            }
        });

        JButton btnDecrease = new JButton("-");
        btnDecrease.setFont(new Font(Constants.FB_FONT, Font.BOLD, 14));
        btnDecrease.addActionListener(e -> {
            if (validateAndSetReset(lblPassengerCount, passengerCount, needsReset)) {
                // Lần đầu reset, không giảm giá trị
                return;
            }
            if (passengerCount[0] > 1) {
                passengerCount[0]--;
                lblPassengerCount.setText(String.valueOf(passengerCount[0]));
            }
        });

        JButton btnIncrease = new JButton("+");
        btnIncrease.setFont(new Font(Constants.FB_FONT, Font.BOLD, 14));
        btnIncrease.addActionListener(e -> {
            if (validateAndSetReset(lblPassengerCount, passengerCount, needsReset)) {
                // Lần đầu reset, không tăng giá trị
                return;
            }
            if (passengerCount[0] < 9) {
                passengerCount[0]++;
                lblPassengerCount.setText(String.valueOf(passengerCount[0]));
            }
        });

        passengerPanel.setBorder(BorderFactory.createTitledBorder("No. of Passengers"));

        JPanel airline1Panel = createAirlineCheckbox("Adult", "Age 12 and over", "/human.png");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 0, 10); // Thêm khoảng cách ben phải
        passengerPanel.add(airline1Panel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        passengerPanel.add(btnDecrease, gbc);

        gbc.gridx = 2;
        passengerPanel.add(lblPassengerCount, gbc);

        gbc.gridx = 3;
        passengerPanel.add(btnIncrease, gbc);

        flightSearcherPanel.add(passengerPanel, BorderLayout.CENTER);
        add(bottomPanel, "wrap, grow");
        add(bottomPanel, "dock south");
    }

    private Map<String, Long> generateMenuData(List<AirportInfo> airportInfos) {
        Map<String, Long> map = new HashMap<>();
        for (AirportInfo airportInfo: airportInfos) {
            NationInfo nationInfo = airportInfo.getCityInfo().getNationInfo();
            String key = String.format("%s, %s, %s", airportInfo.getName(), airportInfo.getCityInfo().getName(), nationInfo.getName());
            map.put(key, airportInfo.getAirportId());
        }
        return map;
    }
}
