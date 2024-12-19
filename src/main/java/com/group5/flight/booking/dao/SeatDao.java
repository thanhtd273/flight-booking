package com.group5.flight.booking.dao;

import com.group5.flight.booking.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatDao extends JpaRepository<Seat, Long> {
    List<Seat> findAll();

    Optional<Seat> findBySeatId(Long seatId);

    List<Seat> findByAvailableTrueAndSeatIdIn(List<Long> seatIds);

    @Query("SELECT s.classLevel, COUNT(s) " +
            "FROM Seat s " +
            "JOIN FlightSeatPassenger fsp ON s.seatId = fsp.seatId " +
            "WHERE fsp.flightId = :flightId AND s.available = true " +
            "GROUP BY s.classLevel")
    List<Object[]> countAvailableSeatsByClass(@Param("flightId") Long flightId);
}