package com.group1.flight.booking.service;

import com.group1.flight.booking.core.ErrorCode;
import com.group1.flight.booking.core.exception.LogicException;
import com.group1.flight.booking.dto.SeatInfo;
import com.group1.flight.booking.model.Seat;

import java.util.List;

public interface SeatService {
    List<Seat> getAllSeats();

    Seat findBySeatId(Long seatId) throws LogicException;

    List<Seat> findByPlaneId(Long planeId) throws LogicException;

    Seat create(SeatInfo seatInfo) throws LogicException;

    Seat update(Long seatId, SeatInfo seatInfo) throws LogicException;

    ErrorCode delete(Long seatId) throws LogicException;

    SeatInfo getSeatInfo(Long seatId, Long flightId) throws LogicException;

}
