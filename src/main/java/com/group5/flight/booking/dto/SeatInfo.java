package com.group5.flight.booking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.group5.flight.booking.model.Seat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatInfo {

    @JsonProperty("flight_id")
    private Long flightId;

    @JsonProperty("seat_number")
    private String seatNumber;

    @JsonProperty("seat_class")
    private String seatClass;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("status")
    private String status;

    public SeatInfo(Long flightId, String seatNumber, String seatClass, BigDecimal price) {
        this.flightId = flightId;
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.price = price;
    }

    public boolean isValidForCreate() {
        return flightId != null &&
               seatNumber != null && !seatNumber.trim().isEmpty() &&
               seatClass != null && !seatClass.trim().isEmpty() &&
    }

    public boolean isValidForUpdate() {
        return flightId != null &&
               seatNumber != null && !seatNumber.trim().isEmpty();
    }
}
