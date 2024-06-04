package tdtu.ems.api_gateway.configs;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtValidator {
    public static final String JWT_SECRET = "hYr9jfTUb68bt6B6h8HYu12W3sh19hIJiuRhi2i89ug8yg8UGhUwZai92jbAIaiEq";

    public Claims validateToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(getSignKey()).parseClaimsJws(token).getBody();
        return claims;
    }


    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
