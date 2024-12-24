package com.group5.flight.booking.form;

import com.group5.flight.booking.core.Constants;
import com.group5.flight.booking.form.component.RoundedBorder;
import com.group5.flight.booking.form.swing.MyTextField;

import javax.swing.*;
import java.awt.*;

public class TestPanel {
    public static void main(String[] args) {
        // Tạo frame chính
        JFrame frame = new JFrame("Thông tin liên hệ");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(900, 600);

        JPanel contactInfoPanel = new JPanel();
        contactInfoPanel.setLayout(new GridBagLayout());
        contactInfoPanel.setBackground(Color.WHITE);
        contactInfoPanel.setBorder(new RoundedBorder(15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Contact Information");
        titleLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contactInfoPanel.add(titleLabel, gbc);

        JLabel firstnameContactLabel = new JLabel("Firstname");
        firstnameContactLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        contactInfoPanel.add(firstnameContactLabel, gbc);

        MyTextField firstnameContactField = new MyTextField();
        firstnameContactField.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        firstnameContactField.setPreferredSize(new Dimension(300, 50));
        gbc.gridx = 0;
        gbc.gridy = 2;
        contactInfoPanel.add(firstnameContactField, gbc);

        // Tên Đệm & Tên
        JLabel lastnameContactLabel = new JLabel("Lastname");
        lastnameContactLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        gbc.gridx = 1;
        gbc.gridy = 1;
        contactInfoPanel.add(lastnameContactLabel, gbc);

        MyTextField lastnameContactField = new MyTextField();
        lastnameContactField.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        lastnameContactField.setPreferredSize(new Dimension(300, 50));
        gbc.gridx = 1;
        gbc.gridy = 2;
        contactInfoPanel.add(lastnameContactField, gbc);

        JLabel phoneContactLabel = new JLabel("Phone Number");
        phoneContactLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        contactInfoPanel.add(phoneContactLabel, gbc);

        MyTextField phoneContactField = new MyTextField();
        phoneContactField.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        phoneContactField.setPreferredSize(new Dimension(300, 50));
        gbc.gridx = 0;
        gbc.gridy = 4;
        contactInfoPanel.add(phoneContactField, gbc);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        gbc.gridx = 1;
        gbc.gridy = 3;
        contactInfoPanel.add(emailLabel, gbc);

        MyTextField emailContactField = new MyTextField();
        emailContactField.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        emailContactField.setPreferredSize(new Dimension(300, 50));
        gbc.gridx = 1;
        gbc.gridy = 4;
        contactInfoPanel.add(emailContactField, gbc);

        JPanel passengerInfoPanel = new JPanel();
        passengerInfoPanel.setLayout(new GridBagLayout());
        passengerInfoPanel.setBackground(Color.WHITE);
        passengerInfoPanel.setBorder(new RoundedBorder(15));

//        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titlePassengerLabel = new JLabel("Passenger Information");
        titlePassengerLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        passengerInfoPanel.add(titlePassengerLabel, gbc);

        JLabel firstnamePassengerLabel = new JLabel("Firstname");
        firstnamePassengerLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        passengerInfoPanel.add(firstnamePassengerLabel, gbc);

        MyTextField firstnamePassengerField = new MyTextField();
        firstnamePassengerField.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        firstnamePassengerField.setPreferredSize(new Dimension(300, 50));
        gbc.gridx = 0;
        gbc.gridy = 2;
        passengerInfoPanel.add(firstnamePassengerField, gbc);

        // Tên Đệm & Tên
        JLabel lastnamePassengerLabel = new JLabel("Lastname");
        lastnamePassengerLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        gbc.gridx = 1;
        gbc.gridy = 1;
        passengerInfoPanel.add(lastnamePassengerLabel, gbc);

        MyTextField lastnamePassengerField = new MyTextField();
        lastnamePassengerField.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        lastnamePassengerField.setPreferredSize(new Dimension(300, 50));
        gbc.gridx = 1;
        gbc.gridy = 2;
        passengerInfoPanel.add(lastnamePassengerField, gbc);

        JLabel phonePassengerLabel = new JLabel("Phone Number");
        phonePassengerLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        passengerInfoPanel.add(phonePassengerLabel, gbc);

        MyTextField phonePassengerField = new MyTextField();
        phonePassengerField.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        phonePassengerField.setPreferredSize(new Dimension(300, 50));
        gbc.gridx = 0;
        gbc.gridy = 4;
        passengerInfoPanel.add(phonePassengerField, gbc);

        JLabel emailPassengerLabel = new JLabel("Email");
        emailPassengerLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        gbc.gridx = 1;
        gbc.gridy = 3;
        passengerInfoPanel.add(emailPassengerLabel, gbc);

        MyTextField emailPassengerField = new MyTextField();
        emailPassengerField.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        emailPassengerField.setPreferredSize(new Dimension(300, 50));
        gbc.gridx = 1;
        gbc.gridy = 4;
        passengerInfoPanel.add(emailPassengerField, gbc);

        // Thêm panel chính vào frame
        frame.add(contactInfoPanel);
        frame.add(passengerInfoPanel);
        frame.setVisible(true);
    }
}
