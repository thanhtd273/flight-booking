package com.group1.flight.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AirlineInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1905122041950251207L;

    private Long airlineId;

    private String name;

    private String code;

    @Override
    public String toString() {
        return "AirlineInfo{" +
                "airlineId=" + airlineId +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
