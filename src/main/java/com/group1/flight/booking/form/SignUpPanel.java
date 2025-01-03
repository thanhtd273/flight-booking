package com.group1.flight.booking.form;

import com.group1.flight.booking.dto.UserInfo;
import com.group1.flight.booking.form.component.FbButton;
import com.group1.flight.booking.form.component.ButtonOutLine;
import com.group1.flight.booking.form.component.FbPasswordField;
import com.group1.flight.booking.form.component.FbTextField;
import com.group1.flight.booking.model.User;
import com.group1.flight.booking.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static com.group1.flight.booking.core.Constants.*;

public class SignUpPanel extends JPanel {

    private static final Logger logger = LoggerFactory.getLogger(SignUpPanel.class);

    private final JPanel mainPanel;

    private final CardLayout cardLayout;

    private final ActivateAccountPanel activateAccountPanel;

    private final AuthService authService;

    private static final int PANEL_WIDTH = 900;
    private static final int PANEL_HEIGHT = 600;

    // Size panel
    int leftPanelWidth = (int) (PANEL_WIDTH * 0.6);
    int rightPanelWidth = (int) (PANEL_WIDTH * 0.4);

    public SignUpPanel(JPanel mainPanel, CardLayout cardLayout, ActivateAccountPanel activateAccountPanel, AuthService authService) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.activateAccountPanel = activateAccountPanel;
        this.authService = authService;
        initComponent();
        setOpaque(false);
    }

    private void initComponent() {
        setLayout(null);

        // Right panel
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(34, 177, 76));
        rightPanel.setBounds(leftPanelWidth, 0, rightPanelWidth, PANEL_HEIGHT);
        rightPanel.setLayout(null);

        JLabel lblWelcome = new JLabel("Hello, Friend!");
        lblWelcome.setFont(new Font(FONT_NAME, Font.BOLD, 28));
        lblWelcome.setForeground(new Color(245, 245, 245));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setVerticalAlignment(SwingConstants.CENTER);
        lblWelcome.setBounds(0, 180, rightPanelWidth, 50);
        rightPanel.add(lblWelcome);

        JLabel lblConnect = new JLabel("Enter your personal details");
        lblConnect.setFont(new Font(FONT_NAME, Font.BOLD, 13));
        lblConnect.setForeground(new Color(245, 245, 245));
        lblConnect.setHorizontalAlignment(SwingConstants.CENTER);
        lblConnect.setVerticalAlignment(SwingConstants.CENTER);
        lblConnect.setBounds(0, 250, rightPanelWidth, 20);
        rightPanel.add(lblConnect);

        JLabel lblLoginInfo = new JLabel("and start journey with us");
        lblLoginInfo.setFont(new Font(FONT_NAME, Font.BOLD, 13));
        lblLoginInfo.setForeground(new Color(245, 245, 245));
        lblLoginInfo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLoginInfo.setVerticalAlignment(SwingConstants.CENTER);
        lblLoginInfo.setBounds(0, 278, rightPanelWidth, 20);
        rightPanel.add(lblLoginInfo);

        ButtonOutLine btnSignIn = new ButtonOutLine();
        btnSignIn.setText("SIGN IN");
        btnSignIn.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        btnSignIn.setForeground(new Color(245, 245, 245));
        btnSignIn.setBackground(new Color(245, 245, 245));
        btnSignIn.setBounds((rightPanelWidth-200)/2, 320, 200, 40);
        btnSignIn.setFocusPainted(false);
        btnSignIn.setBorderPainted(false);
        rightPanel.add(btnSignIn);

        btnSignIn.addActionListener(e -> cardLayout.show(mainPanel, LOGIN_SCREEN));

        // Left panel
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBounds(0, 0, leftPanelWidth, PANEL_HEIGHT);

        JLabel lblCreateAccount = new JLabel("Create Account");
        lblCreateAccount.setFont(new Font(FONT_NAME, Font.BOLD, 30));
        lblCreateAccount.setForeground(new Color(34, 177, 76));
        lblCreateAccount.setHorizontalAlignment(SwingConstants.CENTER);
        lblCreateAccount.setVerticalAlignment(SwingConstants.CENTER);
        lblCreateAccount.setBounds(0, 135, leftPanelWidth, 40);
        leftPanel.add(lblCreateAccount);

        FbTextField txtName = new FbTextField();
        txtName.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/user.png"))));
        txtName.setBounds((leftPanelWidth-300)/2, 195, 300, 40);
        txtName.setHint("Name");
        leftPanel.add(txtName);

        FbTextField emailField = new FbTextField();
        emailField.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/mail.png"))));
        emailField.setBounds((leftPanelWidth-300)/2, 245, 300, 40);
        emailField.setHint("Email");
        leftPanel.add(emailField);

        FbPasswordField passwordField = new FbPasswordField();
        passwordField.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/pass.png"))));
        passwordField.setBounds((leftPanelWidth-300)/2, 295, 300, 40);
        passwordField.setHint("Password");
        leftPanel.add(passwordField);

        FbPasswordField confirmPasswordField = new FbPasswordField();
        confirmPasswordField.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/pass.png"))));
        confirmPasswordField.setBounds((leftPanelWidth-300)/2, 345, 300, 40);
        confirmPasswordField.setHint("Confirm Password");
        leftPanel.add(confirmPasswordField);

        FbButton btnSignUp = createSignUpBtn(emailField, passwordField, confirmPasswordField);
        leftPanel.add(btnSignUp);

        add(leftPanel);
        add(rightPanel);
    }

    private FbButton createSignUpBtn(FbTextField emailField, FbPasswordField passwordField, FbPasswordField confirmPasswordField) {
        FbButton button = new FbButton();
        button.setText("SIGN UP");
        button.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(34, 177, 76));
        button.setBounds((leftPanelWidth-200)/2, 405, 200, 40);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.addActionListener(e -> signUpAction(emailField.getText(),
                String.valueOf(passwordField.getPassword()), String.valueOf(confirmPasswordField.getPassword())));
        return button;
    }

    private void signUpAction(String email, String password, String confirmPassword) {
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(email);
        userInfo.setPassword(password);
        userInfo.setConfirmPassword(confirmPassword);
        try {
            User user = authService.signUp(userInfo);
            logger.debug("Create user successfully, user = {}", user);
            activateAccountPanel.setEmail(email);
            cardLayout.show(mainPanel, ACCOUNT_ACTIVATOR);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Sign Up Fail", JOptionPane.ERROR_MESSAGE);
        }
    }
}
