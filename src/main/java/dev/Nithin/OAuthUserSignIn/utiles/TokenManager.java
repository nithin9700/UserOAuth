package dev.Nithin.OAuthUserSignIn.utiles;

import dev.Nithin.OAuthUserSignIn.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.SignatureException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenManager {
    @Value("${SECRET}")
    private String jwtSecret;
    @Value("${EXPIRATION_TIME}")
    private long EXPIRATION_TIME;

    public String getToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("userId", user.getId());
        claims.put("username", user.getUsername());
        claims.put("Issue Time", System.currentTimeMillis());
        claims.put("Expire Time", System.currentTimeMillis() + EXPIRATION_TIME);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();

    }
    public Boolean validateJwtToken(String token, User userDetails) {
        try {
            Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            String username = getUsernameFromToken(claims);
            boolean isTokenExpired = claims.getExpiration().before(new Date());
            return username.equals(userDetails.getUsername()) && !isTokenExpired;
        } catch (SignatureException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    public String getUsernameFromToken(Claims currentToken) {
        return currentToken.getSubject();
    }
    
}
