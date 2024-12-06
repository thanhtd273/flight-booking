package com.group5.flight.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingInfo {
    private Long bookingCode;

    private Long flightId;

    private FlightInfo flight;

    private Long contactId;

    private ContactInfo contact;

    private Long invoiceId;

    private InvoiceInfo invoice;

    private String paymentMethod;

    private Long ticketNumber;

    private Integer numOfPassengers;

}
