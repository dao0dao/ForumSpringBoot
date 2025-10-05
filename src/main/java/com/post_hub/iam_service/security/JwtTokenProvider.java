package com.post_hub.iam_service.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.post_hub.iam_service.model.entities.User;
import com.post_hub.iam_service.service.models.AutethicationConstans;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {
    private final SecretKey secretKey;
    private final Long jwtValidityInMilliseconds;

    public JwtTokenProvider(@Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration:3600000}") Long jwtValidityInMilliseconds) {
        this.secretKey = this.getKey(secret);
        this.jwtValidityInMilliseconds = jwtValidityInMilliseconds;
    }

    private SecretKey getKey(String secret) {
        byte[] decode64 = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(decode64);
    }

    public String generateToken(@NonNull User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(AutethicationConstans.USER_ID, user.getId());
        claims.put(AutethicationConstans.USERNAME, user.getUsername());
        return this.createToken(claims, user.getEmail());
    }

    private String createToken(Map<String, Object> claims, String Subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(Subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + this.jwtValidityInMilliseconds))
                .signWith(this.secretKey)
                .compact();
    }

    private Claims getAllClaims(String token) {
        try {
            return Jwts
                .parserBuilder()
                .setSigningKey(this.secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
