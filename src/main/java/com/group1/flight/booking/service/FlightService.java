package com.group1.flight.booking.service;

import com.group1.flight.booking.core.ErrorCode;
import com.group1.flight.booking.core.exception.LogicException;
import com.group1.flight.booking.dto.FlightDisplayInfo;
import com.group1.flight.booking.dto.FlightInfo;
import com.group1.flight.booking.dto.FilterCriteria;
import com.group1.flight.booking.dto.SeatInfo;
import com.group1.flight.booking.model.Flight;

import java.util.Date;
import java.util.List;

public interface FlightService {

    Flight create(FlightInfo flightInfo) throws LogicException;

    List<Flight> getAllFlights();

    Flight findByFlightId(Long flightId);

    List<FlightInfo> findFlight(Long departureAirportId, Long destinationAirportId, Date departureDate) throws LogicException;

    List<FlightInfo> filterFlights(FilterCriteria filterCriteria) throws LogicException;

    FlightInfo getFlightInfo(Long flightId);

    List<FlightDisplayInfo> getFlightsDisplay(List<FlightInfo> flightInfos);

    List<SeatInfo> getFlightSeats(Long flightId) throws LogicException;

    ErrorCode exportFlightTicket(Long ticketId);
}
