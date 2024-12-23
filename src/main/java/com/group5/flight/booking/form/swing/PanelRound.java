package com.group5.flight.booking.form.swing;

import javax.swing.*;
import java.awt.*;

public class PanelRound extends JPanel {

    public PanelRound() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(255, 255, 255));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        super.paintComponent(grphcs);
    }
}
