package food_delivery_app_backend.Jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${secret.Key}")
    private String secretKey;

    private SecretKey getSecretKey(){return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    private long tokenExpireTime=24*60*60*1000;


    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+tokenExpireTime))
                .signWith(getSecretKey())
                .compact();
    }

    public String extractUserNameFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Boolean isValid(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
