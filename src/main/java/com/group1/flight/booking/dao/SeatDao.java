package com.group1.flight.booking.dao;

import com.group1.flight.booking.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatDao extends JpaRepository<Seat, Long> {
    List<Seat> findAll();

    Optional<Seat> findBySeatId(Long seatId);

    List<Seat> findByPlaneId(Long planeId);
}