package com.pro.tameit.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {

    private final long expiration = 10 * 24 * 60 * 60 * 1000;

    private final String secret = "SecretKey";

    public String addAuthentication(UserDetailsImpl user) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", user.getUsername());
        claims.put("roles", user.getAuthorities());
        claims.put("id", user.getId());

        // We generate a token now.
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUsername(String token) {
        String username;
        try {
            username = getClaims(token).getSubject();
            return username;
        } catch (Exception ex) {
            return null;
        }
    }

    private boolean isTokenExpired(String token) {
        try {

            Date expirationDate = getClaims(token).getExpiration();
            return expirationDate.before(new Date(System.currentTimeMillis()));
        } catch (Exception e) {
            return false;
        }
    }


    public Boolean isTokenValid(String token, UserDetails userDetails) {
        UserDetailsImpl user = (UserDetailsImpl) userDetails;
        final String username = getUsername(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    private Claims getClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }


}
