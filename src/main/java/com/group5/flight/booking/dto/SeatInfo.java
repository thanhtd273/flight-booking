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
    private Long seatId;

    private Long planeId;

    private PlaneInfo plane;

    private String classLevel;

    private String seatCode;

    private Boolean available;

    public boolean isAllNotNull() {
        return !ObjectUtils.isEmpty(classLevel) && 
               !ObjectUtils.isEmpty(seatCode) && 
               !ObjectUtils.isEmpty(planeId);
    }
}
