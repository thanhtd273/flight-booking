package com.group5.flight.booking.dao;

import com.group5.flight.booking.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatDao extends JpaRepository<Seat, Long> {
}
