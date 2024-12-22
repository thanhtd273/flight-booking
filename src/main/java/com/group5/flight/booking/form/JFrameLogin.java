package com.group5.flight.booking.form;

import com.group5.flight.booking.core.AppUtils;
import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dto.*;
import com.group5.flight.booking.service.AirportService;
import com.group5.flight.booking.service.AuthService;
import com.group5.flight.booking.service.FlightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

import com.toedter.calendar.JDateChooser;
import org.springframework.util.ObjectUtils;

public class JFrameLogin extends JFrame {

    private static final Logger logger = LoggerFactory.getLogger(JFrameLogin.class);

    private final AuthService authService;

    private final AirportService airportService;

    private final FlightService flightService;

    private final CardLayout cardLayout;

    private final JPanel mainPanel;

    private static final String FONT_NAME = "sanserif";

    private static final String SIGNUP_SCREEN = "SignUp";

    private static final String LOGIN_SCREEN = "Login";

    private static final String FLIGHT_DETAIL_SCREEN = "FlightDetail";

    private static final String FLIGHT_SEARCHER_SCREEN = "FlightSearcher";

    private static final String FLIGHT_LIST_SCREEN = "FlightList";

    public JFrameLogin(AuthService authService, AirportService airportService, FlightService flightService) throws LogicException {
        this.authService = authService;
        this.airportService = airportService;
        this.flightService = flightService;

        // Set the frame properties
        setTitle("Login and Sign Up UI");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);

        // Initialize CardLayout and main panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add the sign-up panel, login panel, and flight searcher panel
        mainPanel.add(createSignUpPanel(), SIGNUP_SCREEN);
        mainPanel.add(createLoginPanel(), LOGIN_SCREEN);
        mainPanel.add(createFlightSearcherPanel(), FLIGHT_SEARCHER_SCREEN);
        mainPanel.add(createFlightListPanel(), FLIGHT_LIST_SCREEN);

        // Add the main panel to the frame
        add(mainPanel);

        // Show the sign-up panel initially
        cardLayout.show(mainPanel, FLIGHT_LIST_SCREEN);
    }

    private JPanel createLoginPanel() throws LogicException {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);

        // Left panel
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(34, 177, 76));
        leftPanel.setBounds(0, 0, 500, 600);
        leftPanel.setLayout(null);

        JLabel lblHello = new JLabel("Hello, Friend!");
        lblHello.setFont(new Font(FONT_NAME, Font.BOLD, 28));
        lblHello.setForeground(Color.WHITE);
        lblHello.setBounds(150, 150, 300, 50);
        leftPanel.add(lblHello);

        JLabel lblDetails = new JLabel("Enter your personal details");
        lblDetails.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
        lblDetails.setForeground(Color.WHITE);
        lblDetails.setBounds(130, 200, 300, 20);
        leftPanel.add(lblDetails);

        JLabel lblJourney = new JLabel("and start journey with us");
        lblJourney.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
        lblJourney.setForeground(Color.WHITE);
        lblJourney.setBounds(130, 220, 300, 20);
        leftPanel.add(lblJourney);

        JButton btnSignUp = new JButton("SIGN UP");
        btnSignUp.setFont(new Font(FONT_NAME, Font.BOLD, 16));
        btnSignUp.setForeground(new Color(34, 177, 76));
        btnSignUp.setBackground(Color.WHITE);
        btnSignUp.setBounds(180, 300, 140, 40);
        btnSignUp.setFocusPainted(false);
        btnSignUp.setBorder(BorderFactory.createLineBorder(new Color(34, 177, 76), 2));
        leftPanel.add(btnSignUp);

        btnSignUp.addActionListener(e -> cardLayout.show(mainPanel, SIGNUP_SCREEN));

        // Right panel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBounds(500, 0, 500, 600);

        JLabel lblSignIn = new JLabel("Sign In");
        lblSignIn.setFont(new Font(FONT_NAME, Font.BOLD, 28));
        lblSignIn.setForeground(new Color(34, 177, 76));
        lblSignIn.setBounds(200, 50, 150, 50);
        rightPanel.add(lblSignIn);

        JTextField txtEmail = new JTextField();
        txtEmail.setBorder(BorderFactory.createTitledBorder("Email"));
        txtEmail.setBounds(150, 150, 200, 50);
        rightPanel.add(txtEmail);

        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setBorder(BorderFactory.createTitledBorder("Password"));
        txtPassword.setBounds(150, 220, 200, 50);
        rightPanel.add(txtPassword);

        JLabel lblForgotPassword = new JLabel("Forgot your password ?");
        lblForgotPassword.setFont(new Font(FONT_NAME, Font.PLAIN, 12));
        lblForgotPassword.setForeground(new Color(34, 177, 76));
        lblForgotPassword.setBounds(190, 280, 150, 20);
        rightPanel.add(lblForgotPassword);

        JButton btnSignIn = new JButton("SIGN IN");
        btnSignIn.setFont(new Font(FONT_NAME, Font.BOLD, 16));
        btnSignIn.setForeground(Color.WHITE);
        btnSignIn.setBackground(new Color(34, 177, 76));
        btnSignIn.setBounds(180, 320, 140, 40);
        btnSignIn.setFocusPainted(false);
        btnSignIn.setBorderPainted(false);
        rightPanel.add(btnSignIn);

