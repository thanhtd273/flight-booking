package com.group1.flight.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlaneInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1905122041950251207L;

    private Long planeId;

    private String name;

    private String code;

    private Integer numOfSeats;

    public boolean isAllNotNull() {
        return ObjectUtils.isEmpty(name) && ObjectUtils.isEmpty(code) && ObjectUtils.isEmpty(numOfSeats);
    }

    @Override
    public String toString() {
        return "PlaneInfo{" +
                "planeId=" + planeId +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", numOfSeats=" + numOfSeats +
                '}';
    }
}
