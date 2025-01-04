package com.group1.flight.booking.form;

import com.group1.flight.booking.core.Constants;
import com.group1.flight.booking.core.ErrorCode;
import com.group1.flight.booking.core.exception.LogicException;
import com.group1.flight.booking.form.component.FbButton;
import com.group1.flight.booking.form.component.FbTextField;
import com.group1.flight.booking.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static com.group1.flight.booking.core.Constants.LOGIN_SCREEN;

public class ForgotPasswordPanel extends JPanel {

    private static final Logger logger = LoggerFactory.getLogger(ForgotPasswordPanel.class);

    private static final int PANEL_HEIGHT = 500;

    private final JPanel mainPanel;

    private final CardLayout cardLayout;

    private final UserService userService;

    public ForgotPasswordPanel(JPanel mainPanel, CardLayout cardLayout, UserService userService) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.userService = userService;
        initComponent();
    }

    private void initComponent() {
        setLayout(null);
        setBackground(Color.WHITE);
        int leftPanelWidth = 900;
        setBounds(0, 0, leftPanelWidth, PANEL_HEIGHT);

        JLabel lblForgotPassword = new JLabel("Forgot Password");
        lblForgotPassword.setFont(new Font(Constants.FB_FONT, Font.BOLD, 30));
        lblForgotPassword.setForeground(new Color(34, 177, 76));
        lblForgotPassword.setHorizontalAlignment(SwingConstants.CENTER);
        lblForgotPassword.setBounds(0, 135, leftPanelWidth, 40);
        add(lblForgotPassword);

        FbTextField txtEmail = new FbTextField();
        txtEmail.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/mail.png"))));
        txtEmail.setBounds((leftPanelWidth - 300) / 2, 195, 300, 40);
        txtEmail.setHint("Enter your email");
        add(txtEmail);

        FbButton sendOtpButton = createSendOtpButton(leftPanelWidth, txtEmail);
        add(sendOtpButton);

        FbButton backToLoginBtn = createBackToLoginBtn(leftPanelWidth, txtEmail);
        add(backToLoginBtn);
    }

    private FbButton createBackToLoginBtn(int leftPanelWidth, FbTextField txtEmail) {
        FbButton backToLoginBtn = new FbButton();
        backToLoginBtn.setText("Back to Login");
        backToLoginBtn.setFont(new Font(Constants.FB_FONT, Font.BOLD, 14));
        backToLoginBtn.setForeground(Color.WHITE);
        backToLoginBtn.setBackground(new Color(34, 177, 76));
        backToLoginBtn.setBounds(300, 255, 130, 40);
        backToLoginBtn.setFocusPainted(false);
        backToLoginBtn.setBorderPainted(false);
        backToLoginBtn.addActionListener(e -> cardLayout.show(mainPanel, LOGIN_SCREEN));
        return backToLoginBtn;
    }

    private FbButton createSendOtpButton(int leftPanelWidth, FbTextField txtEmail) {
        FbButton sendOtpButton = new FbButton();
        sendOtpButton.setText("Send OTP");
        sendOtpButton.setFont(new Font(Constants.FB_FONT, Font.BOLD, 14));
        sendOtpButton.setForeground(Color.WHITE);
        sendOtpButton.setBackground(new Color(20, 140, 180));
        sendOtpButton.setBounds(470, 255, 130, 40);
        sendOtpButton.setFocusPainted(false);
        sendOtpButton.setBorderPainted(false);
        sendOtpButton.addActionListener(e -> sendOTPAction(txtEmail.getText()));
        return sendOtpButton;
    }

    private void sendOTPAction(String email) {
        logger.debug("Send OTP to email: {}", email);

        if (!isValidEmail(email)) {
            return;
        }

        try {
            ErrorCode errorCode = userService.forgotPassword(email);
            if (errorCode != ErrorCode.SUCCESS) {
                throw new LogicException(errorCode);
            } else {
                OTPVerifierPanel otpVerifierPanel = new OTPVerifierPanel(mainPanel, cardLayout, userService);
                otpVerifierPanel.setEmail(email);
                mainPanel.add(otpVerifierPanel, Constants.CODE_VERIFIER);
                cardLayout.show(mainPanel, Constants.CODE_VERIFIER);
            }
        } catch (Exception ex) {
            logger.error("Send OTP fail: {}", ex.getMessage());
        }
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!email.matches(emailRegex)) {
            JOptionPane.showMessageDialog(this, "Invalid email format", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}
