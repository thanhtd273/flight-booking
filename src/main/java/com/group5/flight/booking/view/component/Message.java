package com.group5.flight.booking.view.component;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Message extends JPanel {

    private MessageType messageType = MessageType.SUCCESS;

    @Setter
    @Getter
    private boolean show;

    public Message() {
        initComponents();
        setOpaque(false);
        setVisible(false);
    }

    public void showMessage(MessageType messageType, String message) {
        this.messageType = messageType;
        lbMessage.setText(message);
        if (messageType == MessageType.SUCCESS) {
            lbMessage.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/com/group5/flight/booking/view/icon/success.png"))));
        } else {
            lbMessage.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/com/group5/flight/booking/view/icon/error.png"))));
        }
    }

    private void initComponents() {

        lbMessage = new JLabel();

        lbMessage.setFont(new Font("sansserif", 0, 14));
        lbMessage.setForeground(new Color(248, 248, 248));
        lbMessage.setHorizontalAlignment(SwingConstants.CENTER);
        lbMessage.setText("Message");

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbMessage, GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(lbMessage, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        if (messageType == MessageType.SUCCESS) {
            g2.setColor(new Color(15, 174, 37));
        } else {
            g2.setColor(new Color(240, 52, 53));
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setComposite(AlphaComposite.SrcOver);
        g2.setColor(new Color(245, 245, 245));
        g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        super.paintComponent(grphcs);
    }

    public enum MessageType {
        SUCCESS, ERROR
    }

    private JLabel lbMessage;
}
