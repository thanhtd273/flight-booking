package com.group1.flight.booking.dao;

import com.group1.flight.booking.model.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirlineDao extends JpaRepository<Airline, Long> {
    List<Airline> findByDeletedFalse();
    Airline findByAirlineIdAndDeletedFalse(Long airlineId);
}
