package com.group5.flight.booking.service.impl;

import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dao.SeatDao;
import com.group5.flight.booking.dao.FlightDao;
import com.group5.flight.booking.dto.SeatInfo;
import com.group5.flight.booking.model.Seat;
import com.group5.flight.booking.model.Flight;
import com.group5.flight.booking.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.sql.Timestamp;
import java.time.Instant;

@Service
@Transactional
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private static final Logger logger = LoggerFactory.getLogger(SeatServiceImpl.class);
    private final SeatDao seatDao;
    private final FlightDao flightDao;

    @Override
    public List<Seat> getAllSeats() {
        return seatDao.findAll();
    }

    @Override
    public List<Seat> getSeatsByFlight(Long flightId) throws LogicException {
        Flight flight = flightDao.findById(flightId)
            .orElseThrow(() -> new LogicException(ErrorCode.NOT_FOUND, 
                String.format("Flight with id %d not found", flightId)));
        return seatDao.findByFlight(flight);
    }

    @Override
    public Seat findBySeatId(Long id) throws LogicException {
        return seatDao.findById(id)
            .orElseThrow(() -> new LogicException(ErrorCode.NOT_FOUND, 
                String.format("Seat with id %d not found", id)));
    }

    @Override
    public Seat create(SeatInfo seatInfo) throws LogicException {
        if (ObjectUtils.isEmpty(seatInfo)) {
            throw new LogicException(ErrorCode.DATA_NULL, "Seat info is empty");
        }
        validateSeatInfo(seatInfo);

        Flight flight = flightDao.findById(seatInfo.getFlightId())
            .orElseThrow(() -> new LogicException(ErrorCode.NOT_FOUND, 
                String.format("Flight with id %d not found", seatInfo.getFlightId())));

        if (seatDao.existsByFlightAndSeatNumber(flight, seatInfo.getSeatCode())) {
            throw new LogicException(ErrorCode.DUPLICATE, 
                String.format("Seat number %s already exists in flight %d", 
                    seatInfo.getSeatCode(), seatInfo.getFlightId()));
        }

        Seat seat = new Seat();
        seat.setClassLevel(seatInfo.getClassLevel());
        seat.setSeatCode(seatInfo.getSeatCode());
        seat.setAvailable(seatInfo.getAvailable() != null ? seatInfo.getAvailable() : true);
        seat.setCreatedAt(Timestamp.from(Instant.now()));
        seat.setUpdatedAt(Timestamp.from(Instant.now()));

        logger.info("Creating new seat {} for flight {}", seatInfo.getSeatCode(), seatInfo.getFlightId());
        return seatDao.save(seat);
    }

    @Override
    public Seat update(Long id, SeatInfo seatInfo) throws LogicException {
        validateSeatInfo(seatInfo);
        
        Seat existingSeat = findBySeatId(id);
        Flight flight = flightDao.findById(seatInfo.getFlightId())
            .orElseThrow(() -> new LogicException(ErrorCode.NOT_FOUND, 
                String.format("Flight with id %d not found", seatInfo.getFlightId())));

        if (!existingSeat.getSeatCode().equals(seatInfo.getSeatCode()) &&
            seatDao.existsByFlightAndSeatNumber(flight, seatInfo.getSeatCode())) {
            throw new LogicException(ErrorCode.DUPLICATE, 
                String.format("Seat number %s already exists in flight %d", 
                    seatInfo.getSeatCode(), seatInfo.getFlightId()));
        }

        existingSeat.setClassLevel(seatInfo.getClassLevel());
        existingSeat.setSeatCode(seatInfo.getSeatCode());

        logger.info("Updating seat {} for flight {}", id, seatInfo.getFlightId());
        return seatDao.save(existingSeat);
    }
    

    @Override
    public ErrorCode delete(Long id) throws LogicException {
        Seat seat = findBySeatId(id);
        
        if (!seat.getAvailable()) {
            throw new LogicException(ErrorCode.INVALID_OPERATION, 
                "Cannot delete a seat that is not available");
        }

        logger.info("Deleting seat {}", id);
        seatDao.delete(seat);
        return ErrorCode.SUCCESS;
    }

    @Override
    public List<Seat> getAvailableSeats() {
        return seatDao.findByStatus("AVAILABLE");
    }

    @Override
    public void updateSeatStatus(Long id, Boolean isAvailable) throws LogicException {
        Seat seat = findBySeatId(id);
        seat.setAvailable(isAvailable);
        
        logger.info("Updating status of seat {} to {}", id, isAvailable ? "AVAILABLE" : "BOOKED");
        seatDao.save(seat);
    }

    private void validateSeatInfo(SeatInfo info) throws LogicException {
        if (info.getFlightId() == null) {
            throw new LogicException(ErrorCode.INVALID_INPUT, "Flight ID is required");
        }
        if (!StringUtils.hasText(info.getSeatCode())) {
            throw new LogicException(ErrorCode.INVALID_INPUT, "Seat code is required");
        }
        if (!StringUtils.hasText(info.getClassLevel())) {
            throw new LogicException(ErrorCode.INVALID_INPUT, "Seat class is required");
        }
    }
}
