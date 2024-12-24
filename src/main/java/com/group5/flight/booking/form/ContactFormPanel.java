package com.group5.flight.booking.form;

import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dto.UserInfo;
import com.group5.flight.booking.form.swing.Button;
import com.group5.flight.booking.form.swing.ButtonOutLine;
import com.group5.flight.booking.form.swing.MyPasswordField;
import com.group5.flight.booking.form.swing.MyTextField;
import com.group5.flight.booking.model.User;
import com.group5.flight.booking.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static com.group5.flight.booking.core.Constants.*;

public class ContactFormPanel extends JPanel {
    private static final int PANEL_WIDTH = 900;
    private static final int PANEL_HEIGHT = 600;

    // Size panel
    int leftPanelWidth = (int) (PANEL_WIDTH * 0.6);
    int rightPanelWidth = (int) (PANEL_WIDTH * 0.4);

    public ContactFormPanel() {
        initComponent();
        setOpaque(false);
    }

    private void initComponent() {
        setLayout(null);

        JPanel contactInfoPanel = new JPanel();
        contactInfoPanel.setBackground(Color.WHITE);
        contactInfoPanel.setBounds(0, 0, PANEL_WIDTH, PANEL_HEIGHT / 2);
        contactInfoPanel.setLayout(null);

        JLabel lblSignIn = new JLabel("Contact Information");
        lblSignIn.setFont(new Font("sanserif", Font.BOLD, 24));
        lblSignIn.setForeground(new Color(7, 164, 121));
        lblSignIn.setHorizontalAlignment(SwingConstants.CENTER);
        lblSignIn.setVerticalAlignment(SwingConstants.CENTER);
        lblSignIn.setBounds(0, 140, rightPanelWidth, 40);
        contactInfoPanel.add(lblSignIn);

        MyTextField firstNameField = new MyTextField();
        firstNameField.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/mail.png"))));
        firstNameField.setBounds((rightPanelWidth-300)/2, 200, 300, 40);
        firstNameField.setHint("Firstname");
        contactInfoPanel.add(firstNameField);

        MyTextField lastNameContactField = new MyTextField();
        lastNameContactField.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/pass.png"))));
        lastNameContactField.setBounds((rightPanelWidth-300)/2, 250, 300, 40);
        lastNameContactField.setHint("Lastname");
        contactInfoPanel.add(lastNameContactField);

        MyTextField phoneContactPanel = new MyTextField();
        phoneContactPanel.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/pass.png"))));
        phoneContactPanel.setBounds((rightPanelWidth-300)/2, 300, 300, 40);
        phoneContactPanel.setHint("Phone Number");
        contactInfoPanel.add(phoneContactPanel);
        
        MyTextField emailContactPanel = new MyTextField();
        emailContactPanel.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/pass.png"))));
        emailContactPanel.setBounds((rightPanelWidth-300)/2, 350, 300, 40);
        emailContactPanel.setHint("Email");
        contactInfoPanel.add(emailContactPanel);

        // Bottom panel
        JPanel passengerInfoPanel = new JPanel();
        passengerInfoPanel.setLayout(null);
        passengerInfoPanel.setBackground(Color.WHITE);
        passengerInfoPanel.setBounds(0, PANEL_HEIGHT / 2, PANEL_WIDTH, PANEL_HEIGHT / 2);

        JLabel lblCreateAccount = new JLabel("Passenger Information");
        lblCreateAccount.setFont(new Font(FONT_NAME, Font.BOLD, 24));
        lblCreateAccount.setForeground(new Color(34, 177, 76));
        lblCreateAccount.setHorizontalAlignment(SwingConstants.LEFT);
        lblCreateAccount.setVerticalAlignment(SwingConstants.CENTER);
        lblCreateAccount.setBounds(0, 135, leftPanelWidth, 40);
        passengerInfoPanel.add(lblCreateAccount);

        MyTextField txtName = new MyTextField();
        txtName.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/user.png"))));
        txtName.setBounds((leftPanelWidth-300)/2, 195, 300, 40);
        txtName.setHint("Name");
        passengerInfoPanel.add(txtName);

        MyTextField txtEmail = new MyTextField();
        txtEmail.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/mail.png"))));
        txtEmail.setBounds((leftPanelWidth-300)/2, 245, 300, 40);
        txtEmail.setHint("Email");
        passengerInfoPanel.add(txtEmail);

        MyPasswordField txtPassword = new MyPasswordField();
        txtPassword.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/pass.png"))));
        txtPassword.setBounds((leftPanelWidth-300)/2, 295, 300, 40);
        txtPassword.setHint("Password");
        passengerInfoPanel.add(txtPassword);

        MyPasswordField confirmPasswordField = new MyPasswordField();
        //confirmPasswordField.setBorder(BorderFactory.createTitledBorder("Confirm Password"));
        confirmPasswordField.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/pass.png"))));
        confirmPasswordField.setBounds((leftPanelWidth-300)/2, 345, 300, 40);
        confirmPasswordField.setHint("Confirm Password");
        passengerInfoPanel.add(confirmPasswordField);

        Button btnSignUp = new Button();
        btnSignUp.setText("SIGN UP");
        btnSignUp.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        btnSignUp.setForeground(Color.WHITE);
        btnSignUp.setBackground(new Color(34, 177, 76));
        btnSignUp.setBounds((leftPanelWidth-200)/2, 405, 200, 40);
        btnSignUp.setFocusPainted(false);
        btnSignUp.setBorderPainted(false);
        passengerInfoPanel.add(btnSignUp);

        add(passengerInfoPanel);
        add(contactInfoPanel);
    }
}
