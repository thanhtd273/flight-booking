package com.group5.flight.booking.view.component;

import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dto.FlightInfo;
import com.group5.flight.booking.model.Seat;
import com.group5.flight.booking.service.FlightService;
import com.group5.flight.booking.service.SeatService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SelectFlight extends JFrame {

    private FlightInfo selectedFlight;
    private int quantity;
    private FlightService flightService;
    private SeatService seatService;
    private String selectedSeat = "Chưa chọn";

    public SelectFlight(FlightInfo selectedFlight, int quantity, SeatService seatService) {
        this.selectedFlight = selectedFlight;
        this.quantity = quantity;
        this.seatService = seatService;

        setTitle("Thông tin chuyến bay");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));

        panel.add(new JLabel("Mã chuyến bay:"));
        panel.add(new JLabel(selectedFlight.getPlaneId().toString()));
        panel.add(new JLabel("Điểm bắt đầu:"));
        panel.add(new JLabel(selectedFlight.getFromAirport().getAirportName()));
        panel.add(new JLabel("Điểm đến:"));
        panel.add(new JLabel(selectedFlight.getToAirport().getAirportName()));
        panel.add(new JLabel("Giờ bay:"));
        panel.add(new JLabel(selectedFlight.getDepatureDate().toString()));
        panel.add(new JLabel("Giá:"));
        panel.add(new JLabel(String.valueOf(selectedFlight.getBasePrice())));
        panel.add(new JLabel("Số lượng vé:"));
        panel.add(new JLabel(String.valueOf(quantity)));
        panel.add(new JLabel("Ghế ngồi:"));
        panel.add(new JLabel(selectedSeat));  

        JButton backButton = new JButton("Quay lại");
        JButton bookButton = new JButton("Đặt vé");
        JButton seatInfoButton = new JButton("Thông tin ghế ngồi");

        panel.add(backButton);
        panel.add(bookButton);
        panel.add(seatInfoButton);

        backButton.addActionListener(e -> {
            dispose(); // Quay lại màn hình trước
            new FindFlightBooking(flightService).setVisible(true); 
        });

        bookButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Bạn đã đặt vé thành công!");
            dispose();
        });

        seatInfoButton.addActionListener(e -> {
            try {
                List<Seat> availableSeats = seatService.getAvailableSeatsByFlight(selectedFlight.getPlaneId());
                new SeatInfoUI(selectedFlight, availableSeats, quantity, seatService).setVisible(true);
                this.setVisible(false); 
            } catch (LogicException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi lấy danh sách ghế: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(panel, BorderLayout.CENTER);
    }

    public void setSelectedSeat(String seat) {
        this.selectedSeat = seat;
    }
}
