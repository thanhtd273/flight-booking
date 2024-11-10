package com.group5.flight.booking.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OTPInfo {
    private Integer code;

    private String email;
}
