package com.group5.flight.booking.service.impl;

import com.group5.flight.booking.core.AppUtils;
import com.group5.flight.booking.core.DataStatus;
import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.core.exception.LogicException;
import com.group5.flight.booking.dao.UserDao;
import com.group5.flight.booking.dao.UserRoleDao;
import com.group5.flight.booking.dto.OTPInfo;
import com.group5.flight.booking.dto.UpdatePasswordInfo;
import com.group5.flight.booking.dto.UserInfo;
import com.group5.flight.booking.model.Role;
import com.group5.flight.booking.model.User;
import com.group5.flight.booking.model.UserRole;
import com.group5.flight.booking.service.JwtService;
import com.group5.flight.booking.service.MailService;
import com.group5.flight.booking.service.RoleService;
import com.group5.flight.booking.service.UserService;
import jodd.util.StringPool;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final int OTP_EXPIRATION_TIME = 5; // 5 minutes

    private final UserDao userDao;

    private final PasswordEncoder passwordEncoder;

    private final MailService mailService;

    private final JwtService jwtService;

    private final RoleService roleService;

    private final UserRoleDao userRoleDao;

    @Lazy
    private final AuthenticationManager authenticationManager;

    private final Random random = new Random();

    @Override
    @Transactional
    public User createUser(UserInfo userInfo) throws LogicException {
        if (ObjectUtils.isEmpty(userInfo)) {
            throw new LogicException(ErrorCode.DATA_NULL);
        }

        if (ObjectUtils.isEmpty(userInfo.getEmail()) || ObjectUtils.isEmpty(userInfo.getPassword()))
            throw new LogicException(ErrorCode.SIGNUP_FAIL, "Email or password is empty");
        if (!userInfo.getPassword().equals(userInfo.getConfirmPassword()))
            throw new LogicException(ErrorCode.SIGNUP_FAIL, "Password and confirm password do not match");
        if (Boolean.FALSE.equals(AppUtils.validateEmail(userInfo.getEmail())))
            throw new LogicException(ErrorCode.SIGNUP_FAIL, "Invalid email");
        String email = userInfo.getEmail();
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        if (ObjectUtils.isEmpty(userInfo.getName())) {
            String username = email.substring(0, email.indexOf(StringPool.AT));
            user.setName(username);
        } else {
            user.setName(userInfo.getName());
        }
        if (!ObjectUtils.isEmpty(userInfo.getAvatar())) {
            user.setAvatar(userInfo.getAvatar());
        }
        if (!ObjectUtils.isEmpty(userInfo.getStatus())) {
            user.setStatus(userInfo.getStatus());
        } else {
            user.setStatus(DataStatus.PENDING);
        }
        user.setCreatedAt(new Date((System.currentTimeMillis())));

        // Send OTP
        Integer activationCode = generateOtpCode();
        mailService.sendSimpleMail(email, "Account activation code",
                String.format("Activation code: %d. %nThis code will expire in %d minutes.",
                        activationCode, OTP_EXPIRATION_TIME));
        user.setOtpCode(activationCode);
        user.setOtpExpirationTime(new Date(System.currentTimeMillis() + OTP_EXPIRATION_TIME * 60 * 1000));

        user = userDao.save(user);

        // Set roles from user
        if (!ObjectUtils.isEmpty(userInfo.getRoles())) {
            saveUserRoles(user.getUserId(), userInfo.getRoles());
        } else {
            String endUserRole = "End user";
            saveUserRoles(user.getUserId(), endUserRole);
        }
        return user;
    }

    @Override
    public User findByUserId(Long userId) throws LogicException {
        if (ObjectUtils.isEmpty(userId))
            throw new LogicException(ErrorCode.ID_NULL);

        return userDao.findByUserId(userId);
    }

    @Override
    public User findByEmail(String email) throws LogicException {
        return userDao.findByEmail(email);
    }

    @Override
    public UserInfo getUserInfo(User user) throws LogicException {
        if (ObjectUtils.isEmpty(user))
            throw new LogicException(ErrorCode.DATA_NULL);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getUserId());
        userInfo.setEmail(user.getEmail());
        userInfo.setName(user.getName());
        userInfo.setStatus(user.getStatus());
        String roles = user.getRoles().stream().map(Role::getName).collect(Collectors.joining(StringPool.DASH));
        userInfo.setRoles(roles);
        return userInfo;
    }

    @Override
    public UserInfo getUserInfoByToken(String token) throws LogicException {
        String email = jwtService.extractEmail(token);
        User user = findByEmail(email);
        return getUserInfo(user);
    }

    @Override
    public UserInfo getCurrentUser() throws LogicException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) throw new LogicException(ErrorCode.LOGIN_FAIL);
        User user = (User) authentication.getPrincipal();
        return getUserInfo(user);
    }

    @Override
    @Transactional
    public User updateUser(Long userId, UserInfo userInfo) throws LogicException {
        if (ObjectUtils.isEmpty(userInfo))
            throw new LogicException(ErrorCode.DATA_NULL);
        User user = findByUserId(userId);
        if (ObjectUtils.isEmpty(user))
            throw new LogicException(ErrorCode.NOT_FOUND_USER);
        if (!ObjectUtils.isEmpty(userInfo.getName())) {
            user.setName(userInfo.getName());
        }
        if (!ObjectUtils.isEmpty(userInfo.getAvatar())) {
            user.setAvatar(userInfo.getAvatar());
        }
        if (!ObjectUtils.isEmpty(userInfo.getRoles())) {
            saveUserRoles(user.getUserId(), userInfo.getRoles());
        }
        if (!ObjectUtils.isEmpty(userInfo.getStatus())) {
            user.setStatus(userInfo.getStatus());
        }

        user.setUpdatedAt(new Date(System.currentTimeMillis()));
        return userDao.save(user);
    }

    @Override
    public ErrorCode activateUser(OTPInfo activationInfo) throws LogicException {
        ErrorCode verifyResult = verifyOTP(activationInfo);
        if (verifyResult != ErrorCode.SUCCESS) return verifyResult;

        User user = findByEmail(activationInfo.getEmail());
        user.setOtpCode(null);
        user.setOtpExpirationTime(null);
        user.setStatus(DataStatus.ACTIVE);
        userDao.save(user);
        return ErrorCode.SUCCESS;
    }

    @Override
    public ErrorCode deactivateUser(Long userId) throws LogicException {
        User user = findByUserId(userId);
        if (ObjectUtils.isEmpty(user))
            return ErrorCode.NOT_FOUND_USER;

        user.setStatus(DataStatus.DELETED);
        user.setUpdatedAt(new Date(System.currentTimeMillis()));
        userDao.save(user);
        return ErrorCode.SUCCESS;
    }

    @Override
    public ErrorCode changePassword(UpdatePasswordInfo updatePasswordInfo) throws LogicException {
        if (ObjectUtils.isEmpty(updatePasswordInfo))
            return ErrorCode.DATA_NULL;
        User user = findByEmail(updatePasswordInfo.getEmail());
        if (ObjectUtils.isEmpty(user))
            return ErrorCode.NOT_FOUND_USER;

        // Compare old password from request to current password
        if (!user.getPassword().equals(passwordEncoder.encode(updatePasswordInfo.getOldPassword())))
            return ErrorCode.WRONG_PASSWORD;
        user.setPassword(passwordEncoder.encode(updatePasswordInfo.getNewPassword()));
        user.setUpdatedAt(new Date(System.currentTimeMillis()));
        userDao.save(user);
        return ErrorCode.SUCCESS;
    }

    @Override
    public ErrorCode forgotPassword(UserInfo userInfo) throws LogicException {
        if (ObjectUtils.isEmpty(userInfo))
            return ErrorCode.DATA_NULL;

        // Check if user belongs to email is exist on system
        User user = findByEmail(userInfo.getEmail());
        if (ObjectUtils.isEmpty(user))
            return ErrorCode.NOT_FOUND_USER;

        int passwordResetCode = generateOtpCode();
        user.setOtpCode(passwordResetCode);
        user.setOtpExpirationTime(new Date(System.currentTimeMillis() + OTP_EXPIRATION_TIME * 60 * 1000));
        userDao.save(user);
        mailService.sendSimpleMail(userInfo.getEmail(), "Reset password OTP",
                String.format("Reset password code: %s %n This code will expire in %d minutes.", passwordResetCode, OTP_EXPIRATION_TIME));
        return ErrorCode.SUCCESS;
    }

    @Override
    public ErrorCode verifyPasswordResetCode(OTPInfo passwordResetInfo) throws LogicException {
        return verifyOTP(passwordResetInfo);
    }

    @Override
    @Transactional
    public ErrorCode addRole(UserInfo userInfo) throws LogicException {
        if (ObjectUtils.isEmpty(userInfo))
            return ErrorCode.DATA_NULL;
        if (ObjectUtils.isEmpty(userInfo.getRoles()) || ObjectUtils.isEmpty(userInfo.getUserId()))
            return ErrorCode.BLANK_FIELD;

        User user = findByUserId(userInfo.getUserId());
        if (ObjectUtils.isEmpty(user))
            return ErrorCode.NOT_FOUND_USER;
        String[] roleNames = userInfo.getRoles().split(StringPool.DASH);
        for (String roleName : roleNames) {
            Role role = roleService.findByName(roleName);
            if (ObjectUtils.isEmpty(role)) continue;
            UserRole userRole = new UserRole();
            userRole.setUserId(userInfo.getUserId());
            userRole.setRoleId(role.getRoleId());
            userRoleDao.save(userRole);
        }
        user.setUpdatedAt(new Date(System.currentTimeMillis()));
        userDao.save(user);

        return ErrorCode.SUCCESS;
    }

    @Override
    public ErrorCode removeRole(UserInfo userInfo) throws LogicException {
        if (ObjectUtils.isEmpty(userInfo))
            return ErrorCode.DATA_NULL;
        if (ObjectUtils.isEmpty(userInfo.getRoles()) || ObjectUtils.isEmpty(userInfo.getUserId()))
            return ErrorCode.BLANK_FIELD;

        User user = findByUserId(userInfo.getUserId());
        if (ObjectUtils.isEmpty(user))
            return ErrorCode.NOT_FOUND_USER;
        String[] roleNames = userInfo.getRoles().split(StringPool.DASH);
        for (String roleName : roleNames) {
            Role role = roleService.findByName(roleName);
            if (ObjectUtils.isEmpty(role)) continue;
            UserRole userRole = userRoleDao.findByUserIdAndRoleId(userInfo.getUserId(), role.getRoleId());
            userRoleDao.delete(userRole);
        }
        user.setUpdatedAt(new Date(System.currentTimeMillis()));
        userDao.save(user);

        return ErrorCode.SUCCESS;
    }

    private int generateOtpCode() {
        return 100000 + random.nextInt(900000);
    }

    private ErrorCode verifyOTP(OTPInfo otpInfo) throws LogicException{
        if (ObjectUtils.isEmpty(otpInfo))
            return ErrorCode.DATA_NULL;
        if (ObjectUtils.isEmpty(otpInfo.getCode()))
            return ErrorCode.BLANK_FIELD;
        User user = findByEmail(otpInfo.getEmail());
        if (ObjectUtils.isEmpty(user))
            return ErrorCode.NOT_FOUND_USER;
        if (ObjectUtils.isEmpty(user.getOtpExpirationTime()) || System.currentTimeMillis() > user.getOtpExpirationTime().getTime()) {
            return ErrorCode.EXPIRED_CODE;
        }
        if (!Objects.equals(otpInfo.getCode(), user.getOtpCode()))
            return ErrorCode.INVALID_CODE;

        return ErrorCode.SUCCESS;
    }

    private void saveUserRoles(Long userId, String roleStr) throws LogicException {
        List<String> rolesName = Arrays.stream(roleStr.trim().split(StringPool.DASH)).toList();
        for (String roleName: rolesName) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            Role role = roleService.findByName(roleName);
            if (ObjectUtils.isEmpty(role)) continue;
            userRole.setRoleId(role.getRoleId());
            userRoleDao.save(userRole);
        }
    }
}
