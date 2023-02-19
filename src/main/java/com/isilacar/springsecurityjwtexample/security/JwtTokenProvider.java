package com.isilacar.springsecurityjwtexample.security;

import com.isilacar.springsecurityjwtexample.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${my.secret.key}")
    private String APP_SECRET;

    @Value("${my.expires.date}")
    private Long EXPIRED_IN;


    public boolean isTokenExpired(String token) {

        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
            Date expirationDate = Jwts.parserBuilder().setSigningKey(APP_SECRET).build().parseClaimsJws(token).getBody().getExpiration();

            //günü geçmiş oluyor
            return expirationDate.before(new Date());
        }catch (SignatureException e){
            return false;
        }catch (MalformedJwtException e){
            return false;
        }catch (ExpiredJwtException e){
            return false;
        }catch (UnsupportedJwtException e){
            return false;
        }catch (IllegalArgumentException e){
            return false;
        }

    }

    public String getSubject(String jwtToken) {
        return Jwts.parserBuilder().setSigningKey(APP_SECRET).build().parseClaimsJws(jwtToken).getBody().getSubject();
    }

    private Key getKey(){
        byte[] key = Decoders.BASE64.decode(APP_SECRET);
        return Keys.hmacShaKeyFor(key);
    }
    public String generateToken(Authentication authentication ) {
        Date expiredDate = new Date(new Date().getTime() + EXPIRED_IN);
        User user= (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .signWith(getKey(),SignatureAlgorithm.HS256)
                .compact();
    }
}
