package com.group5.flight.booking.service.impl;

import com.group5.flight.booking.core.ErrorCode;
import com.group5.flight.booking.model.User;
import com.group5.flight.booking.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${fb.jwt.secret}")
    private String secretKey;

    @Value("${fb.jwt.expires-in}")
    private int expiresIn;

    @Override
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsSolver) {
        final Claims claims = extractAllClaims(token);
        return claimsSolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public String generateToken(User user) {
        return generateToken(new HashMap<>(), user);
    }

    @Override
    public String generateToken(Map<String, Object> extraClaims, User user) {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        return Jwts
                .builder()
                .setHeader(header)
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiresIn * 1000L))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public ErrorCode validateToken(String token, User user) {
        if (ObjectUtils.isEmpty(user)) return ErrorCode.INVALID_TOKEN;
        String email = extractEmail(token);
        if (!email.equals(user.getUsername()) && isTokenExpired(token)) return ErrorCode.INVALID_TOKEN;
        return ErrorCode.VALID_TOKEN;

    }

    @Override
    public int getExpireIn() {
        return expiresIn;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
