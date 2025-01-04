package com.group1.flight.booking.form;

import javax.swing.*;
import java.awt.*;

import com.group1.flight.booking.core.AppUtils;
import com.group1.flight.booking.dto.BookingInfo;
import com.group1.flight.booking.dto.FlightInfo;
import com.group1.flight.booking.service.BookingService;
import net.miginfocom.swing.MigLayout;
import com.group1.flight.booking.form.component.FlightPayPanel;

import static com.group1.flight.booking.core.Constants.*;

public class FlightDetailPanel extends JPanel {
    
    private static final String GAP_LEFT = "gap left 10";

    private final FlightInfo flightInfo;

    private final JPanel mainPanel;

    private final CardLayout cardLayout;

    private final BookingInfo bookingInfo;

    private final BookingService bookingService;

    public FlightDetailPanel(JPanel mainPanel, CardLayout cardLayout, FlightInfo flightInfo, BookingInfo bookingInfo,
                             BookingService bookingService) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.flightInfo = flightInfo;
        this.bookingInfo = bookingInfo;
        this.bookingService = bookingService;
        initComponents();
    }

    private void initComponents() {
        setLayout(new MigLayout("wrap, fill", "[grow]", "[grow 80][grow 20]"));
        setBackground(Color.WHITE);

        JPanel flightInfoPanel = new JPanel(new MigLayout("wrap 2", "[grow, right]20[grow, left]", "[]10[]10[]10[]10[]10[]10[]"));
        flightInfoPanel.setOpaque(false);

        JLabel lblTitle = new JLabel("Flight Information");
        lblTitle.setFont(new Font(FB_FONT, Font.BOLD, 28));
        lblTitle.setForeground(new Color(34, 177, 76));
        flightInfoPanel.add(lblTitle, "span, center, gap bottom 20");

        // Add flight information labels
        JLabel lblFlightCode = createLabel("Flight Code:");
        JLabel lblDepartureAirport = createLabel("Departure Airport:");
        JLabel lblArrivalAirport = createLabel("Arrival Airport:");
        JLabel lblDepartureTime = createLabel("Departure Time:");
        JLabel lblArrivalTime = createLabel("Arrival Time:");
        JLabel lblTicketPrice = createLabel("Ticket Price:");
        JLabel lblSeat = createLabel("Seat:");

        flightInfoPanel.add(lblFlightCode, GAP_LEFT);
        flightInfoPanel.add(createValueLabel(String.valueOf(flightInfo.getFlightId())));

        flightInfoPanel.add(lblDepartureAirport, GAP_LEFT);
        flightInfoPanel.add(createValueLabel(flightInfo.getDepartureAirportInfo().getName()));

        flightInfoPanel.add(lblArrivalAirport, GAP_LEFT);
        flightInfoPanel.add(createValueLabel(flightInfo.getDestinationAirportInfo().getName()));

        flightInfoPanel.add(lblDepartureTime, GAP_LEFT);
        flightInfoPanel.add(createValueLabel(AppUtils.formatDate(flightInfo.getDepartureDate())));

        flightInfoPanel.add(lblArrivalTime, GAP_LEFT);
        flightInfoPanel.add(createValueLabel(AppUtils.formatDate(flightInfo.getReturnDate())));

        flightInfoPanel.add(lblTicketPrice, GAP_LEFT);
        flightInfoPanel.add(createValueLabel(String.valueOf(flightInfo.getBasePrice())));

        flightInfoPanel.add(lblSeat, GAP_LEFT);
        flightInfoPanel.add(createValueLabel("Not Selected"));

        add(flightInfoPanel, "align center");

        JPanel buttonPanel = new JPanel(new MigLayout("fill, insets 0", "[grow, left][center][grow, right]", "[grow]"));
        buttonPanel.setOpaque(false);

        JButton btnBack = createButton("Back");
        btnBack.addActionListener(e -> cardLayout.show(mainPanel, FLIGHT_SEAT_SCREEN));
        buttonPanel.add(btnBack, "cell 0 0, align left, w 120!, h 40!");

        JButton btnSelectSeat = createButton("Book");
        btnSelectSeat.addActionListener(e -> {
            FlightPayPanel payPanel = new FlightPayPanel(mainPanel, cardLayout, bookingInfo, bookingService);
            mainPanel.add(payPanel, FLIGHT_PAYER);
            cardLayout.show(mainPanel, FLIGHT_PAYER);
        });
        buttonPanel.add(btnSelectSeat, "cell 1 0, align center, w 150!, h 40!");

        add(buttonPanel, "dock south");
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(FB_FONT, Font.PLAIN, 16));
        label.setForeground(new Color(50, 50, 50));
        return label;
    }

    private JLabel createValueLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(FB_FONT, Font.BOLD, 16));
        label.setForeground(new Color(30, 144, 255));
        return label;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font(FB_FONT, Font.BOLD, 14));
        button.setBackground(new Color(34, 177, 76));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }
}
