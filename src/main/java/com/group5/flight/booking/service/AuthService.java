package com.group5.flight.booking.service;

import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dto.Credential;
import com.group5.flight.booking.dto.LoginSessionInfo;
import com.group5.flight.booking.dto.UserInfo;
import com.group5.flight.booking.model.User;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    UserInfo verifyToken(String token) throws LogicException;

    LoginSessionInfo login(HttpServletRequest request, Credential credential) throws LogicException;

    User signUp(UserInfo userInfo) throws LogicException;
ErrorCode forgotPassword(UserInfo userInfo) throws LogicException;  //quoc added
	ErrorCode changePassword(UpdatePasswordInfo updatePasswordInfo) throws LogicException;  //quoc added

    ErrorCode verifyPasswordResetCode(OTPInfo passwordResetInfo) throws LogicException;  //quoc added
}
