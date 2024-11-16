package com.group5.flight.booking.dto;

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

    private String birthday;

    private String gender;

    private String email;

    private String phone;
}
