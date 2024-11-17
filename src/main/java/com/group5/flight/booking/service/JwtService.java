package com.group5.flight.booking.service;

import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.model.User;
import io.jsonwebtoken.Claims;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    //String extractEmail(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsSolver);

   private final String SECRET_KEY = "your-secret-key"; 

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // expired after 1 hour.
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public ErrorCode validateToken(String token, User user) {
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            if (!claims.getSubject().equals(user.getEmail())) {
                return ErrorCode.INVALID_TOKEN;
            }
            return ErrorCode.SUCCESS;
        } catch (Exception e) {
            return ErrorCode.INVALID_TOKEN;
        }
    }

    public int getExpireIn() {
        return 3600; // Token expired.
    }
}
