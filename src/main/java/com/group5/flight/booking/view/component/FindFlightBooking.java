package com.group5.flight.booking.view.component;

import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dto.FlightInfo;
import com.group5.flight.booking.service.FlightService;
import com.group5.flight.booking.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class FindFlightBooking extends JFrame {

    private JTextField fromField, toField, timeField;
    private JSpinner quantitySpinner;
    private JButton searchButton;
    private JTable resultTable;
    private FlightService flightService;
    private SeatService seatService;

    @Autowired
    public FindFlightBooking(FlightService flightService) {
        setTitle("Flight Booking");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.flightService = flightService;

        fromField = new JTextField(20);
        toField = new JTextField(20);
        timeField = new JTextField(20);
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        searchButton = new JButton("Tìm kiếm");

        resultTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(resultTable);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));
        panel.add(new JLabel("Điểm bắt đầu:"));
        panel.add(fromField);
        panel.add(new JLabel("Điểm đến:"));
        panel.add(toField);
        panel.add(new JLabel("Giờ bay:"));
        panel.add(timeField);
        panel.add(new JLabel("Số lượng:"));
        panel.add(quantitySpinner);
        panel.add(new JLabel());
        panel.add(searchButton);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchFlights();
            }
        });
    }

    public JSpinner getQuantitySpinner() {
        return quantitySpinner;
    }

    private void searchFlights() {
        String from = fromField.getText();  
        String to = toField.getText();      
        String timeStr = timeField.getText();  

        Date time = parseDate(timeStr);

        if (time != null) {
            Long fromAirportId = getAirportIdByName(from);
            Long toAirportId = getAirportIdByName(to);

            if (fromAirportId != null && toAirportId != null) {
                // Gọi service để tìm kiếm chuyến bay
                try {
                    List<FlightInfo> flights = flightService.findFlight(fromAirportId, toAirportId, time);

                    flights.sort((f1, f2) -> {
                        int score1 = calculateMatchScore(f1, fromAirportId, toAirportId, time);
                        int score2 = calculateMatchScore(f2, fromAirportId, toAirportId, time);
                        return Integer.compare(score2, score1); 
                    });

                    String[] columns = {"Mã chuyến bay", "Điểm bắt đầu", "Điểm đến", "Giờ bay", "Giá"};
                    Object[][] data = new Object[flights.size()][5];
                    for (int i = 0; i < flights.size(); i++) {
                        FlightInfo flight = flights.get(i);

                        data[i] = new Object[]{
                                flight.getPlaneId(),
                                flight.getFromAirport().getAirportName(),
                                flight.getToAirport().getAirportName(),
                                flight.getDepatureDate(),
                                flight.getBasePrice()
                        };
                    }

                    resultTable.setModel(new DefaultTableModel(data, columns));

                    resultTable.getSelectionModel().addListSelectionListener(e1 -> {
                        int selectedRow = resultTable.getSelectedRow();
                        if (selectedRow != -1) {
                            // Lấy thông tin chuyến bay đã chọn
                            FlightInfo selectedFlight = flights.get(selectedRow);

                            int quantity = (Integer) quantitySpinner.getValue();

                            new SelectFlight(selectedFlight, quantity, seatService).setVisible(true);
                        }
                    });

                } catch (LogicException e) {
                    JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi tìm kiếm chuyến bay: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sân bay với tên đã nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private int calculateMatchScore(FlightInfo flight, Long fromAirportId, Long toAirportId, Date departureTime) {
        int score = 0;

        if (flight.getFromAirportId().equals(fromAirportId)) {
            score += 10; 
        }

        if (flight.getToAirportId().equals(toAirportId)) {
            score += 10;
        }

        if (flight.getDepatureDate().equals(departureTime)) {
            score += 15; 
        } else {
            long diffInMillies = Math.abs(flight.getDepatureDate().getTime() - departureTime.getTime());
            long diffDays = diffInMillies / (1000 * 60 * 60 * 24);
            if (diffDays <= 7) { // Nếu trong vòng 1 tuần
                score += 5; 
            }
        }

        return score;
    }

    private Date parseDate(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ. Vui lòng nhập lại.");
            return null;
        }
    }

    private Long getAirportIdByName(String airportName) {
        if ("Sân bay Nội Bài".equalsIgnoreCase(airportName)) {
            return 1L;  
        } else if ("Sân bay Tân Sơn Nhất".equalsIgnoreCase(airportName)) {
            return 2L;  
        }
        return null;
    }

    public static void main(String[] args) {
        // Khởi tạo Spring ApplicationContext
        org.springframework.context.ApplicationContext context =
                new org.springframework.context.annotation.AnnotationConfigApplicationContext("com.group5.flight.booking");

        FlightService flightService = context.getBean(FlightService.class);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FindFlightBooking(flightService).setVisible(true);
            }
        });
    }
}
