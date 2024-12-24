package com.group5.flight.booking.form;

import com.group5.flight.booking.core.Constants;
import com.group5.flight.booking.dto.ContactInfo;
import com.group5.flight.booking.dto.FlightInfo;
import com.group5.flight.booking.dto.PassengerInfo;
import com.group5.flight.booking.form.component.RoundedBorder;
import com.group5.flight.booking.form.swing.MyTextField;
import com.group5.flight.booking.service.FlightService;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ContactFormPanel extends JPanel{
    
    private static final Integer PANEL_WIDTH = 750;

    private static final Integer PANEL_HEIGHT = 300;

    private final JPanel mainPanel;

    private final CardLayout cardLayout;

    private final FlightInfo flightInfo;

    private final FlightService flightService;

    private ContactInfo contactInfo;

    private List<PassengerInfo> passengerInfoList;

    public ContactFormPanel(JPanel mainPanel, CardLayout cardLayout, FlightInfo flightInfo, FlightService flightService) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.flightInfo = flightInfo;
        this.flightService = flightService;

        contactInfo = new ContactInfo();
        passengerInfoList = new LinkedList<>();

        initComponent();
    }
    
    public void initComponent() {
        setLayout(new BorderLayout());
        setBackground(new Color(247, 249, 250));

        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
        mainContentPanel.setBackground(new Color(247, 249, 250));

        JScrollPane mainScrollPane = new JScrollPane(mainContentPanel);
        mainScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


        mainContentPanel.add(Box.createVerticalStrut(40));
        JPanel titlePanel = createTitlePanel();
        mainContentPanel.add(titlePanel);

        mainContentPanel.add(Box.createVerticalStrut(20));
        JPanel contactPanel = createContactPanel();
        mainContentPanel.add(contactPanel);

        JPanel passengerWrapper = new JPanel();
        passengerWrapper.setLayout(new GridBagLayout());
        passengerWrapper.setMaximumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        passengerWrapper.setMinimumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        passengerWrapper.setBackground(Color.WHITE);
        passengerWrapper.setBorder(new RoundedBorder(15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;

        JLabel titleContactLabel = new JLabel("Traveller Information");
        titleContactLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        passengerWrapper.add(titleContactLabel, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy = 1;
        for (int i = 0; i < 2; i++) {
            passengerWrapper.add(Box.createVerticalStrut(20), gbc);
            gbc.gridy++;
            passengerWrapper.add(createPassengerInfoPanel(i,"Passenger " + (i + 1)), gbc);
            gbc.gridy++;
        }

        mainContentPanel.add(Box.createVerticalStrut(20));
        mainContentPanel.add(passengerWrapper);

        JPanel buttonWrapperPanel = createButtonWrapperPanel();
        mainContentPanel.add(Box.createVerticalStrut(20));
        mainContentPanel.add(buttonWrapperPanel);
        

        add(mainScrollPane, BorderLayout.CENTER);
    }
    
    private JPanel createTitlePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setOpaque(false);
        JLabel titleLabel = new JLabel("Your Booking");
        titleLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 20));
        panel.add(titleLabel, BorderLayout.NORTH);

        JLabel descriptionLabel = new JLabel("Fill in your details and review your booking.");
        descriptionLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        descriptionLabel.setForeground(new Color(171, 165, 168));
        panel.add(descriptionLabel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createButtonWrapperPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setBackground(Color.WHITE);
        JButton backButton = customButton("Back", new Color(255, 69, 0));
        backButton.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, Constants.FLIGHT_LIST_SCREEN));
        buttonPanel.add(backButton, BorderLayout.WEST);

        JButton continueButton = customButton("Continue", new Color(0, 123, 255));
        continueButton.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        continueButton.addActionListener(e -> {
            FlightSeatPanel flightSeatPanel = new FlightSeatPanel(mainPanel, cardLayout, flightInfo, flightService);
            mainPanel.add(flightSeatPanel, Constants.FLIGHT_SEAT_SCREEN);
            cardLayout.show(mainPanel, Constants.FLIGHT_SEAT_SCREEN);
        });
        buttonPanel.add(continueButton, BorderLayout.EAST);

        return buttonPanel;
    }

    private JPanel createContactPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        panel.setMaximumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        panel.setMinimumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new RoundedBorder(15));

        GridBagConstraints gbc = new GridBagConstraints();
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new GridBagLayout());
        innerPanel.setOpaque(false);

        JLabel titleContactLabel = new JLabel("Contact Details");
        titleContactLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(10, 0, 20, 0);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(titleContactLabel, BorderLayout.NORTH);

        JLabel firstnameContactLabel = new JLabel("First / Given Name & Middle Name");
        firstnameContactLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        firstnameContactLabel.setForeground(new Color(171, 165, 168));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 5, 30);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        innerPanel.add(firstnameContactLabel, gbc);

        MyTextField firstnameContactField = new MyTextField();
        firstnameContactField.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        firstnameContactField.setPreferredSize(new Dimension(300, 50));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 10, 20, 30);
        firstnameContactField.addActionListener(e -> contactInfo.setFirstName(firstnameContactField.getText()));
        innerPanel.add(firstnameContactField, gbc);

        JLabel lastnameContactLabel = new JLabel("Family Name / Last Name");
        lastnameContactLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        lastnameContactLabel.setForeground(new Color(171, 165, 168));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 30, 5, 10);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        innerPanel.add(lastnameContactLabel, gbc);

        MyTextField lastnameContactField = new MyTextField();
        lastnameContactField.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        lastnameContactField.setPreferredSize(new Dimension(300, 50));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 30, 20, 10);
        lastnameContactField.addActionListener(e -> contactInfo.setLastName(lastnameContactField.getText()));
        innerPanel.add(lastnameContactField, gbc);

        JLabel phoneContactLabel = new JLabel("Mobile Number");
        phoneContactLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        phoneContactLabel.setForeground(new Color(171, 165, 168));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(20, 10, 5, 30);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        innerPanel.add(phoneContactLabel, gbc);

        MyTextField phoneContactField = new MyTextField();
        phoneContactField.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        phoneContactField.setPreferredSize(new Dimension(300, 50));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 10, 20, 30);
        phoneContactField.addActionListener(e -> contactInfo.setPhone(phoneContactField.getText()));
        innerPanel.add(phoneContactField, gbc);

        JLabel emailContactLabel = new JLabel("Email");
        emailContactLabel.setForeground(new Color(171, 165, 168));
        emailContactLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(20, 30, 5, 10);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        innerPanel.add(emailContactLabel, gbc);

        MyTextField emailContactField = new MyTextField();
        emailContactField.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        emailContactField.setPreferredSize(new Dimension(300, 50));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 30, 20, 10);
        innerPanel.add(emailContactField, gbc);
        emailContactField.addActionListener(e -> contactInfo.setEmail(emailContactField.getText()));
        panel.add(innerPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createPassengerInfoPanel(int i, String title) {
        PassengerInfo passengerInfo = new PassengerInfo();

        GridBagConstraints gbc = new GridBagConstraints();
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        JLabel titlePassengerLabel = new JLabel(title);
        titlePassengerLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(10, 0, 20, 0);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(titlePassengerLabel, gbc);

        JLabel firstnamePassengerLabel = new JLabel("First / Given Name & Middle Name");
        firstnamePassengerLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        firstnamePassengerLabel.setForeground(new Color(171, 165, 168));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 5, 30);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(firstnamePassengerLabel, gbc);

        MyTextField firstnamePassengerField = new MyTextField();
        firstnamePassengerField.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        firstnamePassengerField.setPreferredSize(new Dimension(300, 50));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 10, 20, 30);
        firstnamePassengerField.addActionListener(e -> passengerInfo.setFirstName(firstnamePassengerField.getText()));
        panel.add(firstnamePassengerField, gbc);

        JLabel lastnamePassengerLabel = new JLabel("Family Name / Last Name");
        lastnamePassengerLabel.setForeground(new Color(171, 165, 168));
        lastnamePassengerLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 30, 5, 10);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(lastnamePassengerLabel, gbc);

        MyTextField lastnamePassengerField = new MyTextField();
        lastnamePassengerField.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        lastnamePassengerField.setPreferredSize(new Dimension(300, 50));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 30, 20, 10);
        lastnamePassengerField.addActionListener(e -> passengerInfo.setLastName(lastnamePassengerField.getText()));
        panel.add(lastnamePassengerField, gbc);

        JLabel birthdayPassengerLabel = new JLabel("Date of Birth");
        birthdayPassengerLabel.setForeground(new Color(171, 165, 168));
        birthdayPassengerLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(20, 10, 5, 30);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(birthdayPassengerLabel, gbc);

        JDateChooser birthdayChooser = new JDateChooser();
        birthdayChooser.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        birthdayChooser.setPreferredSize(new Dimension(300, 50));
        birthdayChooser.setBackground(Color.WHITE);
        birthdayChooser.setBorder(BorderFactory.createLineBorder(new Color(171, 165, 168), 1));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 10, 20, 30);
        birthdayChooser.addPropertyChangeListener("date",
                evt -> passengerInfo.setBirthday((Date) evt.getNewValue()));
        panel.add(birthdayChooser, gbc);

        JLabel nationalityPassengerLabel = new JLabel("Nationality");
        nationalityPassengerLabel.setForeground(new Color(171, 165, 168));
        nationalityPassengerLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(20, 30, 5, 10);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(nationalityPassengerLabel, gbc);

        MyTextField nationalityPassengerField = new MyTextField();
        nationalityPassengerField.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        nationalityPassengerField.setPreferredSize(new Dimension(300, 50));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 30, 20, 10);

        panel.add(nationalityPassengerField, gbc);

        return panel;
    }

    private JPanel createFlightInfoPanel() {
        JPanel flightPanel = new JPanel();
        flightPanel.setLayout(new BoxLayout(flightPanel, BoxLayout.Y_AXIS));
        flightPanel.setBackground(Color.WHITE);
        flightPanel.setBorder(BorderFactory.createTitledBorder("Flight Information"));

        JLabel routeLabel = new JLabel("Ho Chi Minh City → Da Nang");
        routeLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        routeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel dateLabel = new JLabel("Departure: Sun, 26 Jan 2025");
        dateLabel.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 14));
        dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel airlineLabel = new JLabel("Vietravel Airlines - Economy");
        airlineLabel.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 14));
        airlineLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel timeLabel = new JLabel("23:59 → 01:25+1");
        timeLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 14));
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel durationLabel = new JLabel("1h 26m - Direct");
        durationLabel.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 14));
        durationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Thêm vào panel
        flightPanel.add(Box.createVerticalStrut(20));
        flightPanel.add(routeLabel);
        flightPanel.add(Box.createVerticalStrut(10));
        flightPanel.add(dateLabel);
        flightPanel.add(Box.createVerticalStrut(10));
        flightPanel.add(airlineLabel);
        flightPanel.add(Box.createVerticalStrut(10));
        flightPanel.add(timeLabel);
        flightPanel.add(Box.createVerticalStrut(10));
        flightPanel.add(durationLabel);

        return flightPanel;
    }

    private JButton customButton(String text, Color color) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                g2.setColor(color);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

                g2.dispose();

                super.paintComponent(g);
            }
        };

        button.setPreferredSize(new Dimension(150, 50));
        button.setFont(new Font(Constants.FB_FONT, Font.PLAIN, 12));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return button;
    }
}
