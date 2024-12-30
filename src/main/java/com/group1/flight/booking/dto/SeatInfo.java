package com.group1.flight.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1905122041950251207L;

    private Long seatId;

    private Long planeId;

    private PlaneInfo planeInfo;

    private String classLevel;

    private String seatCode;

    private Boolean available;

    public boolean isAllNotNull() {
        return !ObjectUtils.isEmpty(classLevel) && 
               !ObjectUtils.isEmpty(seatCode) && 
               !ObjectUtils.isEmpty(planeId);
    }

    @Override
    public String toString() {
        return "SeatInfo{" +
                "seatId=" + seatId +
                ", planeId=" + planeId +
                ", planeInfo=" + planeInfo +
                ", classLevel='" + classLevel + '\'' +
                ", seatCode='" + seatCode + '\'' +
                ", available=" + available +
                '}';
    }
}
