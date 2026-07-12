package com.infy.carservice.gateway.util;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;
    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(secret.getBytes()).build().parseClaimsJws(token);
    }
}
