package com.group5.flight.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlightInfo {
    private String fromAirport;

    private String toAirport;

    private Date depatureDate;

    private Date returnDate;

    private Float basePrice;

    private Integer numOfPassengers;
}
