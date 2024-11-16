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
public class SearchCriteria {
    private Float minPrice;

    private Float maxPrice;

    private Date departureFrom;

    private Date departureTo;

    private Date arrivalFrom;

    private Date arrivalTo;

    private List<Long> airlineIds;
}
