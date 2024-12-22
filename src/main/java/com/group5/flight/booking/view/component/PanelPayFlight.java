package com.group5.flight.booking.view.component;

import javax.swing.*;
import java.awt.*;
import net.miginfocom.swing.MigLayout;

public class PanelPayFlight extends JPanel {

    private JLabel lblPaymentInfo;    // Label to display payment information
    private JLabel lblAccountNumber;  // Account number label
    private JLabel lblBankInfo;       // Bank information label
    private JButton btnBack;          // "Back" button
    private JButton btnDone;          // "Done" button

    public PanelPayFlight() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new MigLayout("wrap 1", "[grow]", "[top][grow][bottom]"));
        setBackground(Color.WHITE);

        // Title
        JLabel lblTitle = new JLabel("Payment Information");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitle.setForeground(new Color(7, 164, 121));
        add(lblTitle, "span, center, gapbottom 20");

        // Payment details labels
        lblPaymentInfo = new JLabel("Please make your payment using the details below:");
        lblPaymentInfo.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblPaymentInfo.setForeground(new Color(100, 100, 100));
        add(lblPaymentInfo, "span, center, gapbottom 20");

        lblAccountNumber = createLabel("Account Number: 123-456-789");
        lblBankInfo = createLabel("Bank: ABC International Bank");

        // Add payment info labels to the panel
        add(lblAccountNumber, "span, center");
        add(lblBankInfo, "span, center, gapbottom 30");

        // Buttons (Back & Done)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new MigLayout("wrap", "[grow]10[grow]"));
        buttonPanel.setOpaque(false);

        btnBack = new JButton("Back");
        btnBack.setBackground(new Color(7, 164, 121));
        btnBack.setForeground(Color.WHITE);
        buttonPanel.add(btnBack, "w 100!, h 40!");

        btnDone = new JButton("Done");
        btnDone.setBackground(new Color(7, 164, 121));
        btnDone.setForeground(Color.WHITE);
        buttonPanel.add(btnDone, "w 100!, h 40!");

        add(buttonPanel, "span, center");
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 16));
        label.setForeground(new Color(100, 100, 100));
        return label;
    }

    // Getters for buttons (to add event listeners later)
    public JButton getBtnBack() {
        return btnBack;
    }

    public JButton getBtnDone() {
        return btnDone;
    }
}
