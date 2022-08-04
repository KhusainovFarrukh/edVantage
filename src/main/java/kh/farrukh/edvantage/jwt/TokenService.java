package kh.farrukh.edvantage.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Service
public class TokenService implements InitializingBean {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.token-validity-in-minutes}")
    private Long tokenValidityMinutes;

    private Key key;

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(TokenBody tokenBody, Date expireDate) {
        return Jwts.builder()
                .setSubject(tokenBody.getEmail())
                .claim("features", tokenBody.getFeatures())
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expireDate)
                .compact();
    }

    public String createToken(TokenBody tokenBody) {
        return createToken(
                tokenBody,
                new Date(System.currentTimeMillis() + tokenValidityMinutes * 60 * 1000)
        );
    }

    public TokenBody verifyToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return new TokenBody(claims.getSubject(), (List<String>) claims.get("features"));
    }
}
