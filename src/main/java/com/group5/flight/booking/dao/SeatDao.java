package com.group5.flight.booking.dao;

import com.group5.flight.booking.model.Flight;
import com.group5.flight.booking.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatDao extends JpaRepository<Seat, Long> {

    List<Seat> findByFlight(Flight flight);

    Optional<Seat> findByFlightAndSeatNumber(Flight flight, String seatNumber);

    boolean existsByFlightAndSeatNumber(Flight flight, String seatNumber);

    List<Seat> findByStatus(String status);

    @Query("SELECT s FROM Seat s WHERE s.flight.id = :flightId AND s.status = 'AVAILABLE'")
    List<Seat> findAvailableSeatsByFlight(@Param("flightId") Long flightId);

    @Query("SELECT s FROM Seat s WHERE s.flight.id = :flightId AND s.seatClass = :seatClass")
    List<Seat> findByFlightAndSeatClass(
        @Param("flightId") Long flightId, 
        @Param("seatClass") String seatClass
    );	

    @Query("SELECT COUNT(s) FROM Seat s WHERE s.flight.id = :flightId AND s.status = 'AVAILABLE'")
    long countAvailableSeatsByFlight(@Param("flightId") Long flightId);

 
    @Modifying
    @Query("UPDATE Seat s SET s.status = :status WHERE s.id = :seatId")
    void updateSeatStatus(@Param("seatId") Long seatId, @Param("status") String status);
}
