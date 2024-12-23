package com.group5.flight.booking.form.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group5.flight.booking.core.APIResponse;
import com.group5.flight.booking.model.User;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginAndRegisterPanel extends JLayeredPane {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Getter
    private User user;

    public LoginAndRegisterPanel(ActionListener eventRegister) {
        initComponents();
        initRegister(eventRegister);
        initLogin();
        login.setVisible(false);
        register.setVisible(true);
    }

    private void initRegister(ActionListener eventRegister) {
        register.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push"));
        JLabel label = new JLabel("Create Account");
        label.setFont(new Font("sanserif", Font.BOLD, 30));
        label.setForeground(new Color(7, 164, 121));
        register.add(label);

        FbTextField txtUser = new FbTextField();
        txtUser.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/user.png"))));
        txtUser.setHint("Name");
        register.add(txtUser, "w 60%");

        FbTextField txtEmail = new FbTextField();
        txtEmail.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/mail.png"))));
        txtEmail.setHint("Email");
        register.add(txtEmail, "w 60%");

        FbPasswordField txtPass = new FbPasswordField();
        txtPass.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/pass.png"))));
        txtPass.setHint("Password");
        register.add(txtPass, "w 60%");

        FbButton cmd = new FbButton();
        cmd.setBackground(new Color(7, 164, 121));
        cmd.setForeground(new Color(250, 250, 250));
        cmd.addActionListener(eventRegister);
        cmd.setText("SIGN UP");
        cmd.addActionListener(e -> {
            String name = txtUser.getText();
            String email = txtEmail.getText();
            String password = String.valueOf(txtPass.getPassword());
            callRegisterAPI(name, email, password);
        });

        register.add(cmd, "w 40%, h 40");
        cmd.addActionListener(ae -> {
            String userName = txtUser.getText().trim();
            String email = txtEmail.getText().trim();
            String password = String.valueOf(txtPass.getPassword());
            User user = new User();
            user.setUserId(null);
            user.setName(userName);
            user.setEmail(email);
            user.setPassword(password);
        });
    }

    private void initLogin() {
        login.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push"));
        JLabel label = new JLabel("Sign In");
        label.setFont(new Font("sanserif", 1, 30));
        label.setForeground(new Color(7, 164, 121));
        login.add(label);

        FbTextField txtEmail = new FbTextField();
        txtEmail.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/mail.png"))));
        txtEmail.setHint("Email");
        login.add(txtEmail, "w 60%");

        FbPasswordField txtPass = new FbPasswordField();
        txtPass.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/pass.png"))));
        txtPass.setHint("Password");
        login.add(txtPass, "w 60%");

        JButton cmdForget = new JButton("Forgot your password ?");
        cmdForget.setForeground(new Color(100, 100, 100));
        cmdForget.setFont(new Font("sanserif", Font.BOLD, 12));
        cmdForget.setContentAreaFilled(false);
        cmdForget.setCursor(new Cursor(Cursor.HAND_CURSOR));
        login.add(cmdForget);

        FbButton cmd = new FbButton();
        cmd.setBackground(new Color(7, 164, 121));
        cmd.setForeground(new Color(250, 250, 250));
        cmd.setText("SIGN IN");
        cmd.addActionListener(e -> {
            String email = txtEmail.getText();
            String password = String.valueOf(txtPass.getPassword());
            callLoginAPI(email, password);
        });

        login.add(cmd, "w 40%, h 40");
    }

    private void callRegisterAPI(String name, String email, String password) {
        try {
            String url = "http://localhost:8080/api/v1/flight-booking/users/register";
            Map<String, String> body = new HashMap<>();
            body.put("name", name);
            body.put("email", email);
            body.put("password", password);

            ResponseEntity<String> response = restTemplate.postForEntity(url, body, String.class);
            APIResponse apiResponse = objectMapper.readValue(response.getBody(), APIResponse.class);

            if (apiResponse.getStatusCode() == 200) {
                JOptionPane.showMessageDialog(this, "Registration successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed: " + apiResponse.getStatusMessage());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void callLoginAPI(String email, String password) {
        try {
            String url = "http://localhost:8080/api/v1/flight-booking/users/login";
            Map<String, String> body = new HashMap<>();
            body.put("email", email);
            body.put("password", password);

            ResponseEntity<String> response = restTemplate.postForEntity(url, body, String.class);
            APIResponse apiResponse = objectMapper.readValue(response.getBody(), APIResponse.class);

            if (apiResponse.getStatusCode() == 200) {
                JOptionPane.showMessageDialog(this, "Login successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Login failed: " + apiResponse.getStatusMessage());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void initComponents() {

        login = new JPanel();
        register = new JPanel();

        setLayout(new CardLayout());

        login.setBackground(new Color(255, 255, 255));

        GroupLayout loginLayout = new GroupLayout(login);
        login.setLayout(loginLayout);
        loginLayout.setHorizontalGroup(
                loginLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 327, Short.MAX_VALUE)
        );
        loginLayout.setVerticalGroup(
                loginLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE)
        );

        add(login, "card3");

        register.setBackground(new Color(255, 255, 255));

        GroupLayout registerLayout = new GroupLayout(register);
        register.setLayout(registerLayout);
        registerLayout.setHorizontalGroup(
                registerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 327, Short.MAX_VALUE)
        );
        registerLayout.setVerticalGroup(
                registerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE)
        );

        add(register, "card2");
    }

    private JPanel login;
    private JPanel register;

    public void showRegister(boolean isLogin) {
        if (isLogin) {
            login.setVisible(false);
            register.setVisible(true);
        } else {
            login.setVisible(true);
            register.setVisible(false);
        }
    }
}