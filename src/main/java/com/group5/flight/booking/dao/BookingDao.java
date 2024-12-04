package com.group5.flight.booking.dao;

import com.group5.flight.booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingDao extends JpaRepository<Booking, Long> {
    @Query("SELECT u FROM Booking u WHERE u.status <> 0")
    List<Booking> findALlBookings();

    @Query("SELECT u FROM Booking u WHERE u.status <> 0 AND u.bookingId = :bookingId")
    Booking findByBookingId(@Param("bookingId") Long bookingId);
}
