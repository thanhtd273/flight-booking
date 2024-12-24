package com.group5.flight.booking.form;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import com.group5.flight.booking.core.Constants;
import com.group5.flight.booking.dto.FlightInfo;
import com.group5.flight.booking.dto.SeatInfo;
import com.group5.flight.booking.form.component.FlightPayPanel;
import com.group5.flight.booking.model.Plane;
import com.group5.flight.booking.service.FlightService;
import com.group5.flight.booking.service.PlaneService;
import net.miginfocom.swing.MigLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.group5.flight.booking.form.swing.Button;

public class FlightSeatPanel extends JPanel {

    private static final Logger logger = LoggerFactory.getLogger(FlightSeatPanel.class);

    private JTextField txtSeat;

    private boolean[] seats;

    private static final int NUM_OF_COLUMN = 6;

    private final FlightInfo flightInfo;

    private final PlaneService planeService;

    private final FlightService flightService;

    private final JPanel mainPanel;

    private final CardLayout cardLayout;

    public FlightSeatPanel(JPanel mainPanel, CardLayout cardLayout, FlightInfo flightInfo,
                           PlaneService planeService, FlightService flightService) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.flightInfo = flightInfo;
        this.planeService = planeService;
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

        // Sử dụng GridBagLayout để tạo khoảng cách giữa 3 cột trái và 3 cột phải
        JPanel seatPanel = new JPanel(new GridBagLayout());
        seatPanel.setOpaque(false);

        Dimension fixedSize = new Dimension(350, 300); // Kích thước cố định cho khung chứa
        int buttonWidth = fixedSize.width / NUM_OF_COLUMN - 15; // Chiều rộng mỗi nút ghế
        int buttonHeight = fixedSize.height / (seats.length / NUM_OF_COLUMN + 1) - 10; // Chiều cao mỗi nút ghế

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

            // Tính toán vị trí hàng và cột
            int row = i / NUM_OF_COLUMN; // Số hàng
            int col = i % NUM_OF_COLUMN; // Số cột

            // Thêm khoảng cách lớn hơn giữa cột 3 và cột 4
            if (col == 3) {
                gbc.insets = new Insets(10, 80, 10, 10); // Khoảng cách lớn hơn giữa cột 3 và 4
            } else {
                gbc.insets = new Insets(10, 10, 10, 10); // Khoảng cách chuẩn giữa các nút
            }

            gbc.gridx = col; // Cột hiện tại
            gbc.gridy = row; // Hàng hiện tại

            seatPanel.add(seatButton, gbc);
        }

        // Thêm thanh cuộn
        JScrollPane scrollPane = new JScrollPane(seatPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(350, 300)); // Kích thước khung cố định
        add(scrollPane, "grow");

        // Chỉ giữ lại nút Back và Confirm
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
            Plane plane = planeService.findByPlaneId(flightInfo.getPlane().getPlaneId());
            List<SeatInfo> seatInfoList = flightService.getFlightSeats(flightInfo.getFlightId());
            logger.debug("Search Info list: {}", seatInfoList);

            seats = new boolean[plane.getNumOfSeats()];
            for (int i = 0; i < seats.length; i++) {
                seats[i] = (i % 3 != 0);
            }
        } catch (Exception e) {
            logger.error("Get flight's seats failed, error: {}", e.getMessage());
        }

    }
}
