package com.group5.flight.booking.view.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class PanelVerifyCode extends JPanel {

    public PanelVerifyCode() {
        initComponents();
        setOpaque(false);
        setFocusCycleRoot(true);
        super.setVisible(false);
        addMouseListener(new MouseAdapter() {
        });
    }

    @Override
    public void setVisible(boolean bln) {
        super.setVisible(bln);
        if (bln) {
            txtCode.grabFocus();
            txtCode.setText("");
        }
    }

    private void initComponents() {

        panelRound1 = new com.group5.flight.booking.view.swing.PanelRound();
        txtCode = new com.group5.flight.booking.view.swing.MyTextField();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        cmdOK = new com.group5.flight.booking.view.swing.ButtonOutLine();
        cmdCancel = new com.group5.flight.booking.view.swing.ButtonOutLine();

        txtCode.setHorizontalAlignment(SwingConstants.CENTER);

        jLabel1.setFont(new Font("sansserif", Font.BOLD, 24)); // NOI18N
        jLabel1.setForeground(new Color(63, 63, 63));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("Verify Code");

        jLabel2.setForeground(new Color(63, 63, 63));
        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel2.setText("Check your mail to get verify code");

        cmdOK.setBackground(new Color(18, 138, 62));
        cmdOK.setText("OK");

        cmdCancel.setBackground(new Color(192, 25, 25));
        cmdCancel.setText("Cancel");
        cmdCancel.addActionListener(this::cmdCancelActionPerformed);

        GroupLayout panelRound1Layout = new GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
                panelRound1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(panelRound1Layout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addGroup(panelRound1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(panelRound1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txtCode, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                                        .addGroup(panelRound1Layout.createSequentialGroup()
                                                .addGap(25, 25, 25)
                                                .addComponent(cmdOK, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(cmdCancel, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)))
                                .addGap(100, 100, 100))
        );
        panelRound1Layout.setVerticalGroup(
                panelRound1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, panelRound1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel1)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(panelRound1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(cmdOK, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cmdCancel, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20))
        );

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(50, Short.MAX_VALUE)
                                .addComponent(panelRound1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(50, Short.MAX_VALUE)
                                .addComponent(panelRound1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(50, Short.MAX_VALUE))
        );
    }

    private void cmdCancelActionPerformed(java.awt.event.ActionEvent evt) {
        setVisible(false);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setColor(new Color(50, 50, 50));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setComposite(AlphaComposite.SrcOver);
        super.paintComponent(grphcs);
    }

    public String getInputCode() {
        return txtCode.getText().trim();
    }

    public void addEventButtonOK(ActionListener event) {
        cmdOK.addActionListener(event);
    }

    private com.group5.flight.booking.view.swing.ButtonOutLine cmdCancel;
    private com.group5.flight.booking.view.swing.ButtonOutLine cmdOK;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private com.group5.flight.booking.view.swing.PanelRound panelRound1;
    private com.group5.flight.booking.view.swing.MyTextField txtCode;
}
