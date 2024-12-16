package com.group5.flight.booking.service.impl;

import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dao.SeatDao;
import com.group5.flight.booking.dao.FlightSeatPassengerDao;
import com.group5.flight.booking.dto.SeatInfo;
import com.group5.flight.booking.model.Seat;
import com.group5.flight.booking.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatDao seatDao;
    private final FlightSeatPassengerDao flightSeatPassengerDao;

    @Override
    public List<Seat> getAllSeats() {
        return seatDao.findAllSeats();
    }

    @Override
    public Seat findBySeatId(Long id) throws LogicException {
        return seatDao.findBySeatId(id)
                .orElseThrow(() -> new LogicException(ErrorCode.DATA_NULL, "Seat not found"));
    }

    @Override
    public Seat create(SeatInfo seatInfo) throws LogicException {
        if (ObjectUtils.isEmpty(seatInfo)) {
            throw new LogicException(ErrorCode.DATA_NULL);
        }
        if (!seatInfo.isAllNotNull()) {
            throw new LogicException(ErrorCode.BLANK_FIELD, "Seat's info is required");
        }

        Seat seat = new Seat();
        seat.setClassLevel(seatInfo.getClassLevel());
        seat.setSeatCode(seatInfo.getSeatCode());
        seat.setAvailable(seatInfo.getAvailable() != null ? seatInfo.getAvailable() : true);
        seat.setCreatedAt(new Date(System.currentTimeMillis()));

        return seatDao.save(seat);
    }
    @Override
    public Seat update(Long id, SeatInfo seatInfo) throws LogicException {
        if (ObjectUtils.isEmpty(seatInfo)) {
            throw new LogicException(ErrorCode.DATA_NULL, "Seat info is null");
        }
        Seat seat = findBySeatId(id);
        if (ObjectUtils.isEmpty(seat)) {
            throw new LogicException(ErrorCode.DATA_NULL, "Seat does not exist");
        }
        if (!ObjectUtils.isEmpty(seatInfo.getClassLevel())) {
            seat.setClassLevel(seatInfo.getClassLevel());
        }
        if (!ObjectUtils.isEmpty(seatInfo.getSeatCode())) {
            seat.setSeatCode(seatInfo.getSeatCode());
        }
        if (seatInfo.getAvailable() != null) {
            seat.setAvailable(seatInfo.getAvailable());
        }

        seat.setUpdatedAt(new Date(System.currentTimeMillis()));
        return seatDao.save(seat);
    }

    @Override
    public ErrorCode delete(Long id) throws LogicException {
        Seat seat = findBySeatId(id);
        if (ObjectUtils.isEmpty(seat))
            return ErrorCode.DATA_NULL;
        seat.setUpdatedAt(new Date(System.currentTimeMillis()));
        seatDao.save(seat);
        return ErrorCode.SUCCESS;
    }

    @Override
    public List<Seat> getAvailableSeatsByFlight(Long flightId) throws LogicException {
        return flightSeatPassengerDao.findAvailableSeatsByFlightId(flightId);
    }

    @Override
    public SeatInfo getSeatInfo(Long seatId) throws LogicException {
        Seat seat = findBySeatId(seatId);
        if (ObjectUtils.isEmpty(seat)) {
            throw new LogicException(ErrorCode.DATA_NULL, "Seat not found");
        }

        SeatInfo seatInfo = new SeatInfo();
        seatInfo.setClassLevel(seat.getClassLevel());
        seatInfo.setSeatCode(seat.getSeatCode());
        seatInfo.setAvailable(seat.getAvailable());

        return seatInfo;
    }

    @Override
    public List<Object[]> countAvailableSeatsByClass(Long flightId) throws LogicException {
        if (ObjectUtils.isEmpty(flightId)) {
            throw new LogicException(ErrorCode.BLANK_FIELD, "Flight ID is required");
        }
        List<Object[]> result = seatDao.countAvailableSeatsByClass(flightId);
        if (result.isEmpty()) {
            throw new LogicException(ErrorCode.DATA_NULL, "No available seats found for this flight.");
        }
        return result;
    }

}

