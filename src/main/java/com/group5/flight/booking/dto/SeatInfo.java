package com.group5.flight.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatInfo {
    private String classLevel;
    private String seatCode;
    private Boolean available;
    private Long flightId;  
    private Long passengerId;  

    public boolean isAllNotNull() {
        return !ObjectUtils.isEmpty(classLevel) && 
               !ObjectUtils.isEmpty(seatCode) && 
               !ObjectUtils.isEmpty(available);
    }
}
