package com.group1.flight.booking.service.impl;

import com.group1.flight.booking.core.ErrorCode;
import com.group1.flight.booking.core.exception.LogicException;
import com.group1.flight.booking.dao.SeatDao;
import com.group1.flight.booking.dto.SeatInfo;
import com.group1.flight.booking.model.FlightSeatPassenger;
import com.group1.flight.booking.model.Seat;
import com.group1.flight.booking.service.FlightSeatPassengerService;
import com.group1.flight.booking.service.PlaneService;
import com.group1.flight.booking.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatDao seatDao;

    private final FlightSeatPassengerService flightSeatPassengerService;

    private final PlaneService planeService;

    @Override
    public List<Seat> getAllSeats() {
        return seatDao.findAll();
    }

    @Override
    public Seat findBySeatId(Long seatId) throws LogicException {
        return seatDao.findBySeatId(seatId)
                .orElseThrow(() -> new LogicException(ErrorCode.DATA_NULL, "Seat not found"));
    }

    @Override
    public List<Seat> findByPlaneId(Long planeId) throws LogicException {
        return seatDao.findByPlaneId(planeId);
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
        seat.setPlaneId(seatInfo.getPlaneId());
        seat.setCreatedAt(new Date(System.currentTimeMillis()));

        return seatDao.save(seat);
    }
    @Override
    public Seat update(Long seatId, SeatInfo seatInfo) throws LogicException {
        if (ObjectUtils.isEmpty(seatInfo)) {
            throw new LogicException(ErrorCode.DATA_NULL, "Seat info is null");
        }
        Seat seat = findBySeatId(seatId);
        if (ObjectUtils.isEmpty(seat)) {
            throw new LogicException(ErrorCode.DATA_NULL, "Seat does not exist");
        }
        if (!ObjectUtils.isEmpty(seatInfo.getClassLevel())) {
            seat.setClassLevel(seatInfo.getClassLevel());
        }
        if (!ObjectUtils.isEmpty(seatInfo.getSeatCode())) {
            seat.setSeatCode(seatInfo.getSeatCode());
        }

        seat.setUpdatedAt(new Date(System.currentTimeMillis()));
        return seatDao.save(seat);
    }

    @Override
    public ErrorCode delete(Long seatId) throws LogicException {
        Seat seat = findBySeatId(seatId);
        if (ObjectUtils.isEmpty(seat))
            return ErrorCode.DATA_NULL;
        seat.setUpdatedAt(new Date(System.currentTimeMillis()));
        seatDao.save(seat);
        return ErrorCode.SUCCESS;
    }

    @Override
    public SeatInfo getSeatInfo(Long seatId, Long flightId) throws LogicException {
        Seat seat = findBySeatId(seatId);
        if (ObjectUtils.isEmpty(seat)) {
            throw new LogicException(ErrorCode.DATA_NULL, "Seat not found");
        }

        SeatInfo seatInfo = new SeatInfo();
        seatInfo.setSeatId(seatId);
        seatInfo.setClassLevel(seat.getClassLevel());
        seatInfo.setSeatCode(seat.getSeatCode());
        FlightSeatPassenger flightSeatPassenger = flightSeatPassengerService.findByFlightIdAndSeatId(flightId, seatId);
        seatInfo.setAvailable(ObjectUtils.isEmpty(flightSeatPassenger));
        seatInfo.setPlaneId(seat.getPlaneId());
        seatInfo.setPlaneInfo(planeService.getPlaneInfo(seat.getPlaneId()));

        return seatInfo;
    }

}

