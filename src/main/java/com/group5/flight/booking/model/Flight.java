package com.group5.flight.booking.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name= "flight")
@Data
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id")
    private Long flightId;

    @Column(name = "plane_id")
    private Long planeId;

    @Column(name = "airline_id")
    private Long airlineId;

    @Column(name = "from_airport_id")
    private Long fromAirportId;

    @Column(name = "to_airport_id")
    private Long toAirportId;

    @Column(name = "departure_date")
    private Date departureDate;

    @Column(name = "return_date")
    private Date returnDate;

    @Column(name = "base_price")
    private Float basePrice;

    @Column(name = "num_of_passengers")
    private Integer numOfPassengers;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "status")
    private Integer status;
}
