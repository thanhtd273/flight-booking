package com.group1.flight.booking.dto;

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
    private ContactInfo contactInfo;

    private List<PassengerInfo> passengerInfos;

    public boolean isAllNull() {
        return ObjectUtils.isEmpty(contactInfo) && ObjectUtils.isEmpty(passengerInfos);
    }
}
