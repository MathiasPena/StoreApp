package com.storeapp.storeapp.config;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtTokenProvider {

    private static final String SECRET_KEY = "YXNkZmdoamtsbW5vcHFyc3R1dnd4eXphYmNkZWZnaGprbG1ub3BxcnN0dXZ3eHl6YQ=="; // Usa al menos 256 bits.
    private final SecretKey secretKey;

    public JwtTokenProvider() {
        this.secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String createToken(String username, List<String> roles) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + 3600000); // 1 hora de validez

    Map<String, Object> claims = new HashMap<>();
    claims.put("roles", roles); // Agrega roles al payload del token

    return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(secretKey, SignatureAlgorithm.HS512)
            .compact();
}

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}