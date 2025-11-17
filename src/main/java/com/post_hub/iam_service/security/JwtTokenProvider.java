package com.post_hub.iam_service.security;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.post_hub.iam_service.model.constans.ApiErrorMessage;
import com.post_hub.iam_service.model.entities.User;
import com.post_hub.iam_service.model.exception.NoAuthorizationException;
import com.post_hub.iam_service.model.exception.NotFoundException;
import com.post_hub.iam_service.security.model.CustomUserDetails;
import com.post_hub.iam_service.service.models.AutethicationConstans;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {
    private final SecretKey secretKey;
    private final Long jwtValidityInMilliseconds;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration:3600000}") Long jwtValidityInMilliseconds) {
        this.secretKey = this.getKey(secret);
        this.jwtValidityInMilliseconds = jwtValidityInMilliseconds;
    }

    private SecretKey getKey(String secret) {
        byte[] decode64 = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(decode64);
    }

    public String generateToken(@NonNull CustomUserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(AutethicationConstans.USER_ID, userDetails.getUserId());
        claims.put(AutethicationConstans.USER_EMAIL, userDetails.getUsername());
        claims.put(AutethicationConstans.ROLES, userDetails.getAuthorities().stream().map(authorities -> authorities.getAuthority()).toList());
        return this.createToken(claims, userDetails.getUserEmail());
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
            throw new NoAuthorizationException(ApiErrorMessage.TOKEN_EXPIRED.getMessage());
        } catch (JwtException | IllegalArgumentException e) {
            throw new NotFoundException(ApiErrorMessage.UNEXPECTED_ERROR_OCCUR.getMessage());
        }
    }

    public String refreshToken(String token) {
        Claims claims = this.getAllClaims(token);
        return this.createToken(claims, claims.getSubject());
    }

    public boolean isValidToken(String token) {
        Claims claims = this.getAllClaims(token);
        return claims.getExpiration().after(new Date());
    }

    public Integer getUserId(String token) {
        Claims claims = this.getAllClaims(token);
        Object userId = claims.get(AutethicationConstans.USER_ID);
        try {
            return Integer.parseInt(userId.toString());
        } catch (Exception e) {
            throw new NotFoundException(ApiErrorMessage.USER_NOT_FOUND.getMessage());
        }
    }

    public String getUserEmail(String token) {
        Claims claims = this.getAllClaims(token);
        Object userEmail = claims.get(AutethicationConstans.USER_EMAIL);
        try {
            return userEmail.toString();
        } catch (Exception e) {
            throw new NotFoundException(ApiErrorMessage.USER_NOT_FOUND.getMessage());
        }
    }

    public List<String> getUserRoles(String token) {
        Claims claims = this.getAllClaims(token);
        Object roles = claims.get(AutethicationConstans.ROLES);
        List<?> list = (List<?>) roles;
        try {
            return list.stream().map(Object::toString).toList();
        } catch (Exception e) {
            throw new NotFoundException(ApiErrorMessage.USER_NOT_FOUND.getMessage());

        }
    }
}
