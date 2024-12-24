package com.group5.flight.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 2405172041950251807L;

    private Long bookingId;

    private Long bookingCode;

    private Long flightId;

    private FlightInfo flightInfo;

    private Long departureAirportId;

    private Long destinationAirportId;

    private Date departureDate;

    private Long contactId;

    private ContactInfo contactInfo;

    private Integer numOfPassengers;

    private PassengerInfo[] passengerInfos;

    private SeatInfo[] seatInfos;

    private Long invoiceId;

    private InvoiceInfo invoiceInfo;

    private String paymentMethod;

    private Long ticketNumber;

    @Override
    public String toString() {
        return "BookingInfo{" +
                "bookingId=" + bookingId +
                ", bookingCode=" + bookingCode +
                ", flightId=" + flightId +
                ", flightInfo=" + flightInfo +
                ", departureAirportId=" + departureAirportId +
                ", destinationAirportId=" + destinationAirportId +
                ", departureDate=" + departureDate +
                ", contactId=" + contactId +
                ", contactInfo=" + contactInfo +
                ", numOfPassengers=" + numOfPassengers +
                ", passengerInfos=" + Arrays.toString(passengerInfos) +
                ", seatInfos=" + Arrays.toString(seatInfos) +
                ", invoiceId=" + invoiceId +
                ", invoiceInfo=" + invoiceInfo +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", ticketNumber=" + ticketNumber +
                '}';
    }
}
