package com.group5.flight.booking.view.component;

import com.group5.flight.booking.service.FlightService;
import com.group5.flight.booking.dto.FlightInfo;
import com.group5.flight.booking.core.exception.LogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;

@Component  // Mark this as a Spring Bean to be autowired
public class FindFlightBooking extends JFrame {

    private JTextField fromField, toField, timeField;
    private JSpinner quantitySpinner;
    private JButton searchButton;
    private JTable resultTable;
    private FlightService flightService;  // Service để tìm chuyến bay

    @Autowired
    public FindFlightBooking(FlightService flightService) {
        // Thiết lập JFrame
        setTitle("Flight Booking");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Khởi tạo FlightService
        this.flightService = flightService;

        // Tạo các thành phần giao diện
        fromField = new JTextField(20);
        toField = new JTextField(20);
        timeField = new JTextField(20);
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        searchButton = new JButton("Tìm kiếm");

        // Tạo bảng để hiển thị kết quả
        resultTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(resultTable);

        // Đặt Layout
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

        // Thêm vào JFrame
        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Hành động khi nút tìm kiếm được nhấn
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchFlights();
            }
        });
    }

    private void searchFlights() {
        String from = fromField.getText();  // Điểm đi
        String to = toField.getText();      // Điểm đến
        String timeStr = timeField.getText();  // Giờ bay

        // Chuyển đổi chuỗi thời gian thành đối tượng Date
        Date time = parseDate(timeStr);

        if (time != null) {
            // Lấy ID của các sân bay từ tên người dùng nhập vào
            Long fromAirportId = getAirportIdByName(from);
            Long toAirportId = getAirportIdByName(to);

            if (fromAirportId != null && toAirportId != null) {
                // Gọi service để tìm kiếm chuyến bay
                try {
                    // Sử dụng FlightService để tìm kiếm chuyến bay
                    List<FlightInfo> flights = flightService.findFlight(fromAirportId, toAirportId, time);

                    // Hiển thị kết quả trong bảng
                    String[] columns = {"Mã chuyến bay", "Điểm bắt đầu", "Điểm đến", "Giờ bay", "Giá"};
                    Object[][] data = new Object[flights.size()][5];
                    for (int i = 0; i < flights.size(); i++) {
                        FlightInfo flight = flights.get(i);

                        // Truy cập trực tiếp các thuộc tính hợp lệ trong FlightInfo
                        data[i] = new Object[]{
                                flight.getPlaneId(),
                                flight.getFromAirport().getAirportName(),
                                flight.getToAirport().getAirportName(),
                                flight.getDepatureDate(),
                                flight.getBasePrice()
                        };
                    }

                    resultTable.setModel(new DefaultTableModel(data, columns));
                } catch (LogicException e) {
                    // Xử lý ngoại lệ LogicException
                    JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi tìm kiếm chuyến bay: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sân bay với tên đã nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
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

    // Phương thức giả lập để tìm ID của sân bay (có thể gọi service hoặc tìm trong cơ sở dữ liệu)
    private Long getAirportIdByName(String airportName) {
        // Tìm kiếm trong cơ sở dữ liệu hoặc sử dụng danh sách đã có
        // Ví dụ đơn giản: tìm ID của sân bay theo tên
        if ("Sân bay Nội Bài".equalsIgnoreCase(airportName)) {
            return 1L;  // ID giả định
        } else if ("Sân bay Tân Sơn Nhất".equalsIgnoreCase(airportName)) {
            return 2L;  // ID giả định
        }
        // Trả về null hoặc ném lỗi nếu không tìm thấy
        return null;
    }

    public static void main(String[] args) {
        // Khởi tạo Spring ApplicationContext
        org.springframework.context.ApplicationContext context =
                new org.springframework.context.annotation.AnnotationConfigApplicationContext("com.group5.flight.booking");

        // Lấy FlightService từ Spring context
        FlightService flightService = context.getBean(FlightService.class);

        // Giả sử FlightService được khởi tạo và truyền vào UI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FindFlightBooking(flightService).setVisible(true);
            }
        });
    }
}
