package com.group5.flight.booking.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfo implements Serializable {
    private Long userId;

    private String name;

    private String email;

    private String password;

    private String confirmPassword;

    private String avatar;

    private String roles;

    private Integer status;
}
