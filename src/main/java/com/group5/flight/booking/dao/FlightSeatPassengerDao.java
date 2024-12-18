package com.group5.flight.booking.dao;

import com.group5.flight.booking.model.FlightSeatPassenger;
import com.group5.flight.booking.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightSeatPassengerDao extends JpaRepository<FlightSeatPassenger, Long> {
    @Query("SELECT s FROM Seat s JOIN FlightSeatPassenger fsp ON s.seatId = fsp.seatId WHERE fsp.flightId = :flightId AND s.available = true")
    List<Seat> findAvailableSeatsByFlightId(@Param("flightId") Long flightId);

}
