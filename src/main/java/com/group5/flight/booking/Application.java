package com.group5.flight.booking;

import com.group5.flight.booking.dto.FlightInfo;
import com.group5.flight.booking.model.User;
import com.group5.flight.booking.service.FlightService;
import com.group5.flight.booking.service.SeatService;
import com.group5.flight.booking.view.component.FindFlightBooking;
import com.group5.flight.booking.view.component.Message;
import com.group5.flight.booking.view.component.PanelCover;
import com.group5.flight.booking.view.component.PanelLoading;
import com.group5.flight.booking.view.component.PanelLoginAndRegister;
import com.group5.flight.booking.view.component.PanelVerifyCode;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@SpringBootApplication
public class Application extends JFrame {

    private final DecimalFormat df = new DecimalFormat("##0.###", DecimalFormatSymbols.getInstance(Locale.US));
    private MigLayout layout;
    private PanelCover cover;
    private PanelLoginAndRegister loginAndRegister;
    private PanelLoading loading;
    private PanelVerifyCode verifyCode;
    private boolean isLogin = true;
    private static final double ADD_SIZE = 30;
    private static final double COVER_SIZE = 40;
    private static final double LOGIN_SIZE = 60;
    private FindFlightBooking findFlightBooking;
    private final FlightService flightService;
    private final SeatService seatService;

    @Autowired
    public Application(FlightService flightService, SeatService seatService) {
        this.flightService = flightService;
        this.seatService = seatService;
        init();
    }

    private void init() {
        bg = new JLayeredPane();
        layout = new MigLayout("fill, insets 0");
        cover = new PanelCover();
        loading = new PanelLoading();
        verifyCode = new PanelVerifyCode();
        ActionListener eventRegister = ae -> register();
        loginAndRegister = new PanelLoginAndRegister(eventRegister);

        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                double fractionCover;
                double fractionLogin;
                double size = COVER_SIZE;
                if (fraction <= 0.5f) {
                    size += fraction * ADD_SIZE;
                } else {
                    size += ADD_SIZE - fraction * ADD_SIZE;
                }
                if (isLogin) {
                    fractionCover = 1f - fraction;
                    fractionLogin = fraction;
                    if (fraction >= 0.5f) {
                        cover.registerRight(fractionCover * 100);
                    } else {
                        cover.loginRight(fractionLogin * 100);
                    }
                } else {
                    fractionCover = fraction;
                    fractionLogin = 1f - fraction;
                    if (fraction <= 0.5f) {
                        cover.registerLeft(fraction * 100);
                    } else {
                        cover.loginLeft((1f - fraction) * 100);
                    }
                }
                if (fraction >= 0.5f) {
                    loginAndRegister.showRegister(isLogin);
                }
                fractionCover = Double.valueOf(df.format(fractionCover));
                fractionLogin = Double.valueOf(df.format(fractionLogin));
                layout.setComponentConstraints(cover, "width " + size + "%, pos " + fractionCover + "al 0 n 100%");
                layout.setComponentConstraints(loginAndRegister, "width " + LOGIN_SIZE + "%, pos " + fractionLogin + "al 0 n 100%");
                bg.revalidate();
            }

            @Override
            public void end() {
                isLogin = !isLogin;
            }
        };
        Animator animator = new Animator(800, target);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.setResolution(0);
        bg.setLayout(layout);
        bg.setLayer(loading, JLayeredPane.POPUP_LAYER);
        bg.setLayer(verifyCode, JLayeredPane.POPUP_LAYER);
        bg.add(loading, "pos 0 0 100% 100%");
        bg.add(verifyCode, "pos 0 0 100% 100%");
        bg.add(cover, "width " + COVER_SIZE + "%, pos " + (isLogin ? "1al" : "0al") + " 0 n 100%");
        bg.add(loginAndRegister, "width " + LOGIN_SIZE + "%, pos " + (isLogin ? "0al" : "1al") + " 0 n 100%");
        loginAndRegister.showRegister(!isLogin);
        cover.login(isLogin);
        cover.addEvent(ae -> {
            if (!animator.isRunning()) {
                animator.start();
            }
        });

        add(bg);
        setTitle("Flight Booking Application - Group5");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void register() {
        User user = loginAndRegister.getUser();
        verifyCode.setVisible(true);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        java.awt.EventQueue.invokeLater(() -> setVisible(true));
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private javax.swing.JLayeredPane bg;
}
