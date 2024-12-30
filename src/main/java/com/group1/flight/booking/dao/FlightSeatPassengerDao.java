package com.group1.flight.booking.dao;

import com.group1.flight.booking.model.FlightSeatPassenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightSeatPassengerDao extends JpaRepository<FlightSeatPassenger, Long> {

    FlightSeatPassenger findByFlightIdAndSeatId(Long flightId, Long seatId);

    List<FlightSeatPassenger> findByFlightId(Long flightId);
}
