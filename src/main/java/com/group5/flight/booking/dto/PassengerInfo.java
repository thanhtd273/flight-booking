package com.group5.flight.booking.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PassengerInfo {
    private String firstName;

    private String lastName;

    private Date birthday;

    private String gender;

    private String email;

    private String phone;
}
