package com.group5.flight.booking.form;

import javax.swing.*;
import java.awt.*;

import com.group5.flight.booking.core.AppUtils;
import com.group5.flight.booking.core.Constants;
import com.group5.flight.booking.dto.FlightInfo;
import com.group5.flight.booking.form.component.FlightPayPanel;
import net.miginfocom.swing.MigLayout;

import static com.group5.flight.booking.core.Constants.*;

public class FlightDetailPanel extends JPanel {

    private final FlightInfo flightInfo;

    private static final String GAP_RIGHT = "gapright 10";

    private final JPanel mainPanel;

    private final CardLayout cardLayout;

    public FlightDetailPanel(JPanel mainPanel, CardLayout cardLayout, FlightInfo flightInfo) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.flightInfo = flightInfo;
        
        initComponents();
    }

    private void initComponents() {
        setLayout(new MigLayout("wrap, fill", "[grow]", "[grow 80][grow 20]"));
        setBackground(Color.WHITE);

        // Panel for flight information
        JPanel flightInfoPanel = new JPanel(new MigLayout("wrap 2", "[grow, right]20[grow, left]", "[]10[]10[]10[]10[]10[]10[]"));
        flightInfoPanel.setOpaque(false);

        JLabel lblTitle = new JLabel("Flight Information");
        lblTitle.setFont(new Font(Constants.FB_FONT, Font.BOLD, 28));
        lblTitle.setForeground(new Color(7, 164, 121));
        flightInfoPanel.add(lblTitle, "span, center, gapbottom 20");

        // Add flight information labels
        JLabel lblFlightCode = createLabel("Flight Code:");
        JLabel lblDepartureAirport = createLabel("Departure Airport:");
        JLabel lblArrivalAirport = createLabel("Arrival Airport:");
        JLabel lblDepartureTime = createLabel("Departure Time:");
        JLabel lblArrivalTime = createLabel("Arrival Time:");
        JLabel lblSeatClass = createLabel("Seat Class:");
        JLabel lblTicketPrice = createLabel("Ticket Price:");
        JLabel lblSeat = createLabel("Seat:");

        flightInfoPanel.add(lblFlightCode, GAP_RIGHT);
        flightInfoPanel.add(createValueLabel(String.valueOf(flightInfo.getFlightId())));

        flightInfoPanel.add(lblDepartureAirport, GAP_RIGHT);
        flightInfoPanel.add(createValueLabel(flightInfo.getFromAirport().getName()));

        flightInfoPanel.add(lblArrivalAirport, GAP_RIGHT);
        flightInfoPanel.add(createValueLabel(flightInfo.getToAirport().getName()));

        flightInfoPanel.add(lblDepartureTime, GAP_RIGHT);
        flightInfoPanel.add(createValueLabel(AppUtils.formatDate(flightInfo.getDepatureDate())));

        flightInfoPanel.add(lblArrivalTime, GAP_RIGHT);
        flightInfoPanel.add(createValueLabel(AppUtils.formatDate(flightInfo.getReturnDate())));

        //TODO
        flightInfoPanel.add(lblSeatClass, GAP_RIGHT);
        flightInfoPanel.add(createValueLabel("N/A"));

        flightInfoPanel.add(lblTicketPrice, GAP_RIGHT);
        flightInfoPanel.add(createValueLabel(String.valueOf(flightInfo.getBasePrice())));

        flightInfoPanel.add(lblSeat, GAP_RIGHT);
        flightInfoPanel.add(createValueLabel("Not Selected"));

        add(flightInfoPanel, "align center");

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new MigLayout("fill, insets 0", "[grow, left][center][grow, right]", "[grow]"));
        buttonPanel.setOpaque(false);

        JButton btnBack = createButton("Back");
        btnBack.addActionListener(e -> cardLayout.show(mainPanel, Constants.FLIGHT_SEARCHER_SCREEN));
        buttonPanel.add(btnBack, "cell 0 0, align left, w 120!, h 40!");

        JButton btnSelectSeat = createButton("Select Seat");
        btnSelectSeat.addActionListener(e -> {
            FlightSeatPanel seatPanel = new FlightSeatPanel(mainPanel, cardLayout);
            mainPanel.add(seatPanel, FLIGHT_SEAT_SCREEN);
            cardLayout.show(mainPanel, FLIGHT_SEAT_SCREEN);
        });
        buttonPanel.add(btnSelectSeat, "cell 1 0, align center, w 150!, h 40!");

        add(buttonPanel, "dock south");
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 16));
        label.setForeground(new Color(50, 50, 50));
        return label;
    }

    private JLabel createValueLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        label.setForeground(new Color(30, 144, 255));
        return label;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font(Constants.FB_FONT, Font.BOLD, 14));
        button.setBackground(new Color(7, 164, 121));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }
}
