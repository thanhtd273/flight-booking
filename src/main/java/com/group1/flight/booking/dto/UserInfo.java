package com.group1.flight.booking.dto;

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

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", avatar='" + avatar + '\'' +
                ", roles='" + roles + '\'' +
                ", status=" + status +
                '}';
    }
}
