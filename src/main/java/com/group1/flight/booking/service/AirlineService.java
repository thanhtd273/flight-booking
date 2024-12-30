package com.group1.flight.booking.service;

import com.group1.flight.booking.core.ErrorCode;
import com.group1.flight.booking.core.exception.LogicException;
import com.group1.flight.booking.dto.AirlineInfo;
import com.group1.flight.booking.model.Airline;

import java.util.List;

public interface AirlineService {

    List<Airline> getAllAirlines();

    Airline findByAirlineId(Long airlineId);

    Airline create(AirlineInfo airlineInfo) throws LogicException;

    Airline update(Long airlineId, AirlineInfo airlineInfo) throws LogicException;

    ErrorCode delete(Long airlineId);

    AirlineInfo getAirlineInfo(Long airlineId);
}
