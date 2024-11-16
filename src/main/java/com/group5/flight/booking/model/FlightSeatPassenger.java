package com.group5.flight.booking.model;

import jakarta.persistence.*;
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
