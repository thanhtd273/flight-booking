package com.group1.flight.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PassengerInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1905122041950251207L;

    private String firstName;

    private String lastName;

    private Date birthday;

    private String gender;

    private String email;

    private String phone;

    private Long nationalityId;

    private NationInfo nationInfo;
    public boolean isAllNotNull() {
        return ObjectUtils.isEmpty(firstName) && ObjectUtils.isEmpty(lastName)
                && ObjectUtils.isEmpty(birthday) && ObjectUtils.isEmpty(gender)
                && ObjectUtils.isEmpty(email) && ObjectUtils.isEmpty(phone) && ObjectUtils.isEmpty(nationalityId);
    }

    public boolean isEmptyField() {
        return ObjectUtils.isEmpty(firstName) || ObjectUtils.isEmpty(lastName)
                || ObjectUtils.isEmpty(birthday) || ObjectUtils.isEmpty(nationalityId);
    }

    @Override
    public String toString() {
        return "PassengerInfo{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", nationalityId=" + nationalityId +
                ", nationInfo=" + nationInfo +
                '}';
    }
}
