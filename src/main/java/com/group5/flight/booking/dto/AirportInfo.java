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
<<<<<<< HEAD
    public String getAirportName(){
=======
    // Thêm phương thức getter cho tên sân bay
    public String getAirportName() {
>>>>>>> 2bfdae384b7f21060d8ed15bb5273a6c31e9c2d9
        return name;
    }
}
