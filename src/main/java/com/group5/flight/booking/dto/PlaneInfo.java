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
public class PlaneInfo {
    private String name;

    private String code;

    public boolean isAllNotNull() {
        return ObjectUtils.isEmpty(name) && ObjectUtils.isEmpty(code);
    }
}
