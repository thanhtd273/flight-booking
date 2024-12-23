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

    private void initComponents() {
        JComboBox<String> cbDestination;
        JComboBox<String> cbDeparture;
        final Long[] departureId = new Long[1];
        final Long[] destinationId = new Long[1];
        final Date[] departureDate = new Date[1];
        final List<FlightInfo> flightInfoList = new ArrayList<>();

        setLayout(new MigLayout("wrap, fill", "[center]", "[top][grow]"));

        // Top section for input fields
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
        cbDeparture.setBounds(300, 100, 400, 50);
        cbDeparture.addActionListener(e -> {
            String location = (String) cbDeparture.getSelectedItem();
            if (!ObjectUtils.isEmpty(location)) {
                departureId[0] = airportLocations.get(location);
            }
            logger.debug("Chose departure location: {}", location);
        });
        flightSearcherPanel.add(cbDeparture);

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
        flightSearcherPanel.add(cbDestination);

        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setBorder(BorderFactory.createTitledBorder("Departure Date"));
        dateChooser.setBounds(300, 240, 400, 50);
        dateChooser.addPropertyChangeListener("date", evt -> {
            departureDate[0] = (Date) evt.getNewValue();
            logger.debug("Selected Departure Date: {}", departureDate[0]);
        });
        flightSearcherPanel.add(dateChooser);
        add(flightSearcherPanel, "wrap, grow");

        JPanel bottomPanel = new JPanel(new MigLayout("align center"));
        bottomPanel.setOpaque(false);
        FbButton btnSearch = new FbButton();
        btnSearch.setText("Search");
        btnSearch.setBackground(new Color(7, 164, 121));
        btnSearch.setForeground(new Color(255, 255, 255));
        btnSearch.setPreferredSize(new Dimension(200, 50));
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
