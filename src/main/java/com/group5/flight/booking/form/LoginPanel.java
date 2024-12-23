package com.group5.flight.booking.form;

import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dto.LoginSessionInfo;
import com.group5.flight.booking.form.swing.Button;
import com.group5.flight.booking.form.swing.ButtonOutLine;
import com.group5.flight.booking.form.swing.MyPasswordField;
import com.group5.flight.booking.form.swing.MyTextField;
import com.group5.flight.booking.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static com.group5.flight.booking.core.Constants.*;

public class LoginPanel extends JPanel{

    private static final Logger logger = LoggerFactory.getLogger(LoginPanel.class);

    private final JPanel mainPanel;

    private final CardLayout cardLayout;

    private final AuthService authService;

    private javax.swing.JPanel login;

    public LoginPanel(JPanel mainPanel, CardLayout cardLayout, AuthService authService) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.authService = authService;
        intComponent();
        setOpaque(false);
    }
    private void intComponent() {
        setLayout(null);

        // Size frame
        int panelWidth = 900;
        int panelHeight = 600;

        // Size panel
        int leftPanelWidth = (int) (panelWidth * 0.4);
        int rightPanelWidth = (int) (panelWidth * 0.6);

        // Left panel
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(34, 177, 76));
        leftPanel.setBounds(0, 0, leftPanelWidth, panelHeight);
        leftPanel.setLayout(null);

        // Label Welcome Back
        JLabel lblWelcome = new JLabel("Welcome Back!");
        lblWelcome.setFont(new Font("sanserif", Font.BOLD, 30));
        lblWelcome.setForeground(new Color(245, 245, 245));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setVerticalAlignment(SwingConstants.CENTER);
        lblWelcome.setBounds(0, 180, leftPanelWidth, 50);
        leftPanel.add(lblWelcome);

        // Label instruction
        JLabel lblInstruction = new JLabel("To keep connected with us please");
        lblInstruction.setFont(new Font("sanserif", Font.BOLD, 13));
        lblInstruction.setForeground(new Color(245, 245, 245));
        lblInstruction.setHorizontalAlignment(SwingConstants.CENTER);
        lblInstruction.setVerticalAlignment(SwingConstants.CENTER);
        lblInstruction.setBounds(0, 250, leftPanelWidth, 20);
        leftPanel.add(lblInstruction);

        JLabel lblInstruction1 = new JLabel("login with your personal info");
        lblInstruction1.setFont(new Font("sanserif", Font.BOLD, 13));
        lblInstruction1.setForeground(new Color(245, 245, 245));
        lblInstruction1.setHorizontalAlignment(SwingConstants.CENTER);
        lblInstruction1.setVerticalAlignment(SwingConstants.CENTER);
        lblInstruction1.setBounds(0, 278, leftPanelWidth, 20);
        leftPanel.add(lblInstruction1);

        // Button SIGN UP
        ButtonOutLine btnSignUp = new ButtonOutLine();
        btnSignUp.setText("SIGN UP");
        btnSignUp.setFont(new Font(FONT_NAME, Font.BOLD, 16));
        btnSignUp.setForeground(new Color(255, 255, 255));
        btnSignUp.setBackground(new Color(255, 255, 255));
        btnSignUp.setBounds((leftPanelWidth-200)/2, 320, 200, 40);
        btnSignUp.setFocusPainted(false);
        btnSignUp.setBorderPainted(false);
        leftPanel.add(btnSignUp);

        btnSignUp.addActionListener(e -> cardLayout.show(mainPanel, SIGNUP_SCREEN));

        // Right panel
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBounds(leftPanelWidth, 0, rightPanelWidth, panelHeight);
        rightPanel.setLayout(null);

        // Label Sign in
        JLabel lblSignIn = new JLabel("Sign In");
        lblSignIn.setFont(new Font("sanserif", Font.BOLD, 30));
        lblSignIn.setForeground(new Color(7, 164, 121));
        lblSignIn.setHorizontalAlignment(SwingConstants.CENTER);
        lblSignIn.setVerticalAlignment(SwingConstants.CENTER);
        lblSignIn.setBounds(0, 140, rightPanelWidth, 40);
        rightPanel.add(lblSignIn);

        // Text email
        MyTextField txtEmail = new MyTextField();
        txtEmail.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/mail.png"))));
        txtEmail.setBounds((rightPanelWidth-300)/2, 200, 300, 40);
        txtEmail.setHint("Email");
        rightPanel.add(txtEmail);

        // Text pass
        MyPasswordField txtPass = new MyPasswordField();
        txtPass.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/pass.png"))));
        txtPass.setBounds((rightPanelWidth-300)/2, 250, 300, 40);
        txtPass.setHint("Password");
        rightPanel.add(txtPass);

        // Button forgot pass
        JButton lblForgotPassword  = new JButton("Forgot your password ?");
        lblForgotPassword.setFont(new Font("sanserif", Font.BOLD, 12));
        lblForgotPassword.setForeground(new Color(100, 100, 100));
        lblForgotPassword.setContentAreaFilled(false);
        lblForgotPassword.setBorderPainted(false);
        lblForgotPassword.setBounds((rightPanelWidth-200)/2, 300, 200, 30);
        lblForgotPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rightPanel.add(lblForgotPassword );

        // Button signin
        com.group5.flight.booking.form.swing.Button btnSignIn = new Button();
        btnSignIn.setText("SIGN IN");
        btnSignIn.setBackground(new Color(7, 164, 121));
        btnSignIn.setForeground(Color.WHITE);
        btnSignIn.setFont(new Font(FONT_NAME, Font.BOLD, 16));
        btnSignIn.setBounds((rightPanelWidth-200)/2, 350, 200, 40);
        rightPanel.add(btnSignIn);

        btnSignIn.addActionListener(e -> {
            String email = txtEmail.getText();
            String password = new String(txtPass.getPassword());
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
        setPreferredSize(new Dimension(panelWidth, panelHeight));
    }
}
