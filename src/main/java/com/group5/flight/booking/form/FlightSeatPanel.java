package com.group5.flight.booking.form;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import com.group5.flight.booking.core.Constants;
import com.group5.flight.booking.dto.FlightInfo;
import com.group5.flight.booking.dto.SeatInfo;
import com.group5.flight.booking.form.component.FlightPayPanel;
import com.group5.flight.booking.service.FlightService;
import net.miginfocom.swing.MigLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.group5.flight.booking.form.swing.Button;

public class FlightSeatPanel extends JPanel {

    private static final Logger logger = LoggerFactory.getLogger(FlightSeatPanel.class);

    private boolean[] seats;

    private static final int NUM_OF_COLUMN = 6;

    private final FlightInfo flightInfo;

    private final FlightService flightService;

    private final JPanel mainPanel;

    private final CardLayout cardLayout;

    public FlightSeatPanel(JPanel mainPanel, CardLayout cardLayout, FlightInfo flightInfo, FlightService flightService) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.flightInfo = flightInfo;
        this.flightService = flightService;

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

        JPanel seatPanel = new JPanel(new GridBagLayout());
        seatPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        for (int i = 0; i < seats.length; i++) {
            Button seatButton = new Button();
            seatButton.setText(String.valueOf(i + 1));
            seatButton.setFont(new Font(Constants.FB_FONT, Font.BOLD, 14));
            seatButton.setPreferredSize(new Dimension(20, 50));

            if (seats[i]) {
                seatButton.setBackground(new Color(7, 164, 121));
                seatButton.setForeground(Color.WHITE);
            } else {
                seatButton.setBackground(Color.GRAY);
                seatButton.setForeground(Color.WHITE);
                seatButton.setEnabled(false);
            }

            int row = i / NUM_OF_COLUMN;
            int col = i % NUM_OF_COLUMN;

            if (col == 3) {
                gbc.insets = new Insets(10, 80, 10, 10);
            } else {
                gbc.insets = new Insets(10, 10, 10, 10);
            }
            gbc.gridx = col;
            gbc.gridy = row;

            seatPanel.add(seatButton, gbc);
        }

        JScrollPane scrollPane = new JScrollPane(seatPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(350, 300));
        add(scrollPane, "grow");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new MigLayout("wrap", "[grow]10[grow]"));
        buttonPanel.setOpaque(false);

        JButton btnBack = new JButton("Back");
        btnBack.setBackground(new Color(7, 164, 121));
        btnBack.setForeground(Color.WHITE);
        btnBack.addActionListener(e -> cardLayout.show(mainPanel, Constants.FLIGHT_DETAIL_SCREEN));
        buttonPanel.add(btnBack, "w 100!, h 40!");

        JButton btnConfirm = new JButton("Confirm");
        btnConfirm.setBackground(new Color(20, 140, 180));
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
        try {
            List<SeatInfo> seatInfoList = flightService.getFlightSeats(flightInfo.getFlightId());
            logger.debug("Search Info list: {}", seatInfoList);

            seats = new boolean[120];
            for (int i = 0; i < seats.length; i++) {
                seats[i] = (i % 3 != 0);
            }
        } catch (Exception e) {
            logger.error("Get flight's seats failed, error: {}", e.getMessage());
        }
    }
}
