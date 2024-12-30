package com.group1.flight.booking.service;

import com.group1.flight.booking.core.ErrorCode;
import com.group1.flight.booking.core.exception.LogicException;
import com.group1.flight.booking.dto.PassengerInfo;
import com.group1.flight.booking.model.Passenger;

import java.util.List;

public interface PassengerService {
    List<Passenger> getAllPassengers();

    Passenger findByPassengerId(Long passengerId);

    Passenger create(PassengerInfo passengerInfo) throws LogicException;

    List<Passenger> create(PassengerInfo[] passengerInfos) throws LogicException;

    Passenger update(Long passengerId, PassengerInfo passengerInfo) throws LogicException;

    ErrorCode delete(Long passengerId);

    PassengerInfo getPassengerInfo(Long passengerId);
}
