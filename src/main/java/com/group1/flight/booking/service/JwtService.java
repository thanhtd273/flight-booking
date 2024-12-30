package com.group1.flight.booking.service;

import com.group1.flight.booking.core.ErrorCode;
import com.group1.flight.booking.model.User;
import io.jsonwebtoken.Claims;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    String extractEmail(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsSolver);

    String generateToken(User user);

    String generateToken(Map<String, Object> extraClaims, User user);

    ErrorCode validateToken(String token, User user);

    int getExpireIn();
}
