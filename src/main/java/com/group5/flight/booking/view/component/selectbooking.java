package com.group5.flight.booking.view.component;

import javax.swing.*;
import java.awt.*;

public class selectbooking extends JFrame {

    private JLabel paymentStatusLabel;
    private JLabel totalAmountLabel;
    private JButton payButton;
    private JButton backButton;

    public PaymentInformation(String bankName, String cardNumber, double ticketPrice, int ticketQuantity) {
        setTitle("Thông tin thanh toán");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 2, 10, 10));

        // Fixed information about the bank
        mainPanel.add(new JLabel("Ngân hàng:"));
        mainPanel.add(new JLabel(bankName));

        mainPanel.add(new JLabel("Số thẻ:"));
        mainPanel.add(new JLabel(cardNumber));

        // Payment status
        mainPanel.add(new JLabel("Trạng thái thanh toán:"));
        paymentStatusLabel = new JLabel("Chưa thanh toán");
        mainPanel.add(paymentStatusLabel);

        // Total amount
        mainPanel.add(new JLabel("Tổng số tiền cần thanh toán:"));
        double totalAmount = ticketPrice * ticketQuantity;
        totalAmountLabel = new JLabel(String.format("%.2f VND", totalAmount));
        mainPanel.add(totalAmountLabel);

        // Buttons
        payButton = new JButton("Thanh toán");
        backButton = new JButton("Quay lại");
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(backButton);
        buttonPanel.add(payButton);

        // Add panels to frame
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners (to be implemented when integrating APIs)
        payButton.addActionListener(e -> {
            // Simulate payment success for now
            paymentStatusLabel.setText("Thanh toán thành công");
            JOptionPane.showMessageDialog(this, "Thanh toán thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });

        backButton.addActionListener(e -> {
            // Close current window and go back
            dispose();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new selectbooking("Ngân hàng ABC", "1234 5678 9012 3456", 500000, 2).setVisible(true);
        });
    }
}
