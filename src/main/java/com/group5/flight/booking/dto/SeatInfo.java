package com.group5.flight.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SeatInfo {
    private Long flightId;
    private Long seatId;
    private String classLevel;
    private String seatCode;
    private Boolean available;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public boolean isValidForCreate() {
        return !ObjectUtils.isEmpty(classLevel) && !classLevel.trim().isEmpty() &&
               !ObjectUtils.isEmpty(seatCode) && !seatCode.trim().isEmpty();
    }

    public boolean isValidForUpdate() {
        return !ObjectUtils.isEmpty(seatId) &&
               !ObjectUtils.isEmpty(classLevel) && !classLevel.trim().isEmpty() &&
               !ObjectUtils.isEmpty(seatCode) && !seatCode.trim().isEmpty();
    }
}
