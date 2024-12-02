package com.group5.flight.booking.dao;

import com.group5.flight.booking.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatDao extends JpaRepository<Seat, Long> {
    List<Seat> findByDeletedFalse();
    
    Optional<Seat> findBySeatIdAndDeletedFalse(Long seatId);
    
    List<Seat> findByAvailableTrueAndSeatIdIn(List<Long> seatIds);
}
