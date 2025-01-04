package com.group1.flight.booking.form;

import com.group1.flight.booking.dto.LoginSessionInfo;
import com.group1.flight.booking.form.component.FbButton;
import com.group1.flight.booking.form.component.ButtonOutLine;
import com.group1.flight.booking.form.component.FbPasswordField;
import com.group1.flight.booking.form.component.FbTextField;
import com.group1.flight.booking.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static com.group1.flight.booking.core.Constants.*;

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

        int panelWidth = 900;
        int panelHeight = 600;

        int leftPanelWidth = (int) (panelWidth * 0.4);
        int rightPanelWidth = (int) (panelWidth * 0.6);

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(34, 177, 76));
        leftPanel.setBounds(0, 0, leftPanelWidth, panelHeight);
        leftPanel.setLayout(null);

        JLabel lblWelcome = new JLabel("Welcome Back!");
        lblWelcome.setFont(new Font(FB_FONT, Font.BOLD, 30));
        lblWelcome.setForeground(new Color(245, 245, 245));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setVerticalAlignment(SwingConstants.CENTER);
        lblWelcome.setBounds(0, 180, leftPanelWidth, 50);
        leftPanel.add(lblWelcome);

        JLabel lblInstruction = new JLabel("To keep connected with us please");
        lblInstruction.setFont(new Font(FB_FONT, Font.BOLD, 13));
        lblInstruction.setForeground(new Color(245, 245, 245));
        lblInstruction.setHorizontalAlignment(SwingConstants.CENTER);
        lblInstruction.setVerticalAlignment(SwingConstants.CENTER);
        lblInstruction.setBounds(0, 250, leftPanelWidth, 20);
        leftPanel.add(lblInstruction);

        JLabel lblInstruction1 = new JLabel("login with your personal info");
        lblInstruction1.setFont(new Font(FB_FONT, Font.BOLD, 13));
        lblInstruction1.setForeground(new Color(245, 245, 245));
        lblInstruction1.setHorizontalAlignment(SwingConstants.CENTER);
        lblInstruction1.setVerticalAlignment(SwingConstants.CENTER);
        lblInstruction1.setBounds(0, 278, leftPanelWidth, 20);
        leftPanel.add(lblInstruction1);

        ButtonOutLine btnSignUp = new ButtonOutLine();
        btnSignUp.setText("SIGN UP");
        btnSignUp.setFont(new Font(FONT_NAME, Font.BOLD, 13));
        btnSignUp.setForeground(new Color(255, 255, 255));
        btnSignUp.setBackground(new Color(255, 255, 255));
        btnSignUp.setBounds((leftPanelWidth-200)/2, 320, 200, 40);
        btnSignUp.setFocusPainted(false);
        btnSignUp.setBorderPainted(false);
        leftPanel.add(btnSignUp);

        btnSignUp.addActionListener(e -> cardLayout.show(mainPanel, SIGNUP_SCREEN));

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBounds(leftPanelWidth, 0, rightPanelWidth, panelHeight);
        rightPanel.setLayout(null);

        JLabel lblSignIn = new JLabel("Sign In");
        lblSignIn.setFont(new Font(FB_FONT, Font.BOLD, 30));
        lblSignIn.setForeground(new Color(34, 177, 76));
        lblSignIn.setHorizontalAlignment(SwingConstants.CENTER);
        lblSignIn.setVerticalAlignment(SwingConstants.CENTER);
        lblSignIn.setBounds(0, 140, rightPanelWidth, 40);
        rightPanel.add(lblSignIn);

        FbTextField emailField = new FbTextField();
        emailField.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/mail.png"))));
        emailField.setBounds((rightPanelWidth-300)/2, 200, 300, 40);
        emailField.setHint("Email");
        rightPanel.add(emailField);

        FbPasswordField passwordField = new FbPasswordField();
        passwordField.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/pass.png"))));
        passwordField.setBounds((rightPanelWidth-300)/2, 250, 300, 40);
        passwordField.setHint("Password");
        rightPanel.add(passwordField);

        JButton lblForgotPassword = createForgotPasswordBtn(rightPanelWidth);
        rightPanel.add(lblForgotPassword );

        FbButton btnSignIn = new FbButton();
        btnSignIn.setText("SIGN IN");
        btnSignIn.setBackground(new Color(34, 177, 76));
        btnSignIn.setForeground(Color.WHITE);
        btnSignIn.setFont(new Font(FONT_NAME, Font.BOLD, 13));
        btnSignIn.setBounds((rightPanelWidth-200)/2, 350, 200, 40);
        btnSignIn.addActionListener(e -> loginAction(emailField.getText(), String.valueOf(passwordField.getPassword())));
        rightPanel.add(btnSignIn);

        add(leftPanel);
        add(rightPanel);
        setPreferredSize(new Dimension(panelWidth, panelHeight));
    }

    private JButton createForgotPasswordBtn(int rightPanelWidth) {
        JButton lblForgotPassword  = new JButton("Forgot your password ?");
        lblForgotPassword.setFont(new Font(FB_FONT, Font.BOLD, 12));
        lblForgotPassword.setForeground(new Color(100, 100, 100));
        lblForgotPassword.setContentAreaFilled(false);
        lblForgotPassword.setBorderPainted(false);
        lblForgotPassword.setBounds((rightPanelWidth -200)/2, 300, 200, 30);
        lblForgotPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblForgotPassword.addActionListener(e -> cardLayout.show(mainPanel, FORGOT_PASSWORD_SCREEN));
        return lblForgotPassword;
    }
    
    private void loginAction(String email, String password) {
        try {
            LoginSessionInfo loginSessionInfo = authService.login(email, password);
            logger.debug("Login successfully with returned info: {}", loginSessionInfo);
            cardLayout.show(mainPanel, FLIGHT_SEARCHER_SCREEN);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
