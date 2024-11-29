package com.group5.flight.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlightSeatPassengerInfo {
    private Long id;
    private Long flightId;
    private Long seatId;
    private Long passengerId;

} 