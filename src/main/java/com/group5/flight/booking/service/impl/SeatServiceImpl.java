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

import java.util.List;
import java.math.BigDecimal;

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
        validateSeatInfo(seatInfo);
        
        Flight flight = flightDao.findById(seatInfo.getFlightId())
            .orElseThrow(() -> new LogicException(ErrorCode.NOT_FOUND, 
                String.format("Flight with id %d not found", seatInfo.getFlightId())));

        if (seatDao.existsByFlightAndSeatNumber(flight, seatInfo.getSeatNumber())) {
            throw new LogicException(ErrorCode.DUPLICATE, 
                String.format("Seat number %s already exists in flight %d", 
                    seatInfo.getSeatNumber(), flight.getFlightId()));
        }

        Seat seat = new Seat();
        seat.setClassLevel(seatInfo.getSeatClass());
        seat.setSeatCode(seatInfo.getSeatNumber());
        seat.setAvailable(true); // Assuming new seats are available by default

        logger.info("Creating new seat {} for flight {}", seatInfo.getSeatNumber(), flight.getId());
        return seatDao.save(seat);
    }

    @Override
    public Seat update(Long id, SeatInfo seatInfo) throws LogicException {
        validateSeatInfo(seatInfo);
        
        Seat existingSeat = findBySeatId(id);
        Flight flight = flightDao.findById(seatInfo.getFlightId())
            .orElseThrow(() -> new LogicException(ErrorCode.NOT_FOUND, 
                String.format("Flight with id %d not found", seatInfo.getFlightId())));

        if (!existingSeat.getSeatCode().equals(seatInfo.getSeatNumber()) &&
            seatDao.existsByFlightAndSeatNumber(flight, seatInfo.getSeatNumber())) {
            throw new LogicException(ErrorCode.DUPLICATE, 
                String.format("Seat number %s already exists in flight %d", 
                    seatInfo.getSeatNumber(), flight.getId()));
        }

        existingSeat.setClassLevel(seatInfo.getSeatClass());
        existingSeat.setSeatCode(seatInfo.getSeatNumber());

        logger.info("Updating seat {} for flight {}", id, flight.getId());
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
        if (!StringUtils.hasText(info.getSeatNumber())) {
            throw new LogicException(ErrorCode.INVALID_INPUT, "Seat number is required");
        }
        if (!StringUtils.hasText(info.getSeatClass())) {
            throw new LogicException(ErrorCode.INVALID_INPUT, "Seat class is required");
        }
    }
}
