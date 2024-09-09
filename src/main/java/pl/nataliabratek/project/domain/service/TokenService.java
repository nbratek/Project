package pl.nataliabratek.project.domain.service;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Service
public class TokenService {

    private static final byte[] KEY_BYTES = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
    private static final SecretKey KEY = Keys.hmacShaKeyFor(KEY_BYTES);

    public String createToken(Integer userId) {
        String jwt = Jwts.builder()
                .subject(userId.toString())
                .signWith(KEY)
                .expiration(new Date(System.currentTimeMillis() + (15 * 60 * 1000)))
                .compact();
        return jwt;
    }

    public boolean checkToken(@Nullable String token)  {
        if(token == null){
            return false;
        }
        String normalizedToken = token.replace("Bearer ", "");
        JwtParser jwtParser = Jwts.parser()
                .verifyWith(KEY)
                .build();
        try {
            jwtParser.parse(normalizedToken);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
    @Nullable
    public Integer getUserId(String token) {
        String normalizedToken = token.replace("Bearer ", "");
        JwtParser jwtParser = Jwts.parser()
                .verifyWith(KEY)
                .build();
        try {
            String userId = jwtParser.parseSignedClaims(normalizedToken).getPayload().getSubject();
            return Integer.parseInt(userId);
        } catch (Exception ignored) {
            return null;
        }
    }
}
