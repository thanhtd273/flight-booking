package com.group5.flight.booking.dao;

import com.group5.flight.booking.model.FlightSeatPassenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightSeatPassengerDao extends JpaRepository<FlightSeatPassenger, Long> {

    FlightSeatPassenger findByFlightIdAndSeatId(Long flightId, Long seatId);
}
