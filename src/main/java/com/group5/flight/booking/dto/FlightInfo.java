package com.group5.flight.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlightInfo {
    private Long planeId;

    private PlaneInfo plane;

    private Long airlineId;

    private AirlineInfo airline;

    private Long fromAirportId;

    private AirportInfo fromAirport;

    private Long toAirportId;

    private AirportInfo toAirport;

    private Date depatureDate;

    private Date returnDate;

    private Float basePrice;

    private Integer numOfPassengers;

    public boolean isAnyNull() {
        return ObjectUtils.isEmpty(planeId) && ObjectUtils.isEmpty(airlineId) && ObjectUtils.isEmpty(fromAirportId)
                && ObjectUtils.isEmpty(toAirportId) && ObjectUtils.isEmpty(depatureDate)
                && ObjectUtils.isEmpty(basePrice);
    }
}