//        LoginSessionInfo loginSessionInfo = authService.login(new Credential("dinhthanha12703@gmail.com", "123456"));
//        logger.debug("Login Info: {}", loginSessionInfo);
        btnSignIn.addActionListener(e -> {
            String email = txtEmail.getText();
            String password = new String(txtPassword.getPassword());

            // Simple validation for demonstration purposes
            if (email.equals("user@example.com") && password.equals("password")) {
                cardLayout.show(mainPanel, FLIGHT_SEARCHER_SCREEN);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid email or password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        loginPanel.add(leftPanel);
        loginPanel.add(rightPanel);

        return loginPanel;
    }

    private JPanel createSignUpPanel() {
        JPanel signUpPanel = new JPanel();
        signUpPanel.setLayout(null);

        // Left panel
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(34, 177, 76));
        leftPanel.setBounds(0, 0, 500, 600);
        leftPanel.setLayout(null);

        JLabel lblWelcome = new JLabel("Welcome Back!");
        lblWelcome.setFont(new Font(FONT_NAME, Font.BOLD, 28));
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setBounds(150, 150, 300, 50);
        leftPanel.add(lblWelcome);

        JLabel lblConnect = new JLabel("To keep connected with us please");
        lblConnect.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
        lblConnect.setForeground(Color.WHITE);
        lblConnect.setBounds(130, 200, 300, 20);
        leftPanel.add(lblConnect);

        JLabel lblLoginInfo = new JLabel("login with your personal info");
        lblLoginInfo.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
        lblLoginInfo.setForeground(Color.WHITE);
        lblLoginInfo.setBounds(130, 220, 300, 20);
        leftPanel.add(lblLoginInfo);

        JButton btnSignIn = new JButton("SIGN IN");
        btnSignIn.setFont(new Font(FONT_NAME, Font.BOLD, 16));
        btnSignIn.setForeground(new Color(34, 177, 76));
        btnSignIn.setBackground(Color.WHITE);
        btnSignIn.setBounds(180, 300, 140, 40);
        btnSignIn.setFocusPainted(false);
        btnSignIn.setBorder(BorderFactory.createLineBorder(new Color(34, 177, 76), 2));
        leftPanel.add(btnSignIn);

        btnSignIn.addActionListener(e -> cardLayout.show(mainPanel, LOGIN_SCREEN));

        // Right panel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBounds(500, 0, 500, 600);

        JLabel lblCreateAccount = new JLabel("Create Account");
        lblCreateAccount.setFont(new Font(FONT_NAME, Font.BOLD, 28));
        lblCreateAccount.setForeground(new Color(34, 177, 76));
        lblCreateAccount.setBounds(170, 50, 200, 50);
        rightPanel.add(lblCreateAccount);

        JTextField txtName = new JTextField();
        txtName.setBorder(BorderFactory.createTitledBorder("Name"));
        txtName.setBounds(150, 150, 200, 50);
        rightPanel.add(txtName);

        JTextField txtEmail = new JTextField();
        txtEmail.setBorder(BorderFactory.createTitledBorder("Email"));
        txtEmail.setBounds(150, 220, 200, 50);
        rightPanel.add(txtEmail);

        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setBorder(BorderFactory.createTitledBorder("Password"));
        txtPassword.setBounds(150, 290, 200, 50);
        rightPanel.add(txtPassword);

        JButton btnSignUp = new JButton("SIGN UP");
        btnSignUp.setFont(new Font(FONT_NAME, Font.BOLD, 16));
        btnSignUp.setForeground(Color.WHITE);
        btnSignUp.setBackground(new Color(34, 177, 76));
        btnSignUp.setBounds(180, 370, 140, 40);
        btnSignUp.setFocusPainted(false);
        btnSignUp.setBorderPainted(false);
        rightPanel.add(btnSignUp);

        signUpPanel.add(leftPanel);
        signUpPanel.add(rightPanel);

        return signUpPanel;
    }

    private JPanel createFlightSearcherPanel() {
        final Long[] departureId = new Long[1];
        final Long[] destinationId = new Long[1];
        final Date[] departureDate = new Date[1];
        final List<FlightInfo> flightInfoList = new ArrayList<>();

        JTable flightTable = new JTable();

        JPanel flightSearcherPanel = new JPanel();
        flightSearcherPanel.setLayout(null);

        JLabel lblTitle = new JLabel("Search Flights");
        lblTitle.setFont(new Font(FONT_NAME, Font.BOLD, 28));
        lblTitle.setForeground(new Color(34, 177, 76));
        lblTitle.setBounds(400, 20, 200, 40);
        flightSearcherPanel.add(lblTitle);

        List<AirportInfo> airportInfoList = airportService.getAllAirportInfos();

        Map<String, Long> airportLocations = generateMenuData(airportInfoList);
        String[] locations = AppUtils.list2Array(airportLocations.keySet().stream().toList());

        JComboBox<String> cbDeparture = new JComboBox<>(locations);
        cbDeparture.setBorder(BorderFactory.createTitledBorder("Departure Location"));
        cbDeparture.setBounds(300, 100, 400, 50);
        cbDeparture.addActionListener(e -> {
            String location = (String) cbDeparture.getSelectedItem();
            if (!ObjectUtils.isEmpty(location)) {
                departureId[0] = airportLocations.get(location);
            }
            logger.debug("Chose departure location: {}", location);
        });
        flightSearcherPanel.add(cbDeparture);

        JComboBox<String> cbDestination = new JComboBox<>(locations);
        cbDestination.setBorder(BorderFactory.createTitledBorder("Destination Location"));
        cbDestination.setBounds(300, 170, 400, 50);
        cbDestination.addActionListener(e -> {
            String location = (String) cbDestination.getSelectedItem();
            if (!ObjectUtils.isEmpty(location)) {
                destinationId[0] = airportLocations.get(location);
            }
            logger.debug("Chose destination location: {}", location);
        });
        flightSearcherPanel.add(cbDestination);

        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setBorder(BorderFactory.createTitledBorder("Departure Date"));
        dateChooser.setBounds(300, 240, 400, 50);
        dateChooser.addPropertyChangeListener("date", evt -> {
            departureDate[0] = (Date) evt.getNewValue();
            logger.debug("Selected Departure Date: {}", departureDate[0]);
        });
        flightSearcherPanel.add(dateChooser);

        JButton btnSearch = new JButton("Search");
        btnSearch.setFont(new Font(FONT_NAME, Font.BOLD, 16));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setBackground(new Color(34, 177, 76));
        btnSearch.setBounds(450, 310, 140, 40);
        btnSearch.setFocusPainted(false);
        btnSearch.addActionListener(e -> {
            logger.debug("Find flight by departureId = {}, destinationId =  {}, departureDate = {}",
                    departureId[0], destinationId[0], departureDate[0]);
            if (ObjectUtils.isEmpty(departureId[0]) || ObjectUtils.isEmpty(destinationId[0]) || ObjectUtils.isEmpty(departureDate[0])) {
                JOptionPane.showMessageDialog(null, "Please choose full of information", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    flightInfoList.addAll(flightService.findFlight(departureId[0], destinationId[0], departureDate[0])) ;
                    logger.debug("Flight list: {}", flightInfoList);
                    updateFlightTable(flightTable, flightInfoList);

                } catch (LogicException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        flightSearcherPanel.add(btnSearch);


        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Flight Code", "Departure", "Destination", "Departure Date", "Return Date", "Price"});
        flightTable.setModel(model);

        JScrollPane scrollPane = new JScrollPane(flightTable);
        scrollPane.setBounds(50, 370, 900, 150);
        flightTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int row = flightTable.rowAtPoint(evt.getPoint());
                if (row >= 0) {
                    FlightInfo flightInfo = flightInfoList.get(row);
                    JPanel flightDetailPanel = createFlightDetailPanel(flightInfo);
                    mainPanel.add(flightDetailPanel, FLIGHT_DETAIL_SCREEN);
                    cardLayout.show(mainPanel, FLIGHT_DETAIL_SCREEN);
                }
            }
        });
        flightSearcherPanel.add(scrollPane);

        return flightSearcherPanel;
    }

    private JPanel createFlightDetailPanel(FlightInfo flightInfo) {
        JPanel flightDetailPanel = new JPanel();
        flightDetailPanel.setLayout(null);

        JLabel lblTitle = new JLabel("Flight Details");
        lblTitle.setFont(new Font(FONT_NAME, Font.BOLD, 28));
        lblTitle.setForeground(new Color(34, 177, 76));
        lblTitle.setBounds(400, 20, 200, 40);
        flightDetailPanel.add(lblTitle);

        JLabel lblFlightCode = new JLabel("Flight Code: " + flightInfo.getFlightId());
        lblFlightCode.setBounds(100, 100, 300, 30);
        flightDetailPanel.add(lblFlightCode);

        JLabel lblDeparture = new JLabel("Departure: " + flightInfo.getFromAirport().getName());
        lblDeparture.setBounds(100, 150, 300, 30);
        flightDetailPanel.add(lblDeparture);

        JLabel lblDestination = new JLabel("Destination: " + flightInfo.getFromAirport().getName());
        lblDestination.setBounds(100, 200, 300, 30);
        flightDetailPanel.add(lblDestination);

        JLabel lblDepartureDate = new JLabel("Departure Date: " + flightInfo.getDepatureDate());
        lblDepartureDate.setBounds(100, 250, 300, 30);
        flightDetailPanel.add(lblDepartureDate);

        JLabel lblReturnDate = new JLabel("Return Date: " + flightInfo.getReturnDate());
        lblReturnDate.setBounds(100, 300, 300, 30);
        flightDetailPanel.add(lblReturnDate);

        JLabel lblPrice = new JLabel("Price: " + flightInfo.getBasePrice());
        lblPrice.setBounds(100, 350, 300, 30);
        flightDetailPanel.add(lblPrice);

        JButton btnBack = new JButton("Back");
        btnBack.setBounds(450, 450, 100, 30);
        btnBack.addActionListener(e -> cardLayout.show(mainPanel, FLIGHT_SEARCHER_SCREEN));
        flightDetailPanel.add(btnBack);

        return flightDetailPanel;
    }

    private JPanel createFlightListPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Left filter panel
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
        filterPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        filterPanel.setBackground(Color.WHITE);

        JLabel filterTitle = new JLabel("Bộ lọc:");
        filterTitle.setFont(new Font("Arial", Font.BOLD, 16));
        filterPanel.add(filterTitle);

        filterPanel.add(createFilterSection("Số điểm dừng", new String[]{"Bay thẳng", "1 điểm dừng", "2+ transits"}));
        filterPanel.add(createFilterSection("Hãng hàng không", new String[]{"Malaysia Airlines", "Thai VietJet Air", "VietJet Air", "Vietnam Airlines", "Vietravel Airlines"}));
        filterPanel.add(createFilterSection("Thời gian bay", new String[]{"00:00 - 06:00", "06:00 - 12:00", "12:00 - 18:00", "18:00 - 24:00"}));

        panel.add(filterPanel, BorderLayout.WEST);

        // Right flight list panel
        JPanel flightListPanel = new JPanel();
        flightListPanel.setLayout(new BoxLayout(flightListPanel, BoxLayout.Y_AXIS));
        flightListPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel bestFlightTitle = new JLabel("Best flight from your search!");
        bestFlightTitle.setFont(new Font("Arial", Font.BOLD, 16));
        flightListPanel.add(bestFlightTitle);

        flightListPanel.add(createFlightCard("Vietravel Airlines", "07:20", "08:40", "1h 20m", 2415000, 2441000));

        JLabel allFlightsTitle = new JLabel("Tất cả các chuyến bay");
        allFlightsTitle.setFont(new Font("Arial", Font.BOLD, 16));
        flightListPanel.add(allFlightsTitle);

        flightListPanel.add(createFlightCard("VietJet Air", "12:35", "13:50", "1h 15m", 2545831, 2441000));
        flightListPanel.add(createFlightCard("VietJet Air", "14:35", "15:50", "1h 15m", 2545831, 2441000));

        panel.add(new JScrollPane(flightListPanel), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createFilterSection(String title, String[] options) {
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));
        sectionPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        sectionPanel.setBackground(Color.WHITE);

        JLabel sectionTitle = new JLabel(title);
        sectionTitle.setFont(new Font("Arial", Font.BOLD, 14));
        sectionPanel.add(sectionTitle);

        for (String option : options) {
            JCheckBox checkBox = new JCheckBox(option);
            checkBox.setFont(new Font("Arial", Font.PLAIN, 12));
            sectionPanel.add(checkBox);
        }

        return sectionPanel;
    }

    private JPanel createFlightCard(String airline, String departure, String arrival, String duration, int price, int originalPrice) {
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setPreferredSize(new Dimension(760, 120));

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.WHITE);
        JLabel headerLabel = new JLabel("Giá tốt từ hãng bay");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        headerLabel.setForeground(Color.ORANGE);
        headerPanel.add(headerLabel);
        cardPanel.add(headerPanel, BorderLayout.NORTH);

        // Content
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        // Left side
        JPanel leftPanel = new JPanel(new GridLayout(3, 1));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel airlineLabel = new JLabel(airline);
        airlineLabel.setFont(new Font("Arial", Font.BOLD, 16));
        leftPanel.add(airlineLabel);

        JLabel timeLabel = new JLabel(departure + " - " + arrival + " (" + duration + ")");
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        leftPanel.add(timeLabel);

        JLabel routeLabel = new JLabel("SGN ➔ DAD | Bay thẳng");
        routeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        leftPanel.add(routeLabel);

        contentPanel.add(leftPanel, BorderLayout.WEST);

        // Center labels (badges and details)
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        centerPanel.setBackground(Color.WHITE);

        JLabel badge1 = new JLabel(new ImageIcon("badge1.png")); // Replace with actual badge image path
        JLabel badge2 = new JLabel("Year End Sale");
        badge2.setFont(new Font("Arial", Font.BOLD, 12));
        badge2.setForeground(Color.RED);
        JLabel badge3 = new JLabel("Coupon: BAYTETYES");
        badge3.setFont(new Font("Arial", Font.PLAIN, 12));
        badge3.setForeground(Color.BLUE);

        centerPanel.add(badge1);
        centerPanel.add(badge2);
        centerPanel.add(badge3);

        contentPanel.add(centerPanel, BorderLayout.CENTER);

        // Right side
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel priceLabel = new JLabel(price + " VND");
        priceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        priceLabel.setForeground(Color.RED);
        priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel originalPriceLabel = new JLabel("" + originalPrice + " VND");
        originalPriceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        originalPriceLabel.setForeground(Color.GRAY);
        originalPriceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        originalPriceLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JButton selectButton = new JButton("Chọn");
        selectButton.setBackground(new Color(0, 102, 204));
        selectButton.setForeground(Color.WHITE);
        selectButton.setFocusPainted(false);
        selectButton.setFont(new Font("Arial", Font.BOLD, 14));

        rightPanel.add(originalPriceLabel, BorderLayout.NORTH);
        rightPanel.add(priceLabel, BorderLayout.CENTER);
        rightPanel.add(selectButton, BorderLayout.SOUTH);

        contentPanel.add(rightPanel, BorderLayout.EAST);

        cardPanel.add(contentPanel, BorderLayout.CENTER);

        return cardPanel;
    }

    private void updateFlightTable(JTable flightTable, List<FlightInfo> flightInfoList) {
        DefaultTableModel model = (DefaultTableModel) flightTable.getModel();
        model.setRowCount(0); // Clear existing data
        List<FlightDisplayInfo> flightDisplayInfos = flightService.getFlightsDisplay(flightInfoList);
        for (FlightDisplayInfo flightDisplayInfo : flightDisplayInfos) {
            model.addRow(new Object[]{
                    flightDisplayInfo.getFlightCode(),
                    flightDisplayInfo.getDeparture(),
                    flightDisplayInfo.getDestination(),
                    flightDisplayInfo.getDepartureDate(),
                    flightDisplayInfo.getReturnDate(),
                    flightDisplayInfo.getPrice()
            });
        }
    }

    private Map<String, Long> generateMenuData(List<AirportInfo> airportInfos) {
        Map<String, Long> map = new HashMap<>();
        for (AirportInfo airportInfo: airportInfos) {
            NationInfo nationInfo = airportInfo.getCity().getNation();
            String key = String.format("%s, %s, %s", airportInfo.getName(), airportInfo.getCity().getName(), nationInfo.getName());
            map.put(key, airportInfo.getAirportId());
        }
        return map;
    }
}
