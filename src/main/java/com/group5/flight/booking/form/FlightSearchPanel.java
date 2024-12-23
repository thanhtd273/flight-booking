package com.group5.flight.booking.form;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.group5.flight.booking.core.AppUtils;
import com.group5.flight.booking.core.Constants;
import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dto.AirportInfo;
import com.group5.flight.booking.dto.FlightDisplayInfo;
import com.group5.flight.booking.dto.FlightInfo;
import com.group5.flight.booking.dto.NationInfo;
import com.group5.flight.booking.form.component.FbButton;
import com.group5.flight.booking.service.AirportService;
import com.group5.flight.booking.service.FlightService;
import com.toedter.calendar.JDateChooser;
import net.miginfocom.swing.MigLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import static com.group5.flight.booking.core.Constants.FLIGHT_DETAIL_SCREEN;

public class FlightSearchPanel extends JPanel {

    private static final Logger logger = LoggerFactory.getLogger(FlightSearchPanel.class);

    private final JPanel mainPanel;

    private final CardLayout cardLayout;

    private JTable flightTable;

    private final AirportService airportService;

    private final FlightService flightService;

    public FlightSearchPanel(JPanel mainPanel, CardLayout cardLayout,
                             AirportService airportService, FlightService flightService) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.airportService = airportService;
        this.flightService = flightService;
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

        FbButton btnSearch = new FbButton();
        btnSearch.setText("Search");
        btnSearch.setBackground(new Color(7, 164, 121));
        btnSearch.setForeground(new Color(255, 255, 255));
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
        flightSearcherPanel.add(btnSearch, "span, align center, w 50%, h 40");

        add(flightSearcherPanel, "growx");

        // Bottom section for table
        JPanel tablePanel = new JPanel(new MigLayout("fill"));
        tablePanel.setOpaque(false);

        flightTable = new JTable();
        flightTable.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Flight Code", "Departure", "Destination", "Departure Date", "Return Date", "Price"}
        ));
        flightTable.setFillsViewportHeight(true);
        flightTable.setRowHeight(30);
        flightTable.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 14));
        flightTable.getTableHeader().setFont(new Font(Constants.FB_FONT, Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(flightTable);
        flightTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int row = flightTable.rowAtPoint(evt.getPoint());
                if (row >= 0) {
                    FlightInfo flightInfo = flightInfoList.get(row);
                    logger.debug("Flight info: {}", flightInfo);
                    FlightDetailPanel flightDetailPanel = new FlightDetailPanel(mainPanel, cardLayout, flightInfo);
                    mainPanel.add(flightDetailPanel, FLIGHT_DETAIL_SCREEN);
                    cardLayout.show(mainPanel, FLIGHT_DETAIL_SCREEN);
                }
            }
        });
        tablePanel.add(scrollPane, "grow");

        add(tablePanel, "grow");
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
}
