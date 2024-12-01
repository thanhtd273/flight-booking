package com.group5.flight.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.Range;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchCriteria {
    private Long fromAirportId;

    private Long toAirportId;

    private Date departureDate;

    private Float minPrice;

    private Float maxPrice;

    private List<Range<LocalTime>> departureTimes;

    private List<Range<LocalTime>> arrivalTimes;

    private List<Long> airlineIds;
}
