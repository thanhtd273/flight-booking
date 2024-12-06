package com.group5.flight.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDetail {
    private ContactInfo contact;

    private List<PassengerInfo> passengers;

    public boolean isAllNull() {
        return ObjectUtils.isEmpty(contact) && ObjectUtils.isEmpty(passengers);
    }
}
