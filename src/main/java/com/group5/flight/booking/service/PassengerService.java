package com.group5.flight.booking.service;

import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dto.PassengerInfo;
import com.group5.flight.booking.model.Passenger;

import java.util.List;

public interface PassengerService {

    List<Passenger> getAllPassengers();

    Passenger findByPassengerId(Long id) throws LogicException;

    Passenger create(PassengerInfo passengerInfo) throws LogicException;

    ErrorCode delete(Long id) throws LogicException;


    Passenger update(Long id, PassengerInfo passengerInfo) throws LogicException;

    List<Passenger> findByLastName(String lastName);

    PassengerInfo getPassengerInfo(Long id) throws LogicException;
}
