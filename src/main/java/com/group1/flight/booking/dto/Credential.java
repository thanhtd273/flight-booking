package com.group1.flight.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Credential {
    private String email;

    private String password;

    @Override
    public String toString() {
        return "Credential{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
