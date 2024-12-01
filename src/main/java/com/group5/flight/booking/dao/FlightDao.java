package com.group5.flight.booking.dao;

import com.group5.flight.booking.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FlightDao extends JpaRepository<Flight, Long> {
    @Query("SELECT u FROM Flight u WHERE u.status <> 0")
    Flight findByFlightId(Long flightId);

    @Query("SELECT u FROM Flight u " +
            "WHERE u.fromAirportId = :fromAirportId " +
            "AND u.toAirportId = :toAirportId " +
            "AND DATE(u.departureDate) = DATE(:departureDate) " +
            "ORDER BY u.departureDate")
    List<Flight> findByFromAndToAirportAndDepartureDate(@Param("fromAirportId") Long fromAirportId,
                                                        @Param("toAirportId") Long toAirportId,
                                                        @Param("departureDate") Date departureDate);
}
