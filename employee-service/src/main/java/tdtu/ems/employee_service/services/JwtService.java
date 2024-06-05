package tdtu.ems.employee_service.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import tdtu.ems.employee_service.models.MyUserDetails;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    public static final String JWT_SECRET = "hYr9jfTUb68bt6B6h8HYu12W3sh19hIJiuRhi2i89ug8yg8UGhUwZai92jbAIaiEq";
    public static final long JWT_EXPIRATION = 1000 * 60 * 30;

    public Claims validateToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(getSignKey()).parseClaimsJws(token).getBody();
        return claims;
    }

    public String generateToken(MyUserDetails user) {
//        Map<String, Object> claims = new HashMap<>();
        String token = Jwts.builder()
//                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, getSignKey())
//                .claim("userDetails", user)
                .compact();
        return token;
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
