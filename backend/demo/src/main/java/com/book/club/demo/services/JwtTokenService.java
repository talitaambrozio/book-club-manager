package com.book.club.demo.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.book.club.demo.models.ModelUserDetailsImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class JwtTokenService {

    @Value("${token.jwt.secret.key}")
    private String secret_Key;

    @Value("${token.jwt.issuer}")
    private String issuer;

    public String generateToken(ModelUserDetailsImpl user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret_Key);
            return JWT.create()
                    .withIssuer(issuer)
                    .withIssuedAt(creationDate())
                    .withExpiresAt(expirationDate())
                    .withSubject(user.getUsername())
                    .withClaim("roles", user.getAuthorities().stream()
                            .map(authority -> authority.getAuthority())
                            .toList())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new JWTCreationException("Error generating token: ", exception);
        }
    }

    public String getToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret_Key);
            return JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new JWTVerificationException("Invalid or expired token!");
        }
    }

    private Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"))
                .plusHours(5).toInstant();
    }

    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant();
    }

}