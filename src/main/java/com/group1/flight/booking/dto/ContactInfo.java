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
@AllArgsConstructor
@NoArgsConstructor
public class ContactInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1905122041950251207L;

    private Long contactId;

    private String firstName;

    private String lastName;

    private String phone;

    private String email;

    public boolean isAllNull() {
        return ObjectUtils.isEmpty(firstName) && ObjectUtils.isEmpty(lastName)
                && ObjectUtils.isEmpty(phone) && ObjectUtils.isEmpty(email);
    }

    public boolean isEmptyField() {
        return ObjectUtils.isEmpty(firstName) || ObjectUtils.isEmpty(lastName)
                || ObjectUtils.isEmpty(phone) || ObjectUtils.isEmpty(email);
    }

    @Override
    public String toString() {
        return "ContactInfo{" +
                "contactId=" + contactId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
