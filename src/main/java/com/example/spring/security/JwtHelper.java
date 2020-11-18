package com.example.spring.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@Component
@Slf4j
public class JwtHelper {
    private final String secret;
    public JwtHelper() {
        //secret = UUID.randomUUID().toString();
        secret = "secret";
    }

    public String generateToken(Authentication auth){
        log.info("Signing Jwt with "+auth.getName());
        return Jwts.builder()
                .setSubject(auth.getName())
                .claim("authorities",auth.getAuthorities())
                .setIssuedAt(Date.valueOf(LocalDate.now()))
                .setExpiration(Date.valueOf(LocalDate.now().plusDays(1)))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }

    public Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }
}
