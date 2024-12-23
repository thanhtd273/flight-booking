package com.group5.flight.booking.form.component;

import javax.swing.*;
import java.awt.*;

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
        add(lblTitle, "span, center, gapbottom 20");

        JLabel lblPaymentInfo = new JLabel("Please make your payment using the details below:");
        lblPaymentInfo.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 16));
        lblPaymentInfo.setForeground(new Color(100, 100, 100));
        add(lblPaymentInfo, "span, center, gapbottom 20");

        JLabel lblAccountNumber = createLabel("Account Number: 123-456-789");
        JLabel lblBankInfo = createLabel("Bank: ABC International Bank");

        add(lblAccountNumber, "span, center");
        add(lblBankInfo, "span, center, gapbottom 30");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new MigLayout("wrap", "[grow]10[grow]"));
        buttonPanel.setOpaque(false);

        JButton btnBack = new JButton("Back");
        btnBack.setBackground(new Color(7, 164, 121));
        btnBack.setForeground(Color.WHITE);
        btnBack.addActionListener(e -> cardLayout.show(mainPanel, Constants.FLIGHT_SEAT_SCREEN));
        buttonPanel.add(btnBack, "w 100!, h 40!");

        JButton btnDone = new JButton("Done");
        btnDone.setBackground(new Color(7, 164, 121));
        btnDone.setForeground(Color.WHITE);
        btnDone.addActionListener(e -> cardLayout.show(mainPanel, Constants.FLIGHT_SEARCHER_SCREEN));
        buttonPanel.add(btnDone, "w 100!, h 40!");

        add(buttonPanel, "span, center");
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 16));
        label.setForeground(new Color(100, 100, 100));
        return label;
    }
}
