package com.group5.flight.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginSessionInfo implements Serializable {
    private String sessionId;

    private String token;

    private UserInfo userInfo;

    private Integer expireIn;

    @Override
    public String toString() {
        return "LoginSessionInfo{" +
                "sessionId='" + sessionId + '\'' +
                ", token='" + token + '\'' +
                ", userInfo=" + userInfo +
                ", expireIn=" + expireIn +
                '}';
    }
}
