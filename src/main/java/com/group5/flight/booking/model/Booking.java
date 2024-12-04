package com.group5.flight.booking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "booking")
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;

    @Column(name = "booking_code")
    private Long bookingCode;

    @Column(name = "flight_id")
    private Long flightId;

    @Column(name = "contact_id")
    private Long contactId;

    @Column(name = "invoice_id")
    private Long invoiceId;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "ticket_number")
    private Long ticketNumber;

    @Column(name = "num_of_passengers")
    private Integer numOfPassengers;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "status")
    private Integer status;
}
