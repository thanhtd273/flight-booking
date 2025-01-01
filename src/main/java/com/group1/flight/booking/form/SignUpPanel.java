package com.group1.flight.booking.form;

import com.group1.flight.booking.core.exception.LogicException;
import com.group1.flight.booking.dto.UserInfo;
import com.group1.flight.booking.form.swing.Button;
import com.group1.flight.booking.form.swing.ButtonOutLine;
import com.group1.flight.booking.form.swing.MyPasswordField;
import com.group1.flight.booking.form.swing.MyTextField;
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

        MyTextField txtName = new MyTextField();
        txtName.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/user.png"))));
        txtName.setBounds((leftPanelWidth-300)/2, 195, 300, 40);
        txtName.setHint("Name");
        leftPanel.add(txtName);

        MyTextField txtEmail = new MyTextField();
        txtEmail.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/mail.png"))));
        txtEmail.setBounds((leftPanelWidth-300)/2, 245, 300, 40);
        txtEmail.setHint("Email");
        leftPanel.add(txtEmail);

        MyPasswordField txtPassword = new MyPasswordField();
        txtPassword.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/pass.png"))));
        txtPassword.setBounds((leftPanelWidth-300)/2, 295, 300, 40);
        txtPassword.setHint("Password");
        leftPanel.add(txtPassword);

        MyPasswordField confirmPasswordField = new MyPasswordField();
        //confirmPasswordField.setBorder(BorderFactory.createTitledBorder("Confirm Password"));
        confirmPasswordField.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/pass.png"))));
        confirmPasswordField.setBounds((leftPanelWidth-300)/2, 345, 300, 40);
        confirmPasswordField.setHint("Confirm Password");
        leftPanel.add(confirmPasswordField);

        Button btnSignUp = new Button();
        btnSignUp.setText("SIGN UP");
        btnSignUp.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        btnSignUp.setForeground(Color.WHITE);
        btnSignUp.setBackground(new Color(34, 177, 76));
        btnSignUp.setBounds((leftPanelWidth-200)/2, 405, 200, 40);
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
                activateAccountPanel.setEmail(txtEmail.getText());
                cardLayout.show(mainPanel, ACCOUNT_ACTIVATOR);
            } catch (LogicException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Sign Up Fail", JOptionPane.ERROR_MESSAGE);
            }
        });
        leftPanel.add(btnSignUp);

        confirmPasswordField.addActionListener(e -> performSignUp(txtName, txtEmail, txtPassword, confirmPasswordField));

        add(leftPanel);
        add(rightPanel);
    }

    private void performSignUp(MyTextField txtName, MyTextField txtEmail, MyPasswordField txtPassword, MyPasswordField confirmPasswordField) {
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(txtEmail.getText());
        userInfo.setPassword(String.valueOf(txtPassword.getPassword()));
        userInfo.setConfirmPassword(String.valueOf(confirmPasswordField.getPassword()));
        try {
            User user = authService.signUp(userInfo);
            logger.debug("Create user successfully, user = {}", user);
            activateAccountPanel.setEmail(txtEmail.getText());
            cardLayout.show(mainPanel, ACCOUNT_ACTIVATOR);
        } catch (LogicException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Sign Up Fail", JOptionPane.ERROR_MESSAGE);
        }
    }
}
