package com.group5.flight.booking.view.component;

import javax.swing.*;
import java.awt.*;
import net.miginfocom.swing.MigLayout;

public class PanelSelectFlight extends JPanel {

    private JLabel lblFlightCode;
    private JLabel lblDepartureAirport;
    private JLabel lblArrivalAirport;
    private JLabel lblDepartureTime;
    private JLabel lblArrivalTime;
    private JLabel lblSeatClass;
    private JLabel lblTicketPrice;
    private JLabel lblSeat;
    private JButton btnBack;
    private JButton btnSelectSeat;
    private JButton btnBook;

    public PanelSelectFlight() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new MigLayout("wrap, fill", "[grow]", "[grow 80][grow 20]"));
        setBackground(Color.WHITE);

        // Panel for flight information
        JPanel flightInfoPanel = new JPanel(new MigLayout("wrap 2", "[grow, right]20[grow, left]", "[]10[]10[]10[]10[]10[]10[]"));
        flightInfoPanel.setOpaque(false);

        JLabel lblTitle = new JLabel("Flight Information");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTitle.setForeground(new Color(7, 164, 121));
        flightInfoPanel.add(lblTitle, "span, center, gapbottom 20");

        // Add flight information labels
        lblFlightCode = createLabel("Flight Code:");
        lblDepartureAirport = createLabel("Departure Airport:");
        lblArrivalAirport = createLabel("Arrival Airport:");
        lblDepartureTime = createLabel("Departure Time:");
        lblArrivalTime = createLabel("Arrival Time:");
        lblSeatClass = createLabel("Seat Class:");
        lblTicketPrice = createLabel("Ticket Price:");
        lblSeat = createLabel("Seat:");

        flightInfoPanel.add(lblFlightCode, "gapright 10");
        flightInfoPanel.add(createValueLabel("N/A"));

        flightInfoPanel.add(lblDepartureAirport, "gapright 10");
        flightInfoPanel.add(createValueLabel("N/A"));

        flightInfoPanel.add(lblArrivalAirport, "gapright 10");
        flightInfoPanel.add(createValueLabel("N/A"));

        flightInfoPanel.add(lblDepartureTime, "gapright 10");
        flightInfoPanel.add(createValueLabel("N/A"));

        flightInfoPanel.add(lblArrivalTime, "gapright 10");
        flightInfoPanel.add(createValueLabel("N/A"));

        flightInfoPanel.add(lblSeatClass, "gapright 10");
        flightInfoPanel.add(createValueLabel("N/A"));

        flightInfoPanel.add(lblTicketPrice, "gapright 10");
        flightInfoPanel.add(createValueLabel("N/A"));

        flightInfoPanel.add(lblSeat, "gapright 10");
        flightInfoPanel.add(createValueLabel("Not Selected"));

        add(flightInfoPanel, "align center");

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new MigLayout("fill, insets 0", "[grow, left][center][grow, right]", "[grow]"));
        buttonPanel.setOpaque(false);

        btnBack = createButton("Back");
        buttonPanel.add(btnBack, "cell 0 0, align left, w 120!, h 40!");

        btnSelectSeat = createButton("Select Seat");
        buttonPanel.add(btnSelectSeat, "cell 1 0, align center, w 150!, h 40!");

        btnBook = createButton("Book");
        buttonPanel.add(btnBook, "cell 2 0, align right, w 120!, h 40!");

        add(buttonPanel, "dock south");
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 16));
        label.setForeground(new Color(50, 50, 50));
        return label;
    }

    private JLabel createValueLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        label.setForeground(new Color(30, 144, 255));
        return label;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(new Color(7, 164, 121));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    // Getters for buttons
    public JButton getBtnBack() {
        return btnBack;
    }

    public JButton getBtnSelectSeat() {
        return btnSelectSeat;
    }

    public JButton getBtnBook() {
        return btnBook;
    }

    // Methods to set flight information dynamically
    public void setFlightCode(String flightCode) {
        lblFlightCode.setText("Flight Code: " + flightCode);
    }

    public void setDepartureAirport(String departureAirport) {
        lblDepartureAirport.setText("Departure Airport: " + departureAirport);
    }

    public void setArrivalAirport(String arrivalAirport) {
        lblArrivalAirport.setText("Arrival Airport: " + arrivalAirport);
    }

    public void setDepartureTime(String departureTime) {
        lblDepartureTime.setText("Departure Time: " + departureTime);
    }

    public void setArrivalTime(String arrivalTime) {
        lblArrivalTime.setText("Arrival Time: " + arrivalTime);
    }

    public void setSeatClass(String seatClass) {
        lblSeatClass.setText("Seat Class: " + seatClass);
    }

    public void setTicketPrice(String ticketPrice) {
        lblTicketPrice.setText("Ticket Price: " + ticketPrice);
    }

    public void setSeat(String seat) {
        lblSeat.setText("Seat: " + seat);
    }
}
