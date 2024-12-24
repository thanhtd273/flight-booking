package com.group5.flight.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlightInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1905122041950251207L;

    private Long flightId;

    private Long planeId;

    private PlaneInfo plane;

    private Long airlineId;

    private AirlineInfo airline;

    private Long departureAirportId;

    private AirportInfo departureAirportInfo;

    private Long destinationAirportId;

    private AirportInfo destinationAirportInfo;

    private Date departureDate;

    private Date returnDate;

    private Float basePrice;

    private Integer numOfPassengers;

    public boolean isAnyNull() {
        return ObjectUtils.isEmpty(planeId) && ObjectUtils.isEmpty(airlineId) && ObjectUtils.isEmpty(departureAirportId)
                && ObjectUtils.isEmpty(destinationAirportId) && ObjectUtils.isEmpty(departureDate)
                && ObjectUtils.isEmpty(basePrice);
    }

    @Override
    public String toString() {
        return "FlightInfo{" +
                "flightId=" + flightId +
                ", planeId=" + planeId +
                ", plane=" + plane +
                ", airlineId=" + airlineId +
                ", airline=" + airline +
                ", departureAirportId=" + departureAirportId +
                ", departureAirportInfo=" + departureAirportInfo +
                ", destinationAirportId=" + destinationAirportId +
                ", destinationAirportInfo=" + destinationAirportInfo +
                ", departureDate=" + departureDate +
                ", returnDate=" + returnDate +
                ", basePrice=" + basePrice +
                ", numOfPassengers=" + numOfPassengers +
                '}';
    }
}
