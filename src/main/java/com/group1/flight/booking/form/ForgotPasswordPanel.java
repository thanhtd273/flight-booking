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

        JButton backToLoginBtn = new JButton("Back to Login");
        backToLoginBtn.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 14));
        backToLoginBtn.setForeground(new Color(34, 177, 76));
        backToLoginBtn.setHorizontalAlignment(SwingConstants.CENTER);
        backToLoginBtn.setBounds(0, 305, leftPanelWidth, 20);
        backToLoginBtn.addActionListener(e -> cardLayout.show(mainPanel, Constants.LOGIN_SCREEN));
        add(backToLoginBtn);
    }

    private FbButton createSendOtpButton(int leftPanelWidth, FbTextField txtEmail) {
        FbButton sendOtpButton = new FbButton();
        sendOtpButton.setText("Send OTP");
        sendOtpButton.setFont(new Font(Constants.FB_FONT, Font.BOLD, 14));
        sendOtpButton.setForeground(Color.WHITE);
        sendOtpButton.setBackground(new Color(34, 177, 76));
        sendOtpButton.setBounds((leftPanelWidth - 200) / 2, 255, 200, 40);
        sendOtpButton.setFocusPainted(false);
        sendOtpButton.setBorderPainted(false);
        sendOtpButton.addActionListener(e -> sendOTPAction(txtEmail.getText()));
        return sendOtpButton;
    }

    private void sendOTPAction(String email) {
        logger.debug("Send OTP to email: {}", email);
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
}
