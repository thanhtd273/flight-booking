package com.group1.flight.booking.form;

import com.group1.flight.booking.form.swing.Button;
import com.group1.flight.booking.form.swing.MyTextField;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ForgotPasswordPanel extends JPanel {

    private static final String FONT_NAME = "Arial";
    private static final int PANEL_HEIGHT = 500;
    private final int leftPanelWidth;

    public ForgotPasswordPanel(int leftPanelWidth) {
        this.leftPanelWidth = leftPanelWidth;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(null);
        setBackground(Color.WHITE);
        setBounds(0, 0, leftPanelWidth, PANEL_HEIGHT);

        JLabel lblForgotPassword = new JLabel("Forgot Password");
        lblForgotPassword.setFont(new Font(FONT_NAME, Font.BOLD, 30));
        lblForgotPassword.setForeground(new Color(34, 177, 76));
        lblForgotPassword.setHorizontalAlignment(SwingConstants.CENTER);
        lblForgotPassword.setBounds(0, 135, leftPanelWidth, 40);
        add(lblForgotPassword);

        MyTextField txtEmail = new MyTextField();
        txtEmail.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/mail.png"))));
        txtEmail.setBounds((leftPanelWidth - 300) / 2, 195, 300, 40);
        txtEmail.setHint("Enter your email");
        add(txtEmail);

        Button btnSendResetLink = new Button();
        btnSendResetLink.setText("SEND RESET LINK");
        btnSendResetLink.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        btnSendResetLink.setForeground(Color.WHITE);
        btnSendResetLink.setBackground(new Color(34, 177, 76));
        btnSendResetLink.setBounds((leftPanelWidth - 200) / 2, 255, 200, 40);
        btnSendResetLink.setFocusPainted(false);
        btnSendResetLink.setBorderPainted(false);
        add(btnSendResetLink);

        JLabel lblBackToLogin = new JLabel("Back to Login");
        lblBackToLogin.setFont(new Font(FONT_NAME, Font.PLAIN, 14));
        lblBackToLogin.setForeground(new Color(34, 177, 76));
        lblBackToLogin.setHorizontalAlignment(SwingConstants.CENTER);
        lblBackToLogin.setBounds(0, 305, leftPanelWidth, 20);
        add(lblBackToLogin);
    }
}
