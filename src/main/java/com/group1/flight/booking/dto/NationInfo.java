package com.group1.flight.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NationInfo {

    private Long nationId;

    private String name;

    @Override
    public String toString() {
        return "NationInfo{" +
                "nationId=" + nationId +
                ", name='" + name + '\'' +
                '}';
    }
}
