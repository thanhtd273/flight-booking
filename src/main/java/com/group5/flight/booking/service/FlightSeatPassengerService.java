package com.group5.flight.booking.service;

import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.model.FlightSeatPassenger;

import java.util.List;

public interface FlightSeatPassengerService {

    FlightSeatPassenger create(Long flightId, Long seatId, Long passengerId) throws LogicException;

    FlightSeatPassenger findByFlightIdAndSeatId(Long flightId, Long seatId);

    List<FlightSeatPassenger> findByFlightId(Long flightId);
}
