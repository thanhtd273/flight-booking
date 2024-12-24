package com.group5.flight.booking.form.component;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import com.group5.flight.booking.core.Constants;
import net.miginfocom.swing.MigLayout;

public class FlightPayPanel extends JPanel {

    private final JPanel mainPanel;

    private final CardLayout cardLayout;

    public FlightPayPanel(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;

        initComponents();
    }

    private void initComponents() {
        setLayout(new MigLayout("wrap 1", "[grow]", "[top][grow][bottom]"));
        setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Payment Information");
        lblTitle.setFont(new Font(Constants.FB_FONT, Font.BOLD, 24));
        lblTitle.setForeground(new Color(7, 164, 121));
        add(lblTitle, "span, center, gap top 20");

        JLabel lblPaymentInfo = new JLabel("Please make your payment using the details below:");
        lblPaymentInfo.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 16));
        lblPaymentInfo.setForeground(new Color(50, 50, 50));
        add(lblPaymentInfo, "span, center, gap top 60");


        JLabel picQR = new JLabel();
        picQR.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 16));
        ImageIcon qr = new ImageIcon(Objects.requireNonNull(getClass().getResource("/scan.png")));
        picQR.setIcon(qr);
        add(picQR, "span, center");

        JLabel lblScanToPay = new JLabel("Scan to pay");
        lblScanToPay.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 16));
        lblScanToPay.setForeground(new Color(0, 150, 200));
        add(lblScanToPay, "span, center, gap bottom 20");


        JLabel lblAccountNumber = createLabel("Account Number: 123-456-7890");
        lblAccountNumber.setForeground(new Color(50, 50, 50));
        lblAccountNumber.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));

        JLabel lblBankInfo = createLabel("Bank: ABC International Bank");
        lblBankInfo.setForeground(new Color(50, 50, 50));
        lblBankInfo.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));

        add(lblAccountNumber, "span, center");
        add(lblBankInfo, "span, center, gap bottom 10");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new MigLayout("wrap", "[grow]10[grow]"));
        buttonPanel.setOpaque(false);

        JButton btnBack = new JButton("Back");
        btnBack.setBackground(new Color(7, 164, 121));
        btnBack.setForeground(Color.WHITE);
        btnBack.addActionListener(e -> cardLayout.show(mainPanel, Constants.FLIGHT_SEAT_SCREEN));
        buttonPanel.add(btnBack, "w 100!, h 40!");

        JButton btnDone = new JButton("Done");
        btnDone.setBackground(new Color(20, 140, 180));
        btnDone.setForeground(Color.WHITE);
        btnDone.addActionListener(e -> cardLayout.show(mainPanel, Constants.FLIGHT_SEARCHER_SCREEN));
        buttonPanel.add(btnDone, "w 100!, h 40!");

        add(buttonPanel, "span, center, gap bottom 20");
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 16));
        label.setForeground(new Color(100, 100, 100));
        return label;
    }
}
