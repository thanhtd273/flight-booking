package com.group1.flight.booking.form;

import com.group1.flight.booking.core.AppUtils;
import com.group1.flight.booking.core.Constants;
import com.group1.flight.booking.form.swing.Button;
import com.group1.flight.booking.form.swing.MyPasswordField;
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

        MyPasswordField passwordField = new MyPasswordField();
        passwordField.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/pass.png"))));
        passwordField.setBounds((leftPanelWidth - 300) / 2, 195, 300, 40);
        passwordField.setHint("Enter your password");
        add(passwordField);

        MyPasswordField confirmPasswordField = new MyPasswordField();
        confirmPasswordField.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/pass.png"))));
        confirmPasswordField.setBounds((leftPanelWidth - 300) / 2, 255, 300, 40);
        confirmPasswordField.setHint("Enter your confirm password");
        add(confirmPasswordField);

        Button summitButton = createSummitButton(leftPanelWidth, passwordField, confirmPasswordField);
        add(summitButton);

    }

    private Button createSummitButton(int leftPanelWidth, MyPasswordField passwordField, MyPasswordField confirmPasswordField) {
        Button summitButton = new Button();
        summitButton.setText("Summit");
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
