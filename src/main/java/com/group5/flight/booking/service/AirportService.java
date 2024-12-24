package com.group5.flight.booking.service;

import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dto.AirportInfo;
import com.group5.flight.booking.model.Airport;

import java.util.List;

public interface AirportService {
    List<Airport> getAllAirports();

    Airport findByAirportId(Long airportId);

    Airport create(AirportInfo airportInfo) throws LogicException;

    Airport update(Long airportId, AirportInfo airportInfo) throws LogicException;

    ErrorCode delete(Long airportId);

    AirportInfo getAirportInfo(Long airportId);

    List<AirportInfo> getAllAirportInfos();


    String[] getLocationOfAllAirport();
}
