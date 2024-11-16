package com.group5.flight.booking.service;

import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dto.AirlineInfo;
import com.group5.flight.booking.model.Airline;

public interface AirlineService {

    Airline findByAirlineId(Long airlineId);
    Airline create(AirlineInfo airlineInfo) throws LogicException;

    Airline update(Long airlineId, AirlineInfo airlineInfo) throws LogicException;

    ErrorCode delete(Long airlineId);
}
