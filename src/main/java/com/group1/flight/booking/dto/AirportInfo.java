package com.group1.flight.booking.dto;

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
    private Long airportId;

    private String name;

    private String airportCode;

    private Long cityId;

    private CityInfo cityInfo;

    public boolean isAllNotNull() {
        return ObjectUtils.isEmpty(name) && ObjectUtils.isEmpty(airportCode) && ObjectUtils.isEmpty(cityId);
    }

    @Override
    public String toString() {
        return "AirportInfo{" +
                "airportId=" + airportId +
                ", name='" + name + '\'' +
                ", airportCode='" + airportCode + '\'' +
                ", cityId=" + cityId +
                ", cityInfo=" + cityInfo +
                '}';
    }
}
