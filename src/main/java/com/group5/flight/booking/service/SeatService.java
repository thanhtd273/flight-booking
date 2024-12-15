package com.group5.flight.booking.service;

import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dto.SeatInfo;
import com.group5.flight.booking.model.Seat;

import java.util.List;

public interface SeatService {
    List<Seat> getAllSeats();

    Seat findBySeatId(Long id) throws LogicException;

    Seat create(SeatInfo seatInfo) throws LogicException;

    Seat update(Long id, SeatInfo seatInfo) throws LogicException;

    ErrorCode delete(Long id) throws LogicException;

    List<Seat> getAvailableSeatsByFlight(Long flightId) throws LogicException;

    SeatInfo getSeatInfo(Long id) throws LogicException;

    List<Object[]> countAvailableSeatsByClass(Long flightId) throws LogicException;
}
