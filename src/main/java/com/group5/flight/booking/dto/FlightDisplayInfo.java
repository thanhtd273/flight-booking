package com.group5.flight.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightDisplayInfo {

    private Long flightCode;

    private String departure;

    private String destination;

    private String departureDate;

    private String returnDate;

    private Float price;
}
