package com.group5.flight.booking.view.component;

import com.group5.flight.booking.model.Seat;
import com.group5.flight.booking.service.SeatService;
import com.group5.flight.booking.dto.FlightInfo;
import com.group5.flight.booking.core.exception.LogicException;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SeatInfoUI extends JFrame {

    private FlightInfo selectedFlight;
    private List<Seat> availableSeats;
    private int quantity;
    private SeatService seatService;
    private String selectedSeats = "";

    // Constructor nhận thêm selectedFlight
    public SeatInfoUI(FlightInfo selectedFlight, List<Seat> availableSeats, int quantity, SeatService seatService) {
        this.selectedFlight = selectedFlight;
        this.availableSeats = availableSeats;
        this.quantity = quantity;
        this.seatService = seatService;

        // Thiết lập JFrame
        setTitle("Chọn ghế ngồi");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(availableSeats.size() + 2, 1));

        // Hiển thị danh sách ghế trống
        for (Seat seat : availableSeats) {
            JCheckBox seatCheckbox = new JCheckBox("Ghế " + seat.getSeatId() + " - " + seat.getClassLevel());
            seatCheckbox.setActionCommand(String.valueOf(seat.getSeatId()));
            panel.add(seatCheckbox);
        }

        // Các nút
        JButton backButton = new JButton("Quay lại");
        JButton confirmButton = new JButton("Xác nhận");

        panel.add(backButton);
        panel.add(confirmButton);

        // Xử lý sự kiện khi nhấn nút "Quay lại"
        backButton.addActionListener(e -> {
            dispose(); // Quay lại giao diện trước
            new SelectFlight(selectedFlight, quantity, seatService).setVisible(true); // Chuyển lại giao diện trước
        });

        // Xử lý sự kiện khi nhấn nút "Xác nhận"
        confirmButton.addActionListener(e -> {
            StringBuilder selectedSeatIds = new StringBuilder();
            for (Component component : panel.getComponents()) {
                if (component instanceof JCheckBox) {
                    JCheckBox checkBox = (JCheckBox) component;
                    if (checkBox.isSelected()) {
                        selectedSeatIds.append(checkBox.getActionCommand()).append(" ");
                    }
                }
            }

            // Kiểm tra số lượng ghế đã chọn
            if (selectedSeatIds.length() > 0) {
                if (selectedSeatIds.toString().split(" ").length == quantity) {
                    selectedSeats = selectedSeatIds.toString();
                    JOptionPane.showMessageDialog(this, "Đặt ghế thành công! Ghế đã chọn: " + selectedSeats);
                    dispose();
                    new SelectFlight(selectedFlight, quantity, seatService).setVisible(true); // Quay lại giao diện chuyến bay
                } else {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn đủ số ghế.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất 1 ghế.");
            }
        });

        add(panel, BorderLayout.CENTER);
    }
}
