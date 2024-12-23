package com.group5.flight.booking.form;

import com.group5.flight.booking.core.Constants;
import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.form.component.FbTextField;
import com.group5.flight.booking.form.component.ButtonOutLine;
import com.group5.flight.booking.form.component.PanelRound;
import com.group5.flight.booking.service.UserService;
import lombok.Setter;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class CodeVerifierPanel extends JPanel {

    private static final Logger logger = LoggerFactory.getLogger(CodeVerifierPanel.class);

    private final JPanel mainPanel;

    private final CardLayout cardLayout;

    private FbTextField txtCode;

    private final UserService userService;

    @Setter
    private String email;

    public CodeVerifierPanel(JPanel mainPanel, CardLayout cardLayout, UserService userService) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.userService = userService;

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

        PanelRound panelRound1 = new PanelRound();
        txtCode = new FbTextField();
        JLabel titleLabel = new JLabel();
        JLabel descriptionLabel = new JLabel();
        ButtonOutLine okBtn = new ButtonOutLine();
        ButtonOutLine cancelBtn = new ButtonOutLine();

        txtCode.setHorizontalAlignment(SwingConstants.CENTER);

        titleLabel.setFont(new Font("sansserif", Font.BOLD, 24)); // NOI18N
        titleLabel.setForeground(new Color(63, 63, 63));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("Verify Code");

        descriptionLabel.setForeground(new Color(63, 63, 63));
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        descriptionLabel.setText("Check your mail to get verify code");

        okBtn.setBackground(new Color(18, 138, 62));
        okBtn.addActionListener(e -> {
            try {
                if (!NumberUtils.isCreatable(txtCode.getText())) {
                    throw new LogicException(ErrorCode.INVALID_CODE);
                }
                logger.debug("OTP code: email = {}, code = {}", email, txtCode.getText());
                ErrorCode errorCode = userService.verifyPasswordResetCode(email, Integer.valueOf(txtCode.getText()));
                if (errorCode != ErrorCode.SUCCESS) {
                    throw new LogicException(errorCode);
                }

                cardLayout.show(mainPanel, Constants.LOGIN_SCREEN);
            } catch (LogicException ex) {
                logger.error("Verify OTP code fail, error: {}", ex.getMessage());
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Verify OTP Failed", JOptionPane.ERROR_MESSAGE);
            }

        });
        okBtn.setText("OK");

        cancelBtn.setBackground(new Color(192, 25, 25));
        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(this::cmdCancelActionPerformed);

        GroupLayout panelRound1Layout = new GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
                panelRound1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(panelRound1Layout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addGroup(panelRound1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(panelRound1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txtCode, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(titleLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(descriptionLabel, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                                        .addGroup(panelRound1Layout.createSequentialGroup()
                                                .addGap(25, 25, 25)
                                                .addComponent(okBtn, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(cancelBtn, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)))
                                .addGap(100, 100, 100))
        );
        panelRound1Layout.setVerticalGroup(
                panelRound1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, panelRound1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(titleLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(descriptionLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(panelRound1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(okBtn, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cancelBtn, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
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

}
