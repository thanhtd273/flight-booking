package com.group5.flight.booking.view.component;

import javax.swing.*;
import java.awt.*;
import net.miginfocom.swing.MigLayout;

public class PanelSeatFlight extends JPanel {

    private JPanel seatPanel;        // Panel to display available seats
    private JTextField txtSeat;      // Text field for seat selection
    private JButton btnBack;         // "Back" button
    private JButton btnConfirm;      // "Confirm" button
    private JLabel lblAvailableSeats;// Label to display available seats

    private boolean[] seats;         // Array to track seat availability
    private int rows = 5;            // Number of rows (adjustable)
    private int cols = 4;            // Number of columns (fixed at 4)

    public PanelSeatFlight() {
        initSeatData(); // Initialize seat availability
        initComponents();
    }

    private void initSeatData() {
        // Create an array to represent seat availability (true: available, false: occupied)
        seats = new boolean[rows * cols];
        for (int i = 0; i < seats.length; i++) {
            seats[i] = (i % 3 != 0); // For demo: mark every 3rd seat as occupied
        }
    }

    private void initComponents() {
        setLayout(new MigLayout("wrap 1", "[grow]", "[top][grow][bottom]"));
        setBackground(Color.WHITE);

        // Title
        JLabel lblTitle = new JLabel("Select a Seat");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitle.setForeground(new Color(7, 164, 121));
        add(lblTitle, "span, center, gapbottom 20");

        // Seat selection panel
        seatPanel = new JPanel(new MigLayout("wrap " + cols, "[grow, center]5".repeat(cols), "[]10[]"));
        seatPanel.setOpaque(false);

        for (int i = 0; i < seats.length; i++) {
            JButton seatButton = new JButton(String.valueOf(i + 1));
            seatButton.setFont(new Font("SansSerif", Font.BOLD, 14));
            seatButton.setPreferredSize(new Dimension(50, 50));

            if (seats[i]) {
                // Available seat
                seatButton.setBackground(new Color(7, 164, 121));
                seatButton.setForeground(Color.WHITE);
            } else {
                // Occupied seat
                seatButton.setBackground(Color.GRAY);
                seatButton.setForeground(Color.WHITE);
                seatButton.setEnabled(false); // Disable the button for occupied seats
            }

            seatButton.addActionListener(e -> txtSeat.setText(seatButton.getText())); // Set selected seat
            seatPanel.add(seatButton);
        }

        add(seatPanel, "grow");

        // Seat selection label and input field
        lblAvailableSeats = new JLabel("Enter Seat Number");
        lblAvailableSeats.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblAvailableSeats.setForeground(new Color(100, 100, 100));
        add(lblAvailableSeats, "span, center, gapbottom 10");

        txtSeat = new JTextField();
        txtSeat.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtSeat.setPreferredSize(new Dimension(100, 30));
        add(txtSeat, "span, center, wrap");

        // Buttons (Back & Confirm)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new MigLayout("wrap", "[grow]10[grow]"));
        buttonPanel.setOpaque(false);

        btnBack = new JButton("Back");
        btnBack.setBackground(new Color(7, 164, 121));
        btnBack.setForeground(Color.WHITE);
        buttonPanel.add(btnBack, "w 100!, h 40!");

        btnConfirm = new JButton("Confirm");
        btnConfirm.setBackground(new Color(7, 164, 121));
        btnConfirm.setForeground(Color.WHITE);
        buttonPanel.add(btnConfirm, "w 100!, h 40!");

        add(buttonPanel, "span, center");
    }

    // Getters for buttons (to add event listeners later)
    public JButton getBtnBack() {
        return btnBack;
    }

    public JButton getBtnConfirm() {
        return btnConfirm;
    }

    // Getter for the seat input field
    public JTextField getTxtSeat() {
        return txtSeat;
    }
}
