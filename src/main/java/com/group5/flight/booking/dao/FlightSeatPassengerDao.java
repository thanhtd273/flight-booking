package com.group5.flight.booking.dao;

import com.group5.flight.booking.model.FlightSeatPassenger;
import com.group5.flight.booking.model.Seat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlightSeatPassengerDao extends JpaRepository<FlightSeatPassenger, Long> {

    @Query("SELECT fsp FROM FlightSeatPassenger fsp WHERE fsp.flight.id = :flightId")
    List<FlightSeatPassenger> findByFlight(@Param("flightId") Long flightId);

    @Query("SELECT fsp FROM FlightSeatPassenger fsp WHERE fsp.flight.id = :flightId AND fsp.seat.seatNumber = :seatNumber")
    Optional<FlightSeatPassenger> findByFlightAndSeatNumber(@Param("flightId") Long flightId, @Param("seatNumber") String seatNumber);

    @Query("SELECT s FROM Seat s " +
       "JOIN FlightSeatPassenger fsp ON s.id = fsp.seatId " +
       "WHERE fsp.flightId = :flightId AND s.available = true")
    List<Seat> findAvailableSeatsByFlight(@Param("flightId") Long flightId);

}
