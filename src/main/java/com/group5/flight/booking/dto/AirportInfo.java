package com.group5.flight.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AirportInfo {
    private String name;

    private String airportCode;

    private Long cityId;

    private CityInfo city;

    public boolean isAllNotNull() {
        return ObjectUtils.isEmpty(name) && ObjectUtils.isEmpty(airportCode) && ObjectUtils.isEmpty(cityId);
    }
    // Thêm phương thức getter cho tên sân bay
    public String getAirportName() {
        return name;
    }
}
