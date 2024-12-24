package com.group5.flight.booking.form;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import com.group5.flight.booking.core.AppUtils;
import com.group5.flight.booking.core.Constants;
import com.group5.flight.booking.dto.BookingInfo;
import com.group5.flight.booking.dto.SeatInfo;
import com.group5.flight.booking.form.component.FlightPayPanel;
import com.group5.flight.booking.service.BookingService;
import com.group5.flight.booking.service.FlightService;
import net.miginfocom.swing.MigLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.group5.flight.booking.form.swing.Button;

public class FlightSeatPanel extends JPanel {

    private static final Logger logger = LoggerFactory.getLogger(FlightSeatPanel.class);

    private static final int NUM_OF_COLUMN = 6;

    private final BookingInfo bookingInfo;

    private final List<SeatInfo> selectedSeats = new ArrayList<>();

    private List<SeatInfo> seatInfoList = new ArrayList<>();

    private final FlightService flightService;

    private final BookingService bookingService;

    private final JPanel mainPanel;

    private final CardLayout cardLayout;

    public FlightSeatPanel(JPanel mainPanel, CardLayout cardLayout, BookingInfo bookingInfo,
                           FlightService flightService, BookingService bookingService) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.bookingInfo = bookingInfo;
        this.flightService = flightService;
        this.bookingService = bookingService;

        initSeatData();
        initComponents();
    }

    private void initComponents() {
        setLayout(new MigLayout("wrap 1", "[grow]", "[top][grow][bottom]"));
        setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel(String.format("Select %d seats", bookingInfo.getNumOfPassengers()));
        lblTitle.setFont(new Font(Constants.FB_FONT, Font.BOLD, 24));
        lblTitle.setForeground(new Color(7, 164, 121));
        add(lblTitle, "span, center, gapbottom 20");

        JPanel seatPanel = new JPanel(new GridBagLayout());
        seatPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        for (int i = 0; i < seatInfoList.size(); i++) {
            Button seatButton = createSeatSelectionBtn(i);

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

        JPanel actionPanel = createActionPanel();

        add(actionPanel, "span, center");
    }

    private Button createSeatSelectionBtn(int i) {
        SeatInfo seatInfo = seatInfoList.get(i);

        Button seatButton = new Button();
        seatButton.setText(seatInfo.getSeatCode());
        seatButton.setFont(new Font(Constants.FB_FONT, Font.BOLD, 14));
        seatButton.setPreferredSize(new Dimension(20, 50));

        if (Boolean.TRUE.equals(seatInfo.getAvailable())) {
            seatButton.setBackground(new Color(7, 164, 121));
            seatButton.setForeground(Color.WHITE);
            seatButton.addActionListener(e -> {
                if (!selectedSeats.contains(seatInfo)) {
                    if (selectedSeats.size() == bookingInfo.getNumOfPassengers()) {
                        AppUtils.showErrorDialog("You have not selected enough seats");
                    } else {
                        selectedSeats.add(seatInfo);
                        seatButton.setBackground(new Color(0, 123, 255));
                    }

                } else{
                    seatButton.setBackground(new Color(7, 164, 121));
                    selectedSeats.remove(seatInfo);
                }
                logger.debug("Selected seats: {}", selectedSeats);
            });
        } else {
            seatButton.setBackground(Color.GRAY);
            seatButton.setForeground(Color.WHITE);
            seatButton.setEnabled(false);
        }
        return seatButton;
    }

    private JPanel createActionPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new MigLayout("wrap", "[grow]10[grow]"));
        buttonPanel.setOpaque(false);

        JButton btnBack = new JButton("Back");
        btnBack.setBackground(new Color(7, 164, 121));
        btnBack.setForeground(Color.WHITE);
        btnBack.addActionListener(e -> cardLayout.show(mainPanel, Constants.FLIGHT_LIST_SCREEN));
        buttonPanel.add(btnBack, "w 100!, h 40!");

        JButton btnContinue = new JButton("Continue");
        btnContinue.setBackground(new Color(20, 140, 180));
        btnContinue.setForeground(Color.WHITE);
        btnContinue.addActionListener(e -> {
            if (bookingInfo.getNumOfPassengers() == selectedSeats.size()) {
                int numOfPassenger = bookingInfo.getNumOfPassengers();
                SeatInfo[] seatIds = new SeatInfo[bookingInfo.getNumOfPassengers()];
                for (int i = 0; i < numOfPassenger; i ++) {
                    seatIds[i] = selectedSeats.get(i);
                }
                bookingInfo.setSeatInfos(seatIds);
                logger.debug("bookingInfo: {}", bookingInfo);
                FlightPayPanel payPanel = new FlightPayPanel(mainPanel, cardLayout, bookingInfo, bookingService);
                mainPanel.add(payPanel, Constants.FLIGHT_PAYER);
                cardLayout.show(mainPanel, Constants.FLIGHT_PAYER);
            } else {
                AppUtils.showErrorDialog("You have not selected enough seats");
            }

        });
        buttonPanel.add(btnContinue, "w 100!, h 40!");
        return buttonPanel;
    }

    private void initSeatData() {
        try {
            seatInfoList = flightService.getFlightSeats(bookingInfo.getFlightId());
            logger.debug("Search Info list: {}", seatInfoList);

        } catch (Exception e) {
            logger.error("Get flight's seats failed, error: {}", e.getMessage());
        }

    }
}
