package com.group5.flight.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PassengerInfo {
    private String firstName;

    private String lastName;

    private Date birthday;

    private String gender;

    private String email;

    private String phone;

    private Long nationalityId;

    private NationInfo nation;
    public boolean isAllNotNull() {
        return ObjectUtils.isEmpty(firstName) && ObjectUtils.isEmpty(lastName)
                && ObjectUtils.isEmpty(birthday) && ObjectUtils.isEmpty(gender)
                && ObjectUtils.isEmpty(email) && ObjectUtils.isEmpty(phone) && ObjectUtils.isEmpty(nationalityId);
    }
}
