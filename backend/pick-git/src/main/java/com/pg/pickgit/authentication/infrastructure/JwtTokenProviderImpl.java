package com.pg.pickgit.authentication.infrastructure;

import com.pg.pickgit.authentication.application.JwtTokenProvider;
import com.pg.pickgit.exception.authentication.InvalidTokenException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProviderImpl implements JwtTokenProvider {

    private final String secretKey;
    private final long expirationTimeInMilliSeconds;

    public JwtTokenProviderImpl(
            @Value("${security.jwt.secret-key}") String secretKey,
            @Value("${security.jwt.expiration-time}") long expirationTimeInMilliSeconds
    ){
        this.secretKey = secretKey;
        this.expirationTimeInMilliSeconds = expirationTimeInMilliSeconds;
    }

    @Override
    public String createToken(String payload) {
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + expirationTimeInMilliSeconds);

        return Jwts.builder()
                .claim("username", payload)
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        }catch(JwtException | IllegalArgumentException e){
            return false;
        }
    }

    @Override
    public String getPayloadByKey(String token, String key) {
        try{
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .get(key, String.class);
        }catch(JwtException | IllegalArgumentException e){
            throw new InvalidTokenException();
        }
    }
}
