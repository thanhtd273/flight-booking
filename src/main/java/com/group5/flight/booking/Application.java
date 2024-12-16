package com.group5.flight.booking;
import com.group5.flight.booking.view.component.*;

import com.group5.flight.booking.service.FlightService;
import com.group5.flight.booking.dto.FlightInfo;
import com.group5.flight.booking.view.component.FindFlightBooking;
import com.group5.flight.booking.view.model.ModelUser;
import com.group5.flight.booking.view.swing.PanelRound;
import net.miginfocom.swing.MigLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import com.group5.flight.booking.core.exception.LogicException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Date;
import java.util.List;
import java.awt.BorderLayout;

@SpringBootApplication
public class Application extends JFrame {

    private final DecimalFormat df = new DecimalFormat("##0.###", DecimalFormatSymbols.getInstance(Locale.US));
    private MigLayout layout;
    private PanelCover cover;
    private PanelLoginAndRegister loginAndRegister;
    private PanelLoading loading;
    private PanelVerifyCode verifyCode;
    private boolean isLogin = true;
    private final double addSize = 30;
    private final double coverSize = 40;
    private final double loginSize = 60;
    private FindFlightBooking findFlightBooking;
    private FlightService flightService;

    @Autowired
    public Application(FlightService flightService) {
        ActionListener eventRegister = e -> {
            System.out.println("Register button clicked!");
        };
        findFlightBooking = new FindFlightBooking(flightService);
        PanelLoginAndRegister panel = new PanelLoginAndRegister(eventRegister);
        loginAndRegister = new PanelLoginAndRegister(e -> handleLogin());
        add(panel);
        init();
        this.flightService = flightService;
    }


    private void init() {
        layout = new MigLayout("fill, insets 0");
        cover = new PanelCover();
        loading = new PanelLoading();
        verifyCode = new PanelVerifyCode();
        ActionListener eventRegister = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                register();
            }
        };
        loginAndRegister = new PanelLoginAndRegister(eventRegister);
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                double fractionCover;
                double fractionLogin;
                double size = coverSize;
                if (fraction <= 0.5f) {
                    size += fraction * addSize;
                } else {
                    size += addSize - fraction * addSize;
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
                layout.setComponentConstraints(loginAndRegister, "width " + loginSize + "%, pos " + fractionLogin + "al 0 n 100%");
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
        animator.setResolution(0);  //  for smooth animation
        bg.setLayout(layout);
        bg.setLayer(loading, JLayeredPane.POPUP_LAYER);
        bg.setLayer(verifyCode, JLayeredPane.POPUP_LAYER);
        bg.add(loading, "pos 0 0 100% 100%");
        bg.add(verifyCode, "pos 0 0 100% 100%");
        bg.add(cover, "width " + coverSize + "%, pos " + (isLogin ? "1al" : "0al") + " 0 n 100%");
        bg.add(loginAndRegister, "width " + loginSize + "%, pos " + (isLogin ? "0al" : "1al") + " 0 n 100%"); //  1al as 100%
        loginAndRegister.showRegister(!isLogin);
        cover.login(isLogin);
        cover.addEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!animator.isRunning()) {
                    animator.start();
                }
            }
        });
    }
    // Handle login and transition to flight booking screen
    private void handleLogin() {
        ModelUser user = loginAndRegister.getUser();  // Get user details from login form

        // Simulating successful login, you can replace this with actual login logic
        boolean loginSuccessful = true;  // Replace with actual validation logic

        if (loginSuccessful) {
            showFindFlightBooking();  // Show flight search screen if login is successful
        } else {
            // Handle login failure (e.g., show error message)
            showMessage(Message.MessageType.ERROR, "Login failed! Please try again.");
        }
    }

    // Show the flight booking panel after login
    private void showFindFlightBooking() {
        // Remove current screen (login/register) and add the flight booking screen
        this.getContentPane().removeAll();
        this.add(findFlightBooking);
        this.revalidate();
        this.repaint();
        findFlightBooking.setVisible(true);  // Ensure the flight booking panel is visible
    }

    public void showFlightList(Long fromAirportId, Long toAirportId, Date departureDate) {
        try {
            // Call the findFlight method on the instance of FlightService
            List<FlightInfo> flights = flightService.findFlight(fromAirportId, toAirportId, departureDate);

            // If no flights found, show a message
            if (flights.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy chuyến bay nào phù hợp!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Create and display the flight list panel
                JPanel flightListPanel = createFlightListPanel(flights);
                this.getContentPane().removeAll();
                this.add(flightListPanel);
                this.revalidate();
                this.repaint();
            }
        } catch (LogicException e) {
            // Handle the exception here (e.g., show a dialog with the error message)
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi tìm kiếm chuyến bay: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createFlightListPanel(List<FlightInfo> flights) {
        String[] columnNames = {"Mã chuyến bay", "Điểm đi", "Điểm đến", "Ngày khởi hành", "Giờ khởi hành", "Số ghế trống"};

        // Tạo dữ liệu cho bảng từ danh sách chuyến bay
        Object[][] data = flights.stream()
                .map(flight -> new Object[]{
                 //       flight.getFlightId(),
                  //      flight.getFromAirportName(),
                   //     flight.getToAirportName(),
                  //      flight.getDepartureDate(),
                  //      flight.getDepartureTime(),
                   //     flight.getAvailableSeats()
                }).toArray(Object[][]::new);

        // Tạo bảng và cuộn cho danh sách chuyến bay
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }
    private void register() {
        ModelUser user = loginAndRegister.getUser();
        //loading.setVisible(true);
        //System.out.println("Click register");
        verifyCode.setVisible(true);
    }

    private void showMessage(Message.MessageType messageType, String message) {
        Message ms = new Message();
        ms.showMessage(messageType, message);
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void begin() {
                if (!ms.isShow()) {
                    bg.add(ms, "pos 0.5al -30", 0); //  Insert to bg fist index 0
                    ms.setVisible(true);
                    bg.repaint();
                }
            }

            @Override
            public void timingEvent(float fraction) {
                float f;
                if (ms.isShow()) {
                    f = 40 * (1f - fraction);
                } else {
                    f = 40 * fraction;
                }
                layout.setComponentConstraints(ms, "pos 0.5al " + (int) (f - 30));
                bg.repaint();
                bg.revalidate();
            }

            @Override
            public void end() {
                if (ms.isShow()) {
                    bg.remove(ms);
                    bg.repaint();
                    bg.revalidate();
                } else {
                    ms.setShow(true);
                }
            }
        };
        Animator animator = new Animator(300, target);
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    animator.start();
                } catch (InterruptedException e) {
                    System.err.println(e);
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        java.awt.EventQueue.invokeLater(() -> {
            org.springframework.context.ApplicationContext context =
                    SpringApplication.run(Application.class, args);

            FlightService flightService = context.getBean(FlightService.class);
            new Application(flightService).setVisible(true);
        });
    }

    private javax.swing.JLayeredPane bg;
}
