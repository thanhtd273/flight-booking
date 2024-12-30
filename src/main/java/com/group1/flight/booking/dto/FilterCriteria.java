package com.group1.flight.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.Range;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FilterCriteria {
    private Long departureAirportId;

    private Long destinationAirportId;

    private Date departureDate;

    private Float minPrice;

    private Float maxPrice;

    private List<Range<LocalTime>> departureTimes = new ArrayList<>();

    private List<Range<LocalTime>> arrivalTimes = new ArrayList<>();

    private List<Long> airlineIds = new ArrayList<>();

    @Override
    public String toString() {
        return "FilterCriteria{" +
                "departureAirportId=" + departureAirportId +
                ", destinationAirportId=" + destinationAirportId +
                ", departureDate=" + departureDate +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", departureTimes=" + departureTimes +
                ", arrivalTimes=" + arrivalTimes +
                ", airlineIds=" + airlineIds +
                '}';
    }
}
