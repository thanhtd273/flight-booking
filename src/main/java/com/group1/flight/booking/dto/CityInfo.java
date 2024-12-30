package com.group1.flight.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CityInfo {

    private Long cityId;

    private Long nationId;

    private NationInfo nationInfo;

    private String name;

    @Override
    public String toString() {
        return "CityInfo{" +
                "cityId=" + cityId +
                ", nationId=" + nationId +
                ", nationInfo=" + nationInfo +
                ", name='" + name + '\'' +
                '}';
    }
}
