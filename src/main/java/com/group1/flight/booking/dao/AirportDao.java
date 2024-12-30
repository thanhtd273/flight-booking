package com.group1.flight.booking.dao;

import com.group1.flight.booking.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportDao extends JpaRepository<Airport, Long> {
    List<Airport> findByDeletedFalse();

    Airport findByAirportIdAndDeletedFalse(Long airportId);
}
