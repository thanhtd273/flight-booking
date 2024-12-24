package com.group5.flight.booking.form;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

import com.group5.flight.booking.core.AppUtils;
import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dto.AirportInfo;
import com.group5.flight.booking.dto.FlightInfo;
import com.group5.flight.booking.dto.NationInfo;
import com.group5.flight.booking.form.component.FbButton;
import com.group5.flight.booking.service.AirlineService;
import com.group5.flight.booking.service.AirportService;
import com.group5.flight.booking.service.FlightService;
import com.group5.flight.booking.service.PlaneService;
import com.toedter.calendar.JDateChooser;
import net.miginfocom.swing.MigLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import static com.group5.flight.booking.core.Constants.FLIGHT_LIST_SCREEN;

public class FlightSearchPanel extends JPanel {

    private static final Logger logger = LoggerFactory.getLogger(FlightSearchPanel.class);

    private final JPanel mainPanel;

    private final CardLayout cardLayout;

    private final AirportService airportService;

    private final FlightService flightService;

    private final AirlineService airlineService;

    private final PlaneService planeService;

    public FlightSearchPanel(JPanel mainPanel, CardLayout cardLayout,
                             AirportService airportService, FlightService flightService, AirlineService airlineService,
                             PlaneService planeService) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.airportService = airportService;
        this.flightService = flightService;
        this.airlineService = airlineService;
        this.planeService = planeService;
        initComponents();
        setOpaque(false);
    }

        private JPanel createAirlineCheckbox(String airlineName, String price, String iconPath) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(new Color(238, 238, 238));

        // Tạo và scale icon human
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(iconPath)));
        Image img = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel airlineIcon = new JLabel(new ImageIcon(img));
        airlineIcon.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        // Panel chứa thông tin
        JPanel verticalPanel = new JPanel();
        verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));
        verticalPanel.setBackground(new Color(238, 238, 238));

        // Aduit
        JLabel airlineNameLabel = new JLabel(airlineName);
        airlineNameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        // 12
        JLabel priceLabel = new JLabel(price);
        priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        priceLabel.setForeground(new Color(100, 100, 100));

        // Thêm thành phần vào verticalPanel
        verticalPanel.add(airlineNameLabel);
        verticalPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        verticalPanel.add(priceLabel);

        // Thêm các thành phần vào panel chính
        panel.add(airlineIcon);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(verticalPanel);

        return panel;
    }

    private JDateChooser createCustomDateChooser(String title, int x, int y, int width, int height, Date[] departureDate) {
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setBorder(BorderFactory.createTitledBorder(title));
        dateChooser.setBounds(x, y, width, height);

        // Sự kiện lắng nghe thay đổi giá trị ngày
        dateChooser.addPropertyChangeListener("date", evt -> {
            departureDate[0] = (Date) evt.getNewValue();
            System.out.println("Selected Date: " + departureDate[0]); // In ra giá trị ngày được chọn
        });

        return dateChooser;
    }

    private void initComponents() {
        JComboBox<String> cbDestination;
        JComboBox<String> cbDeparture;
        final Long[] departureId = new Long[1];
        final Long[] destinationId = new Long[1];
        final Date[] departureDate = new Date[1];
        //setBackground(new Color(255, 255, 255));
        final List<FlightInfo> flightInfoList = new ArrayList<>();

        setLayout(new MigLayout("wrap, fill", "[center]", "[top][grow]"));

        // Top section for input fields
        JPanel flightSearcherPanel = new JPanel(new MigLayout("wrap 2", "[grow,fill][grow,fill]", "[]10[]10[]"));
        flightSearcherPanel.setOpaque(false);
        //flightSearcherPanel.setLayout(null);

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
                departureId[0] = airportLocations.get(location);
            }
            logger.debug("Chose departure location: {}", location);
        });
        //flightSearcherPanel.add(cbDeparture);
        // Thêm vào flightSearcherPanel
        flightSearcherPanel.add(cbDeparture, "width 400!, height 50!, wrap");

        cbDestination = new JComboBox<>(locations);
        cbDestination.setBorder(BorderFactory.createTitledBorder("Destination Location"));
        cbDestination.setBounds(300, 170, 400, 50);
        cbDestination.addActionListener(e -> {
            String location = (String) cbDestination.getSelectedItem();
            if (!ObjectUtils.isEmpty(location)) {
                destinationId[0] = airportLocations.get(location);
            }
            logger.debug("Chose destination location: {}", location);
        });
        //flightSearcherPanel.add(cbDestination);
        // Thêm vào flightSearcherPanel
        flightSearcherPanel.add(cbDestination, "width 400!, height 50!, wrap");

        // Tạo JDateChooser với kích thước tùy chỉnh
        JDateChooser dateChooser = createCustomDateChooser("Departure Date", 300, 440, 400, 80, departureDate);

        // Thêm vào flightSearcherPanel
        flightSearcherPanel.add(dateChooser, "width 400!, height 50!, wrap");

        // Thêm flightSearcherPanel vào JFrame
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
                    departureId[0], destinationId[0], departureDate[0]);
            if (ObjectUtils.isEmpty(departureId[0]) || ObjectUtils.isEmpty(destinationId[0]) || ObjectUtils.isEmpty(departureDate[0])) {
                JOptionPane.showMessageDialog(null, "Please choose full of information", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    flightInfoList.addAll(flightService.findFlight(departureId[0], destinationId[0], departureDate[0])) ;
                    logger.debug("Flight list: {}", flightInfoList);
                    FlightListPanel flightListPanel = new FlightListPanel(mainPanel, cardLayout, departureId[0],
                            destinationId[0], departureDate[0], flightInfoList, airlineService, flightService, planeService);
                    mainPanel.add(flightListPanel, FLIGHT_LIST_SCREEN);
                    cardLayout.show(mainPanel, FLIGHT_LIST_SCREEN);
                } catch (LogicException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        bottomPanel.add(btnSearch);

        // Panel cho hành khách
        JPanel passengerPanel = new JPanel(new GridBagLayout());
        passengerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Số lượng hành khách mặc định
        final int[] passengerCount = {1};
        JLabel lblPassengerCount = new JLabel(String.valueOf(passengerCount[0]), SwingConstants.CENTER);
        lblPassengerCount.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblPassengerCount.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        lblPassengerCount.setPreferredSize(new Dimension(50, 30));

        // Nút giảm (-)
        JButton btnDecrease = new JButton("-");
        btnDecrease.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnDecrease.addActionListener(e -> {
            if (passengerCount[0] > 1) {
                passengerCount[0]--;
                lblPassengerCount.setText(String.valueOf(passengerCount[0]));
            }
        });

        // Nút tăng (+)
        JButton btnIncrease = new JButton("+");
        btnIncrease.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnIncrease.addActionListener(e -> {
            if (passengerCount[0] < 9) {
                passengerCount[0]++;
                lblPassengerCount.setText(String.valueOf(passengerCount[0]));
            }
        });

        //passengerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding bên trong
        passengerPanel.setBorder(BorderFactory.createTitledBorder("No. of Passengers"));

        // Gọi phương thức createAirlineCheckbox để tạo airline1Panel
        JPanel airline1Panel = createAirlineCheckbox("Adult", "Age 12 and over", "/Human.png");

        // Thêm airline1Panel vào passengerPanel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 0, 10); // Thêm khoảng cách ben phải
        passengerPanel.add(airline1Panel, gbc);

        // Sắp xếp các thành phần vào passengerPanel
        gbc.gridx = 1;
        gbc.gridy = 0;
        passengerPanel.add(btnDecrease, gbc);

        gbc.gridx = 2;
        passengerPanel.add(lblPassengerCount, gbc);

        gbc.gridx = 3;
        passengerPanel.add(btnIncrease, gbc);

        // Thêm passengerPanel vào flightSearcherPanel
        flightSearcherPanel.add(passengerPanel, BorderLayout.CENTER);
        //bottomPanel.add(passengerPanel);
        add(bottomPanel, "wrap, grow");
        add(bottomPanel, "dock south");
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
