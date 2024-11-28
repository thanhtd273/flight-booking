package com.group5.flight.booking.service.impl;

import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dao.SeatDao;
import com.group5.flight.booking.dao.FlightSeatPassengerDao;
import com.group5.flight.booking.dto.SeatInfo;
import com.group5.flight.booking.model.Seat;
import com.group5.flight.booking.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private static final Logger logger = LoggerFactory.getLogger(SeatServiceImpl.class);
    private final SeatDao seatDao;
    private final FlightSeatPassengerDao flightSeatPassengerDao;

    @Override
    public List<Seat> getAllSeats() {
        logger.info("Fetching all seats");
        return seatDao.findAll();
    }

    @Override
    public Seat findBySeatId(Long id) throws LogicException {
        logger.info("Finding seat by ID: {}", id);
        return seatDao.findById(id)
            .orElseThrow(() -> new LogicException(ErrorCode.NOT_FOUND, 
                String.format("Seat with id %d not found", id)));
    }

    @Override
    public Seat create(SeatInfo seatInfo) throws LogicException {
        logger.info("Creating seat with info: {}", seatInfo);
        if (ObjectUtils.isEmpty(seatInfo)) {
            throw new LogicException(ErrorCode.DATA_NULL, "Seat info is empty");
        }
        validateSeatInfo(seatInfo);

        if (seatDao.existsBySeatCode(seatInfo.getSeatCode())) {
            throw new LogicException(ErrorCode.DUPLICATE, 
                String.format("Seat code %s already exists", seatInfo.getSeatCode()));
        }

        Seat seat = new Seat();
        seat.setClassLevel(seatInfo.getClassLevel());
        seat.setSeatCode(seatInfo.getSeatCode());
        seat.setAvailable(seatInfo.getAvailable() != null ? seatInfo.getAvailable() : true);
        seat.setCreatedAt(Timestamp.from(Instant.now()));
        seat.setUpdatedAt(Timestamp.from(Instant.now()));

        return seatDao.save(seat);
    }

    @Override
    public ErrorCode delete(Long id) throws LogicException {
        logger.info("Deleting seat with ID: {}", id);
        Seat seat = findBySeatId(id);
        
        if (!seat.getAvailable()) {
            throw new LogicException(ErrorCode.INVALID_OPERATION, 
                "Cannot delete a seat that is not available");
        }

        seatDao.delete(seat);
        return ErrorCode.SUCCESS;
    }

    @Override
    public List<Seat> getAvailableSeats() {
        logger.info("Fetching available seats");
        return seatDao.findByStatus("AVAILABLE");
    }

    @Override
    public void updateSeatStatus(Long id, Boolean isAvailable) throws LogicException {
        logger.info("Updating seat status for ID: {} to {}", id, isAvailable);
        Seat seat = findBySeatId(id);
        seat.setAvailable(isAvailable);
        seatDao.save(seat);
    }

    @Override
    public List<Seat> getAvailableSeatsByFlight(Long flightId) throws LogicException {
        if (flightId == null) {
            throw new LogicException(ErrorCode.INVALID_INPUT, "Flight ID cannot be null");
        }
        return flightSeatPassengerDao.findAvailableSeatsByFlight(flightId);
    }
    
    private void validateSeatInfo(SeatInfo info) throws LogicException {
        if (!StringUtils.hasText(info.getSeatCode())) {
            throw new LogicException(ErrorCode.INVALID_INPUT, "Seat code is required");
        }
        if (!StringUtils.hasText(info.getClassLevel())) {
            throw new LogicException(ErrorCode.INVALID_INPUT, "Seat class is required");
        }
    }
}
