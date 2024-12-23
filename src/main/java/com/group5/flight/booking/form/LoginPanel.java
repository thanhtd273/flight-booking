package com.group5.flight.booking.form;

import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dto.LoginSessionInfo;
import com.group5.flight.booking.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

import static com.group5.flight.booking.core.Constants.*;

public class LoginPanel extends JPanel{

    private static final Logger logger = LoggerFactory.getLogger(LoginPanel.class);

    private final JPanel mainPanel;

    private final CardLayout cardLayout;

    private final AuthService authService;

    public LoginPanel(JPanel mainPanel, CardLayout cardLayout, AuthService authService) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.authService = authService;
        intComponent();
        setOpaque(false);
    }
    private void intComponent() {
        setLayout(null);

        // Left panel
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(34, 177, 76));
        leftPanel.setBounds(0, 0, 500, 600);
        leftPanel.setLayout(null);

        JLabel lblHello = new JLabel("Hello, Friend!");
        lblHello.setFont(new Font(FONT_NAME, Font.BOLD, 28));
        lblHello.setForeground(Color.WHITE);
        lblHello.setBounds(150, 150, 300, 50);
        leftPanel.add(lblHello);

        JLabel lblDetails = new JLabel("Enter your personal details");
        lblDetails.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
        lblDetails.setForeground(Color.WHITE);
        lblDetails.setBounds(130, 200, 300, 20);
        leftPanel.add(lblDetails);

        JLabel lblJourney = new JLabel("and start journey with us");
        lblJourney.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
        lblJourney.setForeground(Color.WHITE);
        lblJourney.setBounds(130, 220, 300, 20);
        leftPanel.add(lblJourney);

        JButton btnSignUp = new JButton("SIGN UP");
        btnSignUp.setFont(new Font(FONT_NAME, Font.BOLD, 16));
        btnSignUp.setForeground(new Color(34, 177, 76));
        btnSignUp.setBackground(Color.WHITE);
        btnSignUp.setBounds(180, 300, 140, 40);
        btnSignUp.setFocusPainted(false);
        btnSignUp.setBorder(BorderFactory.createLineBorder(new Color(34, 177, 76), 2));
        btnSignUp.addActionListener(e -> cardLayout.show(mainPanel, SIGNUP_SCREEN));
        leftPanel.add(btnSignUp);

        // Right panel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBounds(500, 0, 500, 600);

        JLabel lblSignIn = new JLabel("Sign In");
        lblSignIn.setFont(new Font(FONT_NAME, Font.BOLD, 28));
        lblSignIn.setForeground(new Color(34, 177, 76));
        lblSignIn.setBounds(200, 50, 150, 50);
        rightPanel.add(lblSignIn);

        JTextField txtEmail = new JTextField();
        txtEmail.setBorder(BorderFactory.createTitledBorder("Email"));
        txtEmail.setBounds(150, 150, 200, 50);
        rightPanel.add(txtEmail);

        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setBorder(BorderFactory.createTitledBorder("Password"));
        txtPassword.setBounds(150, 220, 200, 50);
        rightPanel.add(txtPassword);

        JLabel lblForgotPassword = new JLabel("Forgot your password ?");
        lblForgotPassword.setFont(new Font(FONT_NAME, Font.PLAIN, 12));
        lblForgotPassword.setForeground(new Color(34, 177, 76));
        lblForgotPassword.setBounds(190, 280, 150, 20);
        rightPanel.add(lblForgotPassword);

        JButton btnSignIn = new JButton("SIGN IN");
        btnSignIn.setFont(new Font(FONT_NAME, Font.BOLD, 16));
        btnSignIn.setForeground(Color.WHITE);
        btnSignIn.setBackground(new Color(34, 177, 76));
        btnSignIn.setBounds(180, 320, 140, 40);
        btnSignIn.setFocusPainted(false);
        btnSignIn.setBorderPainted(false);
        rightPanel.add(btnSignIn);

        btnSignIn.addActionListener(e -> {
            String email = txtEmail.getText();
            String password = new String(txtPassword.getPassword());

            try {
                LoginSessionInfo loginSessionInfo = authService.login(email, password);
                logger.debug("Login successfully with returned info: {}", loginSessionInfo);
                cardLayout.show(mainPanel, FLIGHT_SEARCHER_SCREEN);
            } catch (LogicException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(leftPanel);
        add(rightPanel);
    }
}
