package com.group5.flight.booking.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "airport")
@Data
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airport_id")
    private Long airportId;

    @Column(name = "name")
    private String name;

    @Column(name = "airport_code")
    private String airportCode;

    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "deleted")
    private Boolean deleted;
}
