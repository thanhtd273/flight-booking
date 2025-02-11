package com.group1.flight.booking.form;

import com.group1.flight.booking.core.AppUtils;
import com.group1.flight.booking.core.Constants;
import com.group1.flight.booking.dto.BookingInfo;
import com.group1.flight.booking.dto.ContactInfo;
import com.group1.flight.booking.dto.PassengerInfo;
import com.group1.flight.booking.form.component.RoundedBorder;
import com.group1.flight.booking.form.component.FbTextField;
import com.group1.flight.booking.model.Nation;
import com.group1.flight.booking.service.BookingService;
import com.group1.flight.booking.service.FlightService;
import com.group1.flight.booking.service.NationService;
import com.toedter.calendar.JDateChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ContactFormPanel extends JPanel{

    private static final Logger logger = LoggerFactory.getLogger(ContactFormPanel.class);
    
    private static final Integer PANEL_WIDTH = 750;

    private static final Integer PANEL_HEIGHT = 300;

    private final JPanel mainPanel;

    private final CardLayout cardLayout;

    private final BookingInfo bookingInfo;

    private final FlightService flightService;

    private final BookingService bookingService;

    private final NationService nationService;

    private final ContactInfo contactInfo;

    private final PassengerInfo[] passengerInfos;

    public ContactFormPanel(JPanel mainPanel, CardLayout cardLayout, BookingInfo bookingInfo,
                            FlightService flightService, BookingService bookingService, NationService nationService) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.bookingInfo = bookingInfo;
        this.flightService = flightService;
        this.bookingService = bookingService;
        this.nationService = nationService;

        contactInfo = new ContactInfo();
        passengerInfos = new PassengerInfo[bookingInfo.getNumOfPassengers()];
        for (int i = 0; i < bookingInfo.getNumOfPassengers(); i ++) {
            passengerInfos[i] = new PassengerInfo();
        }

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
        passengerWrapper.setBorder(new RoundedBorder());

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
        for (int i = 0; i < bookingInfo.getNumOfPassengers(); i++) {
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
        continueButton.addActionListener(e -> performContinueAction());
        buttonPanel.add(continueButton, BorderLayout.EAST);

        return buttonPanel;
    }

    private JPanel createContactPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        panel.setMaximumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        panel.setMinimumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new RoundedBorder());

        GridBagConstraints gbc = new GridBagConstraints();
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new GridBagLayout());
        innerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Contact Details");
        titleLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(10, 0, 20, 0);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(titleLabel, BorderLayout.NORTH);

        JLabel firstnameLabel = new JLabel("First / Given Name & Middle Name");
        firstnameLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        firstnameLabel.setForeground(new Color(171, 165, 168));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 5, 30);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        innerPanel.add(firstnameLabel, gbc);

        FbTextField firstnameField = new FbTextField();
        firstnameField.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        firstnameField.setPreferredSize(new Dimension(300, 50));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 10, 20, 30);
        firstnameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                contactInfo.setFirstName(firstnameField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                contactInfo.setFirstName(firstnameField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                contactInfo.setFirstName(firstnameField.getText());
            }
        });
        innerPanel.add(firstnameField, gbc);

        JLabel lastnameLabel = new JLabel("Family Name / Last Name");
        lastnameLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        lastnameLabel.setForeground(new Color(171, 165, 168));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 30, 5, 10);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        innerPanel.add(lastnameLabel, gbc);

        FbTextField lastnameField = new FbTextField();
        lastnameField.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        lastnameField.setPreferredSize(new Dimension(300, 50));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 30, 20, 10);
        lastnameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                contactInfo.setLastName(lastnameField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                contactInfo.setLastName(lastnameField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                contactInfo.setLastName(lastnameField.getText());
            }
        });
        innerPanel.add(lastnameField, gbc);

        JLabel phoneLabel = new JLabel("Mobile Number");
        phoneLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        phoneLabel.setForeground(new Color(171, 165, 168));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(20, 10, 5, 30);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        innerPanel.add(phoneLabel, gbc);

        FbTextField phoneField = new FbTextField();
        phoneField.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        phoneField.setPreferredSize(new Dimension(300, 50));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 10, 20, 30);
        phoneField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                contactInfo.setPhone(phoneField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                contactInfo.setPhone(phoneField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                contactInfo.setPhone(phoneField.getText());
            }
        });
        innerPanel.add(phoneField, gbc);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setForeground(new Color(171, 165, 168));
        emailLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(20, 30, 5, 10);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        innerPanel.add(emailLabel, gbc);

        FbTextField emailField = new FbTextField();
        emailField.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        emailField.setPreferredSize(new Dimension(300, 50));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 30, 20, 10);
        innerPanel.add(emailField, gbc);
        emailField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                contactInfo.setEmail(emailField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                contactInfo.setEmail(emailField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                contactInfo.setEmail(emailField.getText());
            }
        });
        panel.add(innerPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createPassengerInfoPanel(int i, String title) {

        GridBagConstraints gbc = new GridBagConstraints();
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(10, 0, 20, 0);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(titleLabel, gbc);

        JLabel firstnameLabel = new JLabel("First / Given Name & Middle Name");
        firstnameLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        firstnameLabel.setForeground(new Color(171, 165, 168));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 5, 30);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(firstnameLabel, gbc);

        FbTextField firstnameField = new FbTextField();
        firstnameField.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        firstnameField.setPreferredSize(new Dimension(300, 50));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 10, 20, 30);
        firstnameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                passengerInfos[i].setFirstName(firstnameField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                passengerInfos[i].setFirstName(firstnameField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                passengerInfos[i].setFirstName(firstnameField.getText());
            }
        });
        panel.add(firstnameField, gbc);

        JLabel lastnameLabel = new JLabel("Family Name / Last Name");
        lastnameLabel.setForeground(new Color(171, 165, 168));
        lastnameLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 30, 5, 10);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(lastnameLabel, gbc);

        FbTextField lastnameField = new FbTextField();
        lastnameField.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        lastnameField.setPreferredSize(new Dimension(300, 50));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 30, 20, 10);
        lastnameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                passengerInfos[i].setLastName(lastnameField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                passengerInfos[i].setLastName(lastnameField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                passengerInfos[i].setLastName(lastnameField.getText());
            }
        });
        panel.add(lastnameField, gbc);

        JLabel birthdayLabel = new JLabel("Date of Birth");
        birthdayLabel.setForeground(new Color(171, 165, 168));
        birthdayLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(20, 10, 5, 30);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(birthdayLabel, gbc);

        JDateChooser birthdayChooser = new JDateChooser();
        birthdayChooser.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        birthdayChooser.setPreferredSize(new Dimension(300, 50));
        birthdayChooser.setBackground(Color.WHITE);
        birthdayChooser.setBorder(BorderFactory.createLineBorder(new Color(171, 165, 168), 1));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 10, 20, 30);
        birthdayChooser.addPropertyChangeListener("date",
                evt -> passengerInfos[i].setBirthday((Date) evt.getNewValue()));
        panel.add(birthdayChooser, gbc);

        JLabel nationalityPassengerLabel = new JLabel("Nationality");
        nationalityPassengerLabel.setForeground(new Color(171, 165, 168));
        nationalityPassengerLabel.setFont(new Font(Constants.FB_FONT, Font.BOLD, 16));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(20, 30, 5, 10);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(nationalityPassengerLabel, gbc);

        List<Nation> nationInfos = nationService.getAllNations();
        Map<String, Long> nationNameMap = generateMenuData(nationInfos);
        String[] names = AppUtils.list2Array(nationNameMap.keySet().stream().toList());

        JComboBox<String> nationalityPassengerField = new JComboBox<>(names);
        nationalityPassengerField.setPreferredSize(new Dimension(300, 50));
        nationalityPassengerField.addActionListener(e -> {
            String nationality = (String) nationalityPassengerField.getSelectedItem();
            if (!ObjectUtils.isEmpty(nationality)) {
                passengerInfos[i].setNationalityId(nationNameMap.get(nationality));
                passengerInfos[i].setNationInfo(nationService.getNationInfo(nationNameMap.get(nationality)));
            }
            logger.debug("Chosen nationality: {}", nationality);
        });

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 30, 20, 10);

        panel.add(nationalityPassengerField, gbc);

        return panel;
    }

    private void performContinueAction() {
        logger.debug("contactInfo: {}, passengerInfos: {}", contactInfo, passengerInfos);
        if (contactInfo.isEmptyField() || Arrays.stream(passengerInfos).anyMatch(PassengerInfo::isEmptyField)) {
            AppUtils.showErrorDialog("Please fill out all information");
            return;
        }
        if (!AppUtils.validateEmail(contactInfo.getEmail())) {
            AppUtils.showErrorDialog("Invalid email! Please fill email again");
            return;
        }
        if (!AppUtils.validatePhoneNumber(contactInfo.getPhone())) {
            AppUtils.showErrorDialog("Invalid phone number! Please fill phone number again");
            return;
        }
        bookingInfo.setPassengerInfos(passengerInfos);
        bookingInfo.setContactInfo(contactInfo);
        FlightSeatPanel flightSeatPanel = new FlightSeatPanel(mainPanel, cardLayout, bookingInfo,
                flightService, bookingService);
        mainPanel.add(flightSeatPanel, Constants.FLIGHT_SEAT_SCREEN);
        cardLayout.show(mainPanel, Constants.FLIGHT_SEAT_SCREEN);
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

    private Map<String, Long> generateMenuData(List<Nation>  nationList) {
        Map<String, Long> map = new HashMap<>();
        for (Nation nation: nationList) {
            map.put(nation.getName(), nation.getNationId());
        }
        return map;
    }
}
