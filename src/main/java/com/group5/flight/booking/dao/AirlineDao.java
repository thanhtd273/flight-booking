package com.group5.flight.booking.dao;

import com.group5.flight.booking.model.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlineDao extends JpaRepository<Airline, Long> {
    Airline findByAirlineIdAndDeletedFalse(Long airlineId);
}
