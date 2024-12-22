package com.group5.flight.booking;

import com.group5.flight.booking.form.JFrameLogin;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(Application.class)
                .web(WebApplicationType.NONE)
                .headless(false)
                .bannerMode(Banner.Mode.OFF)
                .run(args);

        EventQueue.invokeLater(() -> {

            JFrameLogin ex = ctx.getBean(JFrameLogin.class);
            ex.setVisible(true);
        });
    }

}
