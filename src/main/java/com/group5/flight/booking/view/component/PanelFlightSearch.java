package com.group5.flight.booking.view.component;

import com.group5.flight.booking.view.swing.Button;
import com.group5.flight.booking.view.swing.MyTextField;
import java.awt.Color;
import java.awt.Font;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;

public class PanelFlightSearch extends JPanel {

    private MyTextField txtDeparture;
    private MyTextField txtDestination;
    private MyTextField txtDepartureDate;
    private Button btnSearch;
    private JTable flightTable;

    public PanelFlightSearch() {
        initComponents();
        setOpaque(false);
    }

    private void initComponents() {
        setLayout(new MigLayout("wrap, fill", "[center]", "[top][grow]"));

        // Top section for input fields
        JPanel inputPanel = new JPanel(new MigLayout("wrap 2", "[grow,fill][grow,fill]", "[]10[]10[]"));
        inputPanel.setOpaque(false);

        JLabel lblTitle = new JLabel("Search Flights");
        lblTitle.setFont(new Font("sanserif", Font.BOLD, 30));
        lblTitle.setForeground(new Color(7, 164, 121));
        add(lblTitle, "wrap, align center");

        txtDeparture = new MyTextField();
        txtDeparture.setPrefixIcon(resizeIcon("/departures.png", 20, 20));
        txtDeparture.setHint("Departure Location");
        inputPanel.add(txtDeparture, "span");

        txtDestination = new MyTextField();
        txtDestination.setPrefixIcon(resizeIcon("/destination.png", 20, 20));
        txtDestination.setHint("Destination Location");
        inputPanel.add(txtDestination, "span");

        txtDepartureDate = new MyTextField();
        txtDepartureDate.setPrefixIcon(resizeIcon("/calendar.png", 20, 20));
        txtDepartureDate.setHint("Departure Date (YYYY-MM-DD)");
        inputPanel.add(txtDepartureDate, "span");

        btnSearch = new Button();
        btnSearch.setText("Search");
        btnSearch.setBackground(new Color(7, 164, 121));
        btnSearch.setForeground(new Color(255, 255, 255));
        inputPanel.add(btnSearch, "span, align center, w 50%, h 40");

        add(inputPanel, "growx");

        // Bottom section for table
        JPanel tablePanel = new JPanel(new MigLayout("fill"));
        tablePanel.setOpaque(false);

        flightTable = new JTable();
        flightTable.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Flight Code", "Departure", "Destination", "Departure Date", "Return Date", "Price"}
        ));
        flightTable.setFillsViewportHeight(true);
        flightTable.setRowHeight(30);
        flightTable.setFont(new Font("sansserif", Font.PLAIN, 14));
        flightTable.getTableHeader().setFont(new Font("sansserif", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(flightTable);
        tablePanel.add(scrollPane, "grow");

        add(tablePanel, "grow");
    }

    private ImageIcon resizeIcon(String resourcePath, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(resourcePath)));
            return new ImageIcon(icon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));
        } catch (Exception e) {
            System.err.println("Failed to load image: " + resourcePath);
            return null;
        }
    }

    public MyTextField getTxtDeparture() {
        return txtDeparture;
    }

    public MyTextField getTxtDestination() {
        return txtDestination;
    }

    public MyTextField getTxtDepartureDate() {
        return txtDepartureDate;
    }

    public Button getBtnSearch() {
        return btnSearch;
    }

    public JTable getFlightTable() {
        return flightTable;
    }
}
