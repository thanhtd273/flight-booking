package com.group1.flight.booking.form;

import com.group1.flight.booking.core.AppUtils;
import com.group1.flight.booking.core.Constants;
import com.group1.flight.booking.form.component.FbButton;
import com.group1.flight.booking.form.component.FbPasswordField;
import com.group1.flight.booking.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ResetPasswordPanel extends JPanel{

    private static final Logger logger = LoggerFactory.getLogger(ResetPasswordPanel.class);

    private static final int PANEL_HEIGHT = 500;

    private final JPanel mainPanel;

    private final CardLayout cardLayout;

    private final String email;

    private final UserService userService;

    public ResetPasswordPanel(JPanel mainPanel, CardLayout cardLayout, String email, UserService userService) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.email = email;
        this.userService = userService;
        initComponent();
    }

    private void initComponent() {
        setLayout(null);
        setBackground(Color.WHITE);
        int leftPanelWidth = 900;
        setBounds(0, 0, leftPanelWidth, PANEL_HEIGHT);

        JLabel lblResetPassword = new JLabel("Reset Password");
        lblResetPassword.setFont(new Font(Constants.FB_FONT, Font.BOLD, 30));
        lblResetPassword.setForeground(new Color(34, 177, 76));
        lblResetPassword.setHorizontalAlignment(SwingConstants.CENTER);
        lblResetPassword.setBounds(0, 135, leftPanelWidth, 40);
        add(lblResetPassword);

        FbPasswordField passwordField = new FbPasswordField();
        passwordField.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/pass.png"))));
        passwordField.setBounds((leftPanelWidth - 300) / 2, 195, 300, 40);
        passwordField.setHint("Enter your password");
        add(passwordField);

        FbPasswordField confirmPasswordField = new FbPasswordField();
        confirmPasswordField.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/pass.png"))));
        confirmPasswordField.setBounds((leftPanelWidth - 300) / 2, 255, 300, 40);
        confirmPasswordField.setHint("Enter your confirm password");
        add(confirmPasswordField);

        FbButton summitButton = createSummitButton(leftPanelWidth, passwordField, confirmPasswordField);
        add(summitButton);

    }

    private FbButton createSummitButton(int leftPanelWidth, FbPasswordField passwordField, FbPasswordField confirmPasswordField) {
        FbButton summitButton = new FbButton();
        summitButton.setText("Submit");
        summitButton.setFont(new Font(Constants.FB_FONT, Font.BOLD, 14));
        summitButton.setForeground(Color.WHITE);
        summitButton.setBackground(new Color(34, 177, 76));
        summitButton.setBounds((leftPanelWidth - 200) / 2, 320, 200, 40);
        summitButton.setFocusPainted(false);
        summitButton.setBorderPainted(false);
        summitButton.addActionListener(e -> summitAction(String.valueOf(passwordField.getPassword()),
                        String.valueOf(confirmPasswordField.getPassword())));
        return summitButton;
    }

    private void summitAction(String password, String confirmPassword) {
        logger.debug("Reset password of account having email = {}", email);
        try {
            userService.resetPassword(email, password, confirmPassword);
            JOptionPane.showMessageDialog(null, "Reset password success", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(mainPanel, Constants.LOGIN_SCREEN);
        } catch (Exception ex) {
            logger.error("Reset password failed, error: {}", ex.getMessage());
            AppUtils.showErrorDialog(ex.getMessage());
        }
    }
}
