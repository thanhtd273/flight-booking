package com.group5.flight.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingInfo {

    private Long bookingId;

    private Long bookingCode;

    private Long flightId;

    private FlightInfo flight;

    private Long departureAirportId;

    private Long destinationAirportId;

    private Date departureDate;

    private Long contactId;

    private ContactInfo contact;

    private Integer numOfPassengers;

    private PassengerInfo[] passengers;

    private SeatInfo[] seats;

    private Long invoiceId;

    private InvoiceInfo invoice;

    private String paymentMethod;

    private Long ticketNumber;

    @Override
    public String toString() {
        return "BookingInfo{" +
                "bookingCode=" + bookingCode +
                ", flightId=" + flightId +
                ", flight=" + flight +
                ", departureAirportId=" + departureAirportId +
                ", destinationAirportId=" + destinationAirportId +
                ", departureDate=" + departureDate +
                ", contactId=" + contactId +
                ", contact=" + contact +
                ", numOfPassengers=" + numOfPassengers +
                ", passengers=" + Arrays.toString(passengers) +
                ", seats=" + Arrays.toString(seats) +
                ", invoiceId=" + invoiceId +
                ", invoice=" + invoice +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", ticketNumber=" + ticketNumber +
                '}';
    }
}
