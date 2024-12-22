package com.group5.flight.booking.service;

import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dto.FlightDisplayInfo;
import com.group5.flight.booking.dto.FlightInfo;
import com.group5.flight.booking.dto.SearchCriteria;
import com.group5.flight.booking.model.Flight;

import java.util.Date;
import java.util.List;

public interface FlightService {

    Flight create(FlightInfo flightInfo) throws LogicException;

    List<Flight> getAllFlights();

    Flight findByFlightId(Long flightId);

    List<FlightInfo> findFlight(Long fromAirportId, Long toAirportId, Date departureDate) throws LogicException;

    List<FlightInfo> filter(SearchCriteria searchCriteria) throws LogicException;

    FlightInfo getFlightInfo(Long flightId);

    List<FlightDisplayInfo> getFlightsDisplay(List<FlightInfo> flightInfos);
}
