package com.group5.flight.booking.service;


import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dto.OTPInfo;
import com.group5.flight.booking.dto.UpdatePasswordInfo;
import com.group5.flight.booking.dto.UserInfo;
import com.group5.flight.booking.model.User;

public interface UserService {
    User createUser(UserInfo userInfo) throws LogicException;

    User findByUserId(Long userId) throws LogicException;

    User findByEmail(String email) throws LogicException;

    UserInfo getUserInfo(User user) throws LogicException;

    UserInfo getUserInfoByToken(String token) throws LogicException;

    UserInfo getCurrentUser() throws LogicException;

    User updateUser(Long userId, UserInfo userInfo) throws LogicException;

    ErrorCode activateUser(String email, Integer code) throws LogicException;

    ErrorCode deactivateUser(Long userId) throws LogicException;

    ErrorCode changePassword(UpdatePasswordInfo updatePasswordInfo) throws LogicException;

    ErrorCode forgotPassword(UserInfo userInfo) throws LogicException;

    ErrorCode verifyPasswordResetCode(String email, Integer code) throws LogicException;

    ErrorCode addRole(UserInfo userInfo) throws LogicException;

    ErrorCode removeRole(UserInfo userInfo) throws LogicException;

}
