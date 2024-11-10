package com.group5.flight.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
public class UpdatePasswordInfo implements Serializable {
    private String email;

    private String oldPassword;

    private String newPassword;
}
