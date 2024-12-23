package com.group5.flight.booking.form;

import javax.swing.*;
import java.awt.*;

import com.group5.flight.booking.core.Constants;
import com.group5.flight.booking.form.component.FlightPayPanel;
import net.miginfocom.swing.MigLayout;

public class FlightSeatPanel extends JPanel {

    private JTextField txtSeat;

    private boolean[] seats;
    private static final int NUM_OF_COLUMN = 4;

    private final JPanel mainPanel;

    private final CardLayout cardLayout;

    public FlightSeatPanel(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;

        initSeatData();
        initComponents();
    }

    private void initComponents() {
        setLayout(new MigLayout("wrap 1", "[grow]", "[top][grow][bottom]"));
        setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Select a Seat");
        lblTitle.setFont(new Font(Constants.FB_FONT, Font.BOLD, 24));
        lblTitle.setForeground(new Color(7, 164, 121));
        add(lblTitle, "span, center, gapbottom 20");

        JPanel seatPanel = new JPanel(new MigLayout("wrap " + NUM_OF_COLUMN, "[grow, center]5".repeat(NUM_OF_COLUMN), "[]10[]"));
        seatPanel.setOpaque(false);

        for (int i = 0; i < seats.length; i++) {
            JButton seatButton = new JButton(String.valueOf(i + 1));
            seatButton.setFont(new Font(Constants.FB_FONT, Font.BOLD, 14));
            seatButton.setPreferredSize(new Dimension(50, 50));

            if (seats[i]) {
                seatButton.setBackground(new Color(7, 164, 121));
                seatButton.setForeground(Color.WHITE);
            } else {
                seatButton.setBackground(Color.GRAY);
                seatButton.setForeground(Color.WHITE);
                seatButton.setEnabled(false);
            }

            seatButton.addActionListener(e -> txtSeat.setText(seatButton.getText()));
            seatPanel.add(seatButton);
        }

        add(seatPanel, "grow");

        // Seat selection label and input field
        JLabel lblAvailableSeats = new JLabel("Enter Seat Number");
        lblAvailableSeats.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 16));
        lblAvailableSeats.setForeground(new Color(100, 100, 100));
        add(lblAvailableSeats, "span, center, gapbottom 10");

        txtSeat = new JTextField();
        txtSeat.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 16));
        txtSeat.setPreferredSize(new Dimension(100, 30));
        add(txtSeat, "span, center, wrap");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new MigLayout("wrap", "[grow]10[grow]"));
        buttonPanel.setOpaque(false);

        JButton btnBack = new JButton("Back");
        btnBack.setBackground(new Color(7, 164, 121));
        btnBack.setForeground(Color.WHITE);
        btnBack.addActionListener(e -> cardLayout.show(mainPanel, Constants.FLIGHT_DETAIL_SCREEN));
        buttonPanel.add(btnBack, "w 100!, h 40!");

        JButton btnConfirm = new JButton("Confirm");
        btnConfirm.setBackground(new Color(7, 164, 121));
        btnConfirm.setForeground(Color.WHITE);
        btnConfirm.addActionListener(e -> {
            FlightPayPanel payPanel = new FlightPayPanel(mainPanel, cardLayout);
            mainPanel.add(payPanel, Constants.FLIGHT_PAYER);
            cardLayout.show(mainPanel, Constants.FLIGHT_PAYER);
        });
        buttonPanel.add(btnConfirm, "w 100!, h 40!");

        add(buttonPanel, "span, center");
    }

    private void initSeatData() {
        int rows = 5;
        seats = new boolean[rows * NUM_OF_COLUMN];
        for (int i = 0; i < seats.length; i++) {
            seats[i] = (i % 3 != 0);
        }
    }
}
