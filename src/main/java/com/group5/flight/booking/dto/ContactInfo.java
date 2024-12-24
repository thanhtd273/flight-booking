package com.group5.flight.booking.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactInfo {

    private Long contactId;

    private String firstName;

    private String lastName;

    private String phone;

    private String email;

    public boolean isAllNull() {
        return ObjectUtils.isEmpty(firstName) && ObjectUtils.isEmpty(lastName)
                && ObjectUtils.isEmpty(phone) && ObjectUtils.isEmpty(email);
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
