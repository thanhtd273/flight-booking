package com.group5.flight.booking;

import com.group5.flight.booking.view.component.PanelFlightSearch;
import com.group5.flight.booking.view.component.PanelSelectFlight;
import com.group5.flight.booking.view.component.PanelSeatFlight;
import com.group5.flight.booking.view.component.PanelPayFlight;

import java.awt.Color;
import javax.swing.JFrame;

public class Application extends JFrame {

    private PanelFlightSearch flightSearchPanel;
    private PanelSelectFlight selectFlightPanel;
    private PanelSeatFlight seatFlightPanel;
    private PanelPayFlight payFlightPanel;

    public Application() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Flight Booking Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(245, 245, 245));

        // Initialize the panels
        flightSearchPanel = new PanelFlightSearch();
        selectFlightPanel = new PanelSelectFlight();
        seatFlightPanel = new PanelSeatFlight();
        payFlightPanel = new PanelPayFlight();

        // Add action listener for "Search" button in PanelFlightSearch
        flightSearchPanel.getBtnSearch().addActionListener(e -> showFlightSelectionPanel());

        // Add action listener for "Select Seat" button in PanelSelectFlight
        selectFlightPanel.getBtnSelectSeat().addActionListener(e -> showSeatSelectionPanel());

        // Add action listener for "Book" button in PanelSelectFlight
        selectFlightPanel.getBtnBook().addActionListener(e -> showPaymentPanel());

        // Set the initial panel to flightSearchPanel
        setContentPane(flightSearchPanel);
    }

    private void showFlightSelectionPanel() {
        // Switch to the PanelSelectFlight panel
        setContentPane(selectFlightPanel);
        revalidate();
        repaint();
    }

    private void showSeatSelectionPanel() {
        // Switch to the PanelSeatFlight panel
        setContentPane(seatFlightPanel);
        revalidate();
        repaint();
    }

    private void showPaymentPanel() {
        // Switch to the PanelPayFlight panel
        setContentPane(payFlightPanel);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new Application().setVisible(true);
        });
    }
}
