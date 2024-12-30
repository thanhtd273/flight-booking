package com.group1.flight.booking.form;

import com.group1.flight.booking.core.AppUtils;
import com.group1.flight.booking.core.Constants;
import com.group1.flight.booking.dto.SeatInfo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestPanel {
    private static final int NUM_OF_COLUMN = 6;

    private static final int NUM_OF_PASSENGERS = 3;

    private static final List<Long> selectedSeats = new ArrayList<>();

    private static final List<SeatInfo> seatInfoList = new ArrayList<>();


    public static void main(String[] args) {
        generateFlightSeats();

        initComponent();
    }

    private static void initComponent() {
        JFrame frame = new JFrame("Thông tin liên hệ và chuyến bay");
        frame.setBackground(new Color(247, 249, 250));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(900, 600);

        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("wrap 1", "[grow]", "[top][grow][bottom]"));
        panel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JLabel lblTitle = new JLabel(String.format("Select %d seats", NUM_OF_PASSENGERS));
        lblTitle.setFont(new Font(Constants.FB_FONT, Font.BOLD, 24));
        lblTitle.setForeground(new Color(7, 164, 121));
        panel.add(lblTitle, "span, center, gapbottom 20");

        JPanel seatPanel = new JPanel(new MigLayout("wrap " + NUM_OF_COLUMN, "[grow, center]5".repeat(NUM_OF_COLUMN), "[]10[]"));
        seatPanel.setOpaque(false);

        for (SeatInfo seatInfo: seatInfoList) {
            JButton seatButton = getSeatButton(seatInfo);
            seatPanel.add(seatButton);
        }

        panel.add(seatPanel, "grow");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new MigLayout("wrap", "[grow]10[grow]"));
        buttonPanel.setOpaque(false);

        JButton btnBack = new JButton("Back");
        btnBack.setBackground(new Color(7, 164, 121));
        btnBack.setForeground(Color.WHITE);
        buttonPanel.add(btnBack, "w 100!, h 40!");

        JButton btnConfirm = new JButton("Confirm");
        btnConfirm.setBackground(new Color(7, 164, 121));
        btnConfirm.addActionListener(e -> {
            if (selectedSeats.size() == NUM_OF_PASSENGERS) {
                System.out.print(selectedSeats);
            } else {
                AppUtils.showErrorDialog("You have not selected enough seats");
            }
        });
        btnConfirm.setForeground(Color.WHITE);

        buttonPanel.add(btnConfirm, "w 100!, h 40!");

        panel.add(buttonPanel, "span, center");

        frame.add(scrollPane);
        frame.setVisible(true);
    }

    private static JButton getSeatButton(SeatInfo seatInfo) {
        JButton seatButton = new JButton(seatInfo.getSeatCode());
        seatButton.setFont(new Font(Constants.FB_FONT, Font.BOLD, 14));
        seatButton.setPreferredSize(new Dimension(50, 50));

        if (Boolean.TRUE.equals(seatInfo.getAvailable())) {
            seatButton.setBackground(new Color(7, 164, 121));
            seatButton.setForeground(Color.WHITE);
            seatButton.addActionListener(e -> {
                if (selectedSeats.size() == NUM_OF_PASSENGERS) {
                    AppUtils.showErrorDialog("You have selected all the seats booked");
                } else if (!selectedSeats.contains(seatInfo.getSeatId())) {
                    selectedSeats.add(seatInfo.getSeatId());
                    seatButton.setBackground(new Color(0, 123, 255));
                } else {
                    seatButton.setBackground(new Color(7, 164, 121));
                    selectedSeats.remove(seatInfo.getSeatId());
                }

            });
        } else {
            seatButton.setBackground(Color.GRAY);
            seatButton.setForeground(Color.WHITE);
            seatButton.setEnabled(false);
        }

        return seatButton;
    }

    public static void generateFlightSeats() {
        String classLevel;
        int seatIdCounter = 1;
        Random random = new Random();
        for (int row = 1; row <= 20; row++) {
            for (char column = 'A'; column <= 'G'; column++) {
                String seatCode = row + String.valueOf(column);

                if (row <= 5) {
                    classLevel = "First";
                } else if (row <= 15) {
                    classLevel = "Business";
                } else {
                    classLevel = "Economy";
                }

                seatInfoList.add(new SeatInfo((long) seatIdCounter++, 0L, null, classLevel, seatCode, random.nextBoolean()));
            }
        }
    }
}
