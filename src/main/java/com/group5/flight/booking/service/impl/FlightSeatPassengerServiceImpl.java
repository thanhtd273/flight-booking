package com.group5.flight.booking.service.impl;

import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dao.FlightSeatPassengerDao;
import com.group5.flight.booking.model.FlightSeatPassenger;
import com.group5.flight.booking.service.FlightSeatPassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightSeatPassengerServiceImpl implements FlightSeatPassengerService {

    private final FlightSeatPassengerDao flightSeatPassengerDao;

    @Override
    public FlightSeatPassenger create(Long flightId, Long seatId, Long passengerId) throws LogicException {
        if (ObjectUtils.isEmpty(flightId) || ObjectUtils.isEmpty(seatId) || ObjectUtils.isEmpty(passengerId)) {
            throw new LogicException(ErrorCode.BLANK_FIELD);
        }

        FlightSeatPassenger flightSeatPassenger = new FlightSeatPassenger();
        flightSeatPassenger.setFlightId(flightId);
        flightSeatPassenger.setSeatId(seatId);
        flightSeatPassenger.setPassengerId(passengerId);

        return flightSeatPassengerDao.save(flightSeatPassenger);
    }

    @Override
    public FlightSeatPassenger findByFlightIdAndSeatId(Long flightId, Long seatId) {
        return flightSeatPassengerDao.findByFlightIdAndSeatId(flightId, seatId);
    }

    @Override
    public List<FlightSeatPassenger> findByFlightId(Long flightId) {
        return flightSeatPassengerDao.findByFlightId(flightId);
    }
}
