package com.group1.flight.booking.form.component;

import javax.swing.*;
import java.awt.*;

public class FbCheckbox extends JCheckBox {
    public FbCheckbox(String text) {
        super(text);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int arc = 10;
        if (isSelected()) {
            g2.setColor(new Color(0, 150, 0));
        } else {
            g2.setColor(Color.LIGHT_GRAY);
        }
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        g2.setColor(Color.GRAY);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);

        g2.setColor(Color.BLACK);
        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        int textX = getHeight() + 5;
        int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
        g2.drawString(getText(), textX, textY);

        g2.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(20, 20);
    }
}
