package com.group5.flight.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TravelInfo {
    private Long flightId;

    private Long departureAirportId;

    private Long destinationAirportId;

    private Date departureDate;

    private Integer numOfPassengers;

    private Long contactId;

    private List<Long> passengerIds;

    private List<Long> seatIds;

    private Long invoiceId;

    @Override
    public String toString() {
        return "TravelInfo{" +
                "flightId=" + flightId +
                ", departureAirportId=" + departureAirportId +
                ", destinationAirportId=" + destinationAirportId +
                ", departureDate=" + departureDate +
                ", numOfPassengers=" + numOfPassengers +
                ", contactId=" + contactId +
                ", passengerIds=" + passengerIds +
                ", seatIds=" + seatIds +
                ", invoiceId=" + invoiceId +
                '}';
    }
}
