package com.group1.flight.booking.service;

import com.group1.flight.booking.core.exception.LogicException;
import com.group1.flight.booking.dto.LoginSessionInfo;
import com.group1.flight.booking.dto.UserInfo;
import com.group1.flight.booking.model.User;

public interface AuthService {
    UserInfo verifyToken(String token) throws LogicException;

    LoginSessionInfo login(String email, String password) throws LogicException;

    User signUp(UserInfo userInfo) throws LogicException;
}
