package com.group5.flight.booking.form;

import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dto.UserInfo;
import com.group5.flight.booking.model.User;
import com.group5.flight.booking.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

import static com.group5.flight.booking.core.Constants.*;

public class SignUpPanel extends JPanel {

    private static final Logger logger = LoggerFactory.getLogger(SignUpPanel.class);

    private final JPanel mainPanel;

    private final CardLayout cardLayout;

    private final CodeVerifierPanel codeVerifierPanel;

    private final AuthService authService;

    public SignUpPanel(JPanel mainPanel, CardLayout cardLayout, CodeVerifierPanel codeVerifierPanel, AuthService authService) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.codeVerifierPanel = codeVerifierPanel;
        this.authService = authService;
        initComponent();
        setOpaque(false);
    }

    private void initComponent() {
        setLayout(null);

        // Left panel
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(34, 177, 76));
        leftPanel.setBounds(0, 0, 500, 600);
        leftPanel.setLayout(null);

        JLabel lblWelcome = new JLabel("Welcome Back!");
        lblWelcome.setFont(new Font(FONT_NAME, Font.BOLD, 28));
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setBounds(150, 150, 300, 50);
        leftPanel.add(lblWelcome);

        JLabel lblConnect = new JLabel("To keep connected with us please");
        lblConnect.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
        lblConnect.setForeground(Color.WHITE);
        lblConnect.setBounds(130, 200, 300, 20);
        leftPanel.add(lblConnect);

        JLabel lblLoginInfo = new JLabel("login with your personal info");
        lblLoginInfo.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
        lblLoginInfo.setForeground(Color.WHITE);
        lblLoginInfo.setBounds(130, 220, 300, 20);
        leftPanel.add(lblLoginInfo);

        JButton btnSignIn = new JButton("SIGN IN");
        btnSignIn.setFont(new Font(FONT_NAME, Font.BOLD, 16));
        btnSignIn.setForeground(new Color(34, 177, 76));
        btnSignIn.setBackground(Color.WHITE);
        btnSignIn.setBounds(180, 300, 140, 40);
        btnSignIn.setFocusPainted(false);
        btnSignIn.setBorder(BorderFactory.createLineBorder(new Color(34, 177, 76), 2));
        btnSignIn.addActionListener(e -> cardLayout.show(mainPanel, LOGIN_SCREEN));
        leftPanel.add(btnSignIn);

        // Right panel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBounds(500, 0, 500, 600);

        JLabel lblCreateAccount = new JLabel("Create Account");
        lblCreateAccount.setFont(new Font(FONT_NAME, Font.BOLD, 28));
        lblCreateAccount.setForeground(new Color(34, 177, 76));
        lblCreateAccount.setBounds(170, 50, 200, 50);
        rightPanel.add(lblCreateAccount);

        JTextField txtEmail = new JTextField();
        txtEmail.setBorder(BorderFactory.createTitledBorder("Email"));
        txtEmail.setBounds(150, 220, 200, 50);
        rightPanel.add(txtEmail);

        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setBorder(BorderFactory.createTitledBorder("Password"));
        txtPassword.setBounds(150, 290, 200, 50);
        rightPanel.add(txtPassword);

        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBorder(BorderFactory.createTitledBorder("Confirm Password"));
        confirmPasswordField.setBounds(150, 360, 200, 50);
        rightPanel.add(confirmPasswordField);

        JButton btnSignUp = new JButton("SIGN UP");
        btnSignUp.setFont(new Font(FONT_NAME, Font.BOLD, 16));
        btnSignUp.setForeground(Color.WHITE);
        btnSignUp.setBackground(new Color(34, 177, 76));
        btnSignUp.setBounds(180, 440, 140, 40);
        btnSignUp.setFocusPainted(false);
        btnSignUp.setBorderPainted(false);
        btnSignUp.addActionListener(e -> {
            UserInfo userInfo = new UserInfo();
            userInfo.setEmail(txtEmail.getText());
            userInfo.setPassword(String.valueOf(txtPassword.getPassword()));
            userInfo.setConfirmPassword(String.valueOf(confirmPasswordField.getPassword()));
            try {
                User user = authService.signUp(userInfo);
                logger.debug("Create user successfully, user = {}", user);
                codeVerifierPanel.setEmail(txtEmail.getText());
                cardLayout.show(mainPanel, CODE_VERIFIER);
            } catch (LogicException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Sign Up Fail", JOptionPane.ERROR_MESSAGE);
            }
        });
        rightPanel.add(btnSignUp);

        add(leftPanel);
        add(rightPanel);
    }
}
