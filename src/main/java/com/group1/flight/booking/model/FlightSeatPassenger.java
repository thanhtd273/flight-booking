package com.group1.flight.booking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "flight_seat_passenger")
@Data
public class FlightSeatPassenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "flight_id")
    private Long flightId;

    @Column(name = "seat_id")
    private Long seatId;

    @Column(name = "passenger_id")
    private Long passengerId;
}
