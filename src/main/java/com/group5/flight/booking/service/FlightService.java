package com.group5.flight.booking.service;

import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dto.SearchCriteria;
import com.group5.flight.booking.model.Flight;

import java.util.List;

public interface FlightService {
    List<Flight> search(SearchCriteria searchCriteria) throws LogicException;
}
