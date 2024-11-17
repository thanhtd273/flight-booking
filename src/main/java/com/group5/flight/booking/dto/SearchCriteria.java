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

    private DataRange<Float> priceRange;

    private DataRange<Date> departureRanges;

    private DataRange<Date> arrivalRanges;

    private List<Long> airlineIds;
}
